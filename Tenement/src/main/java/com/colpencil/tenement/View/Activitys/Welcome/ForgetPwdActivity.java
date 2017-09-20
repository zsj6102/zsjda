package com.colpencil.tenement.View.Activitys.Welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Result;
import com.colpencil.tenement.Present.Welcome.ForgetPwdPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Imples.ForgetPwdView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.Md5Utils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeCount;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @Description: 忘记密码
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/29
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_forgetpwd
)
public class ForgetPwdActivity extends ColpencilActivity implements ForgetPwdView, View.OnClickListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.et_phone)
    EditText et_phone;

    @Bind(R.id.et_yan)
    EditText et_yan;

    @Bind(R.id.et_pwd)
    EditText et_pwd;

    @Bind(R.id.et_new_pwd)
    EditText et_new_pwd;

    @Bind(R.id.tv_getyan)
    TextView tv_getyan;

    @Bind(R.id.ll_phone)
    LinearLayout ll_phone;

    @Bind(R.id.btn_change)
    Button btn_change;
    private String phone="";
    private String pwd="";
    private String newPwd="";
    private String yan="";
    private ForgetPwdPresent present;

    private SweetAlertDialog pDialog;

    private int i = -1;
    private int flag;

    @Override
    protected void initViews(View view) {
        flag = getIntent().getIntExtra("flag", 0);
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        if (flag==2){
            phone = SharedPreferencesUtil.getInstance(this).getString("acount");
            tv_title.setText("修改密码");
            et_phone.setText(phone);
            et_phone.setEnabled(false);
        }else if (flag==3){
            tv_title.setText("忘记密码");
        }

        btn_change.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        tv_getyan.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new ForgetPwdPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void isChange(EntityResult<String> result) {
        int code = result.getCode();
        String message = result.getMessage();
        switch (code){
            case 0:  // 成功
                pDialog.setTitleText("修改成功哦！")
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                mAm.finishAllActivity();
                SharedPreferencesUtil.getInstance(this).setString("passWord","");
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                ToastTools.showShort(this,true,"修改成功！");
                break;
            case 1:  // 失败
                pDialog.setTitleText(message)
                        .setConfirmText("重新提交")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                if (flag==2){
                                    present.changePwd(phone,yan, Md5Utils.encode(pwd),Md5Utils.encode(newPwd),1+"");
                                }else if (flag==3){
                                    present.changePwd(phone,yan, Md5Utils.encode(pwd),Md5Utils.encode(newPwd),0+"");
                                }
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
            case 4: // 验证失败
                pDialog.dismissWithAnimation();
                ToastTools.showShort(this,false,message);
                break;
        }
        i = -1;
    }

    @Override
    public void getMessgae(Result result) {
        int code = result.code;
        String message = result.message;
        switch (code){
            case 0:
                TimeCount time = new TimeCount(60000, 1000, tv_getyan);
                time.start();//开启倒计时
                ToastTools.showShort(this,true,"验证码已成功发送，注意查收！");
                break;
            case 1:
                ToastTools.showShort(this,false,message);
                break;
            case 3:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }

    @Override
    public void loadError(String msg) {
        if (pDialog!=null){
            pDialog.dismiss();
        }
        ToastTools.showShort(this,false,"请求失败！");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_getyan:
                if (flag==2){//修改密码
                    present.getCode(phone,flag);
                }else {
                    phone = et_phone.getText().toString();
                    if (TextUtils.isEmpty(phone)){
                        ToastTools.showShort(this,false,"您未输入手机号码");
                        return;
                    }
                    if (TextStringUtils.isMobileNO(phone)==false){
                        ToastTools.showShort(this,false,"您未输入手机号码不合法");
                        return;
                    }
                    present.getCode(phone,flag);
                }
                break;
            case R.id.btn_change:
                phone = et_phone.getText().toString();
                pwd = et_pwd.getText().toString();
                newPwd = et_new_pwd.getText().toString();
                yan = et_yan.getText().toString();
                if (flag!=2){
                    if (TextUtils.isEmpty(phone)){
                        ToastTools.showShort(this,false,"您未输入手机号码");
                        return;
                    }
                    if (TextStringUtils.isMobileNO(phone)==false){
                        ToastTools.showShort(this,false,"您未输入手机号码不合法");
                        return;
                    }
                }
                if (TextUtils.isEmpty(pwd)){
                    ToastTools.showShort(this,false,"您输入的密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(newPwd)){
                    ToastTools.showShort(this,false,"您输入的新密码不能为空");
                    return;
                }
                if (pwd.length()<6){
                    NotificationTools.show(this, "密码不能小于6位数！");
                    return;
                }
                if (TextUtils.isEmpty(yan)){
                    ToastTools.showShort(this,false,"您输入的验证码不能为空");
                    return;
                }
                if (pwd.equals(newPwd)==false){
                    ToastTools.showShort(this,false,"您输入的两次密码不一致");
                    return;
                }

                if (pwd.length()<6&&pwd.length()>15){
                    ToastTools.showShort(this,false,"您设定的密码必须大于6个字符小于15个字符");
                    return;
                }
                // TODO: 2016/9/29 更改密码的操作
                if (flag==2){
                    present.changePwd(phone,yan, Md5Utils.encode(pwd),Md5Utils.encode(newPwd),1+"");
                }else if (flag==3){
                    present.changePwd(phone,yan, Md5Utils.encode(pwd),Md5Utils.encode(newPwd),0+"");
                }

                if (pDialog!=null&&pDialog.isShowing()){
                    pDialog.dismiss();
                }
                pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                        .setTitleText("正在提交中");
                pDialog.show();
                pDialog.setCancelable(false);
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
                break;

        }
    }
}
