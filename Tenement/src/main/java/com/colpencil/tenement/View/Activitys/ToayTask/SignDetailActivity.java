package com.colpencil.tenement.View.Activitys.ToayTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Bean.Sign;
import com.colpencil.tenement.Present.TodayTask.SignPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Imples.SignView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.PhotoBitmapUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @author 汪 亮
 * @Description: 拍完照片后的签到
 * @Email DramaScript@outlook.com
 * @date 2016/9/21
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_sigin_detail
)
public class SignDetailActivity extends ColpencilActivity implements View.OnClickListener,SignView {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.imageView)
    ImageView imageView;

    @Bind(R.id.btn_chong)
    Button btn_chong;

    @Bind(R.id.btn_sign)
    Button btn_sign;


    private String imagePath;

    private String path = Environment.getExternalStorageDirectory() + "/aImage/";
    private String fileName;
    private String signStatus;
    private SignPresent present;
    private String loaction;

    private int i = -1;
    private SweetAlertDialog pDialog;

    private File lubanFile;

    @Override
    protected void initViews(View view) {
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        signStatus = getIntent().getStringExtra("signStatus");
        imagePath = getIntent().getStringExtra("imagePath");
        loaction = getIntent().getStringExtra("loaction");
        if (!TextUtils.isEmpty(imagePath)) {
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, option);
            int bitmapDegree = PhotoBitmapUtils.getBitmapDegree(imagePath);
            Bitmap bitmap1 = PhotoBitmapUtils.rotateBitmapByDegree(bitmap, bitmapDegree);
            imageView.setImageBitmap(bitmap1);
        }
        if ("0".equals(signStatus)){
            tv_title.setText("签到");
            btn_sign.setText("签到");
        }else{
            tv_title.setText("签退");
            btn_sign.setText("签退");
        }
        tv_rigth.setText("签到记录");
        tv_rigth.setVisibility(View.INVISIBLE);
        ll_rigth.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        btn_chong.setOnClickListener(this);
        btn_sign.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new SignPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_rigth: // 我的签到记录

                break;
            case R.id.btn_chong: // 重拍
                MaterialDialog show = new MaterialDialog.Builder(SignDetailActivity.this)
                        .title("打开相机")
                        .content("您是否需要打开相机拍照,重新拍照？")
                        .positiveText("打开")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                getImage();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            }
                        })
                        .show();
                break;
            case R.id.btn_sign: // 签到
                if (TextUtils.isEmpty(imagePath)){
                    ToastTools.showShort(this,false,"请先选择拍照！");
                    return;
                }
                if (pDialog!=null&&pDialog.isShowing()){
                    pDialog.dismiss();
                }
                pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                        .setTitleText("正在提交中");
                pDialog.show();
                pDialog.setCancelable(true);
                new CountDownTimer(800 * 7, 800) {
                    public void onTick(long millisUntilFinished) {
                        // you can change the progress bar color by ProgressHelper every 800 millis
                        i++;
                        switch (i){
                            case 0:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                                break;
                            case 1:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                                break;
                            case 2:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                                break;
                            case 3:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
                                break;
                            case 4:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
                                break;
                            case 5:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
                                break;
                            case 6:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                                break;
                        }
                    }

                    @Override
                    public void onFinish() {

                    }

                }.start();
                Luban.get(this)
                        .load(new File(imagePath))
                        .putGear(Luban.THIRD_GEAR)
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                ColpencilLogger.e("开始压缩中。。。");
                            }

                            @Override
                            public void onSuccess(File file) {
                                submit(file);
                                lubanFile =file;
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }).launch();

                break;
        }
    }

    /**
     * 提交记录
     */
    private void submit(File file) {
        switch (signStatus){

            case "0": // 未签到
                present.siginIn(loaction,file);
                break;
            case "1": //  已签到
                present.siginOut(loaction,file);
                break;
            case "2":  //  已签退

                break;
        }
    }

    private void getImage() {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path + fileName)));
        startActivityForResult(intent, Activity.DEFAULT_KEYS_DIALER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Activity.DEFAULT_KEYS_DIALER: {
                if (resultCode==-1){
                    imagePath = path + fileName;
                    File file = new File(path + fileName);
                    BitmapFactory.Options option = new BitmapFactory.Options();
                    option.inSampleSize = 2;
                    Bitmap bitmap = BitmapFactory.decodeFile(path + fileName, option);
                    int bitmapDegree = PhotoBitmapUtils.getBitmapDegree(path + fileName);
                    Bitmap bitmap1 = PhotoBitmapUtils.rotateBitmapByDegree(bitmap, bitmapDegree);
                    imageView.setImageBitmap(bitmap1);

                }
                break;
            }
        }
    }

    @Override
    public void signIn(EntityResult<Sign> result) {
        int code = result.getCode();
        String message = result.getMessage();
        switch (code){
            case 0:  // 成功
                pDialog.setTitleText("提交成功哦！")
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                pDialog.dismiss();
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(1);
                RxBus.get().post("sign",rxBusMsg);
                ToastTools.showShort(this,true,"签到成功！");
                finish();
                mAm.finishActivity(SignInActivity.class);
                break;
            case 1:  // 失败
                pDialog.setTitleText("提交失败，请检查网络是否通畅。")
                        .setConfirmText("重新提交")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                submit(lubanFile);
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                pDialog.dismissWithAnimation();
                            }
                        })
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                break;
            case 3: // 未登录
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case 4: // 验证失败
                pDialog.dismissWithAnimation();
                ToastTools.showShort(this,false,message);
                break;
        }
        i = -1;
    }

    @Override
    public void signOut(EntityResult<Sign> result) {
        int code = result.getCode();
        String message = result.getMessage();
        switch (code){
            case 0:  // 成功
                pDialog.setTitleText("提交成功哦！")
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                pDialog.dismissWithAnimation();
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(2);
                RxBus.get().post("sign",rxBusMsg);
                finish();
                mAm.finishActivity(SignInActivity.class);
                ToastTools.showShort(this,true,"提交成功");
                break;
            case 1:  // 失败
                pDialog.setTitleText("提交失败，请检查网络是否通畅。")
                        .setConfirmText("重新提交")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                submit(lubanFile);
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                pDialog.dismissWithAnimation();
                            }
                        })
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                break;
            case 3: // 未登录
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case 4: // 验证失败
                pDialog.dismissWithAnimation();
                ToastTools.showShort(this,false,message);
                break;
        }
        i = -1;
    }

    @Override
    public void loadError(String msg) {
        pDialog.dismiss();
        ToastTools.showShort(this,false,"请求失败！");
    }
}
