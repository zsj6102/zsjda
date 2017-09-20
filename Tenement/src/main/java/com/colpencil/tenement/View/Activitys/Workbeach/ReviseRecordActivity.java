package com.colpencil.tenement.View.Activitys.Workbeach;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.tenement.Bean.CleanRecord;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Result;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Present.Home.EditRecordPresent;
import com.colpencil.tenement.Present.Home.UploadRecordPresenter;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Imples.EditRecordView;
import com.colpencil.tenement.View.Imples.UploadRecordView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilGridView;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.yqritc.recyclerviewflexibledivider.R.id.time;

/**
 * Created by Administrator on 2017/2/13.
 */

@ActivityFragmentInject(contentViewId = R.layout.activity_revise_record)
public class ReviseRecordActivity extends ColpencilActivity implements EditRecordView{

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_date)
    TextView tv_time;

    @Bind(R.id.tv_address)
    TextView tv_address;

    @Bind(R.id.edt_explain)
    EditText edt_explain;

    @Bind(R.id.item_greenclean_gridview)
    ColpencilGridView gridView;

    @Bind(R.id.btn_change)
    Button btn_revise;

    private SweetAlertDialog dialog;
    private EditRecordPresent present;

    private CleanRecord cleanRecord;
    private int type;
    private String content;
    private int i = -1;

    @Override
    protected void initViews(View view) {
        cleanRecord = (CleanRecord) getIntent().getSerializableExtra("cleanRecord");
        type = getIntent().getIntExtra(StringConfig.TYPE,0);

        tv_title.setText("修改工作记录");
        tv_time.setText(cleanRecord.getDate());
        tv_time.setTextColor(getResources().getColor(R.color.text_drak1));
        tv_address.setText(cleanRecord.getPlace());
        tv_address.setTextColor(getResources().getColor(R.color.text_drak1));

        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_revise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit();
            }
        });
    }

    private void commit(){
        content = edt_explain.getText().toString();
        if (TextUtils.isEmpty(content)){
            ToastTools.showShort(ReviseRecordActivity.this,false,"您还未填写任务描述");
            return;
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new SweetAlertDialog(ReviseRecordActivity.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("正在提交中");
        dialog.setCancelable(true);
        dialog.show();
        new CountDownTimer(800 * 7, 800) {
            public void onTick(long millisUntilFinished) {
                // you can change the progress bar color by ProgressHelper every 800 millis
                i++;
                switch (i) {
                    case 0:
                        dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                        break;
                    case 1:
                        dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                        break;
                    case 2:
                        dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                        break;
                    case 3:
                        dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
                        break;
                    case 4:
                        dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
                        break;
                    case 5:
                        dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
                        break;
                    case 6:
                        dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.mian_blue));
                        break;
                }
            }

            @Override
            public void onFinish() {

            }

        }.start();

        present.editRecord(content, cleanRecord.getWorkId());
    }

    public void key(){
        // 虚拟键盘隐藏 判断view是否为空
        if (getWindow().peekDecorView() != null) {
            //隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(getWindow().peekDecorView().getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog != null){
            dialog.dismiss();
        }
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new EditRecordPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void editResult(Result result) {
        int code = result.code;
        String message = result.message;
        switch (code) {
            case 0: //成功
                dialog.setTitleText("修改成功")
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                Intent intent = new Intent(this, CleanRecordActivity.class);
                intent.putExtra(StringConfig.TYPE, type);
                startActivity(intent);
                finish();
                mAm.finishActivity(CleanRecordActivity.class);
                ToastTools.showShort(this, true, "修改成功");
                break;
            case 1: //失败
                dialog.setTitleText("修改失败")
                        .setConfirmText("重新提交")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                commit();
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                dialog.dismiss();
                            }
                        })
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                break;
            case 3://未登录
                dialog.dismissWithAnimation();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case 4: // 验证失败
                dialog.dismissWithAnimation();
                ToastTools.showShort(this, true, message);
                break;
            default:
                ToastTools.showShort(this, false, message);
                break;
        }

        i = -1;
    }

    @Override
    public void loadError(String msg) {
        if(dialog != null){
            dialog.dismiss();
        }
        ToastTools.showShort(this, "请求失败");
    }
}
