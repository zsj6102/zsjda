package com.colpencil.tenement.View.Activitys.Workbeach;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.colpencil.tenement.Bean.CodeResult;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Bean.WaterInfo;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Present.Home.WriteWatermeterPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.OnlineTalk.VoiceCallActivity;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Imples.WriteWatermeterView;
import com.easemob.chat.EMChatManager;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author 汪 亮
 * @Description: 填写水表界面
 * @Email DramaScript@outlook.com
 * @date 2016/9/18
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_writewatermeter
)
public class WriteWatermeterActivity extends ColpencilActivity
        implements WriteWatermeterView {

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.et_nums)
    EditText et_nums;

    @Bind(R.id.tv_roomid)
    TextView tv_roomid;

    @Bind(R.id.tv_name)
    TextView tv_name;

    @Bind(R.id.tv_id)
    TextView tv_id;

    @Bind(R.id.tv_last)
    TextView tv_last;

    @Bind(R.id.submit)
    TextView submit;

    @Bind(R.id.tv_type)
    TextView tv_type;

    @Bind(R.id.dan)
    TextView dan;

    @Bind(R.id.dan2)
    TextView dan2;

    private WriteWatermeterPresent present;
    private String result;
    private CodeResult codeResult;
    private Gson gson;
    private SweetAlertDialog pDialog;

    private int i = -1;
    private int isSao;
    private int roomNo;

    private String communityId="";
    private String ownerName="";
    private String waterId="";
    private String roomId="";
    private int type=0;
    private double lastRecord=0;
    private String recordId;
    private int user_id;
    private boolean canPost = true;


    @Override
    protected void initViews(View view) throws JsonSyntaxException,IllegalStateException {
        isSao = getIntent().getIntExtra("isSao",0);
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        if (isSao == 0) { // 点击记录进来
            tv_title.setText("抄表记录");
            roomNo = getIntent().getIntExtra("roomNo", 0);
            type = getIntent().getIntExtra("type", 0);
            ownerName = getIntent().getStringExtra("ownerName");
            waterId = getIntent().getStringExtra("waterId");
            String lastRecord = getIntent().getStringExtra("lastRecord");
            communityId = getIntent().getStringExtra("communityId");
            roomId = getIntent().getStringExtra("roomId");
            String degrees = getIntent().getStringExtra("degrees");
            tv_roomid.setText(roomId + "");
            tv_name.setText(ownerName);
            tv_id.setText(waterId);
            et_nums.setText(degrees);
            tv_last.setText(lastRecord);
            if (type == 0) {
                tv_type.setText("水表");
                dan.setText("吨");
                dan2.setText("吨");
            } else {
                tv_type.setText("电表");
                dan.setText("度");
                dan2.setText("度");
            }
            DialogTools.showLoding(this, "温馨提示", "正在获取上月见抄");
            present.getLast(waterId, type + "");

        } else { // 直接点击扫描记录进来的
            tv_title.setText(R.string.title_writeWatermeter);
            result = getIntent().getStringExtra("result");
            gson = new Gson();
            codeResult = gson.fromJson(result, CodeResult.class);
            ColpencilLogger.e("------------解析二维码信息：" + codeResult.toString());
            type = codeResult.type;
            waterId = codeResult.devId;
            tv_id.setText(waterId);
            if (type == 0) {
                tv_type.setText("水表");
                dan.setText("吨");
                dan2.setText("吨");
            } else {
                tv_type.setText("电表");
                dan.setText("度");
                dan2.setText("度");
            }
            DialogTools.showLoding(this, "温馨提示", "正在获取上月见抄中。。。");
            present.getLast(waterId, type + "");
        }
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new WriteWatermeterPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.ll_left)
    void backOnclick() {
        finish();
    }

    @OnClick(R.id.submit)
    void submit() {
        if (canPost==true){
            post();
        }
    }

    private void post(){
        String num = et_nums.getText().toString();
        if (TextUtils.isEmpty(num)) {
            ToastTools.showShort(this,"请输入本月见抄！");
            return;
        }
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
        if (Double.valueOf(lastRecord)>Double.valueOf(num)){
            ToastTools.showShort(this,"您输入的抄表数不可以小于上抄表数！");
            return;
        }
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("正在提交中");
        pDialog.show();
        pDialog.setCancelable(true);
        new CountDownTimer(800 * 7, 800) {
            public void onTick(long millisUntilFinished) {
                // you can change the progress bar color by ProgressHelper every 800 millis
                i++;
                switch (i) {
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
        present.submit(roomId, ownerName, waterId, num, type, lastRecord+"", communityId,recordId);
    }

    @Override
    public void submitResult(EntityResult<String> result) {
        int code = result.getCode();
        String message = result.getMessage();
        switch (code) {
            case 0:  // 成功
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(0);
                RxBus.get().post("wwa",rxBusMsg);
                pDialog.setTitleText("提交成功！")
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                finish();
                if (isSao!=0){
                    startActivity(new Intent(WriteWatermeterActivity.this,RecordDetailsActivity.class));
                }
                ToastTools.showShort(this,true,"提交成功！");
                break;
            case 1:  // 失败
                pDialog.setTitleText(message)
                        .setConfirmText("重新提交")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                submit();
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
            default:
                pDialog.dismissWithAnimation();
                ToastTools.showShort(this, false, message);
                break;
        }
        i = -1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pDialog != null) {
            pDialog.dismiss();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void getLast(EntityResult<WaterInfo> last) {
        DialogTools.dissmiss();
        int code = last.getCode();
        String message = last.getMessage();
        WaterInfo data = last.getData();
        lastRecord = data.lastRecord;
        ownerName = data.ownerName;
        roomId = data.roomId+"";
        if (code == 0) {
            recordId = last.getData().recordId+"";
            user_id = SharedPreferencesUtil.getInstance(this).getInt("user_id", 0);
            if (last.getData().currEmp!=-1){
                if (last.getData().currEmp!=user_id){
                    canPost = false;
                    submit.setBackground(getResources().getDrawable(R.drawable.rect_gre));
                    ToastTools.showShort(this,false,"您没有该条记录的修改权限，建议您联系原抄表记录者进行修改！");
                }else {
                    // TODO: 2016/10/25 已经抄表了，提示用户是否修改
                    if (isSao!=0){
                        MaterialDialog show = new MaterialDialog.Builder(WriteWatermeterActivity.this)
                                .title("温馨提示")
                                .content("该手电表本月已有抄表记录，是否进行修改？")
                                .positiveText(R.string.online_yes)
                                .negativeText(R.string.online_no)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        canPost = true;
                                        submit.setBackground(getResources().getDrawable(R.drawable.rect_blue));
                                    }
                                })
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        finish();
                                    }
                                })
                                .show();
                    }
                }
            }
        } else if (code == 1) {
            ToastTools.showShort(this,false,message);
        } else if (code==5){
            ToastTools.showShort(this,false,message);
            finish();
        }else {
            ToastTools.showShort(this,false,message);
        }
        tv_roomid.setText(data.roomNo + "");
        tv_name.setText(data.ownerName);
        if (isSao!=0){
            if (data.currRecord==-1){
                et_nums.setText("");
            }else {
                et_nums.setText(data.currRecord+"");
            }
            if (lastRecord==-1){
                tv_last.setText("暂无！");
            }else {
                if (type==0){
                    tv_last.setText(lastRecord+"");
                    dan2.setText("吨");
                }else {
                    tv_last.setText(lastRecord+"");
                    dan2.setText("度");
                }
            }
        }

        communityId = data.communityId + "";
    }

    @Override
    public void loadError(String msg) {
        if (pDialog!=null){
            pDialog.dismiss();
        }
        DialogTools.dissmiss();
        ToastTools.showShort(this,false,"请求失败！");
    }
}