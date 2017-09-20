package com.colpencil.propertycloud.View.Activitys.Welcome;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Present.CloseManager.ChangePwdPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Imples.ChangePwdView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.Md5Utils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeCount;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import butterknife.Bind;

/**
 * @author 汪 亮
 * @Description: 修改/忘记密码
 * @Email DramaScript@outlook.com
 * @date 2016/11/10
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_changepwd
)
public class ChangePwdActivity extends ColpencilActivity implements View.OnClickListener, ChangePwdView {

    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.et_acount)
    EditText et_acount;
    @Bind(R.id.et_passWord)
    EditText et_passWord;
    @Bind(R.id.et_passWord2)
    EditText et_passWord2;
    @Bind(R.id.et_yan)
    EditText et_yan;
    @Bind(R.id.tv_get)
    TextView tv_get;
    @Bind(R.id.btn_subimt)
    Button btn_subimt;
    @Bind(R.id.ll_forget)
    LinearLayout ll_forget;
    @Bind(R.id.ll_change)
    LinearLayout ll_change;
    @Bind(R.id.current_mobile)
    TextView current_mobile;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    private int flag;// 2 修改密码   3 忘记密码
    private String mobile;
    private String acount;
    private String yan;
    private String pwd;
    private String pwd2;
    private ChangePwdPresent present;

    @Override
    protected void initViews(View view) {
        flag = getIntent().getIntExtra("flag", 0);
        switch (flag) {
            case 2:
                mobile = SharedPreferencesUtil.getInstance(this).getString("mobile");
                et_acount.setText(mobile);
                et_acount.setEnabled(false);
                tv_title.setText("修改密码");
                ll_forget.setVisibility(View.GONE);
                ll_change.setVisibility(View.VISIBLE);
                current_mobile.setText(mobile);
                break;
            case 3:
                et_acount.setEnabled(true);
                tv_title.setText("忘记密码");
                ll_forget.setVisibility(View.VISIBLE);
                ll_change.setVisibility(View.GONE);
                break;
        }
        tv_get.setOnClickListener(this);
        btn_subimt.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        et_acount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mobile = et_acount.getText().toString();
                    if (TextUtils.isEmpty(mobile)) {
                        ToastTools.showShort(ChangePwdActivity.this, "手机号码不能为空");
                    } else if (!TextStringUtils.isMobileNO(mobile)) {
                        ToastTools.showShort(ChangePwdActivity.this, "请输入正确的手机号");
                    } else {

                    }
                }
            }
        });
        et_yan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    yan = et_yan.getText().toString();
                    if (TextUtils.isEmpty(yan)) {
                        ToastTools.showShort(ChangePwdActivity.this, "验证码不能为空");
                    }
                }
            }
        });
        et_passWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    pwd = et_passWord.getText().toString();
                    if (TextUtils.isEmpty(pwd)) {
                        ToastTools.showShort(ChangePwdActivity.this, "新密码不能为空");
                    }
                }
            }
        });
        et_passWord2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    pwd2 = et_passWord2.getText().toString();
                    if (TextUtils.isEmpty(pwd2)) {
                        ToastTools.showShort(ChangePwdActivity.this, "再次输入新密码不能为空");
                    }
                }
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new ChangePwdPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get: //  获取验证码
                if (tv_get.getText().toString().contains("验证码")) {
                    acount = et_acount.getText().toString();
                    if (flag == 2) {
                        present.getCode(flag, mobile);
                    } else {
                        if (TextUtils.isEmpty(acount)) {
                            ToastTools.showShort(this, "手机号码不能为空！");
                            return;
                        } else if (!TextStringUtils.isMobileNO(acount)) {
                            ToastTools.showShort(this, "请输入正确的手机号！");
                            return;
                        } else {
                            present.getCode(flag, acount);
                        }
                    }
                }
                break;
            case R.id.btn_subimt:
                acount = et_acount.getText().toString();
                yan = et_yan.getText().toString();
                pwd = et_passWord.getText().toString();
                pwd2 = et_passWord2.getText().toString();
                if (flag == 2) {
                    if (TextUtils.isEmpty(yan)) {
                        ToastTools.showShort(this, "您还未填写验证码！");
                        return;
                    } else if (TextUtils.isEmpty(pwd)) {
                        ToastTools.showShort(this, "您还未填写修改后的密码！");
                        return;
                    } else if (TextUtils.isEmpty(pwd2)) {
                        ToastTools.showShort(this, "您还未填写修改后的新密码！");
                        return;
                    } else if (pwd.length() < 6) {
                        ToastTools.showShort(this, "密码不能小于6位数！");
                        return;
                    } else if (!pwd.equals(pwd2)) {
                        ToastTools.showShort(this, "两次输入的密码不一致！");
                        return;
                    } else {
                        present.modPwd(acount, "mod", yan, Md5Utils.encode(pwd), Md5Utils.encode(pwd2));
                    }
                } else {
                    if (TextUtils.isEmpty(acount)) {
                        ToastTools.showShort(this, "手机号码不能为空！");
                        return;
                    } else if (!TextStringUtils.isMobileNO(acount)) {
                        ToastTools.showShort(this, "请输入正确的手机号！");
                        return;
                    } else if (TextUtils.isEmpty(yan)) {
                        ToastTools.showShort(this, "您还未填写验证码！");
                        return;
                    } else if (TextUtils.isEmpty(pwd)) {
                        ToastTools.showShort(this, "您还未填写新密码！");
                        return;
                    } else if (pwd.length() < 6) {
                        ToastTools.showShort(this, "密码不能小于6位数！");
                        return;
                    } else if (TextUtils.isEmpty(pwd2)) {
                        ToastTools.showShort(this, "您还未填写修改后的新密码！");
                        return;
                    } else if (!pwd.equals(pwd2)) {
                        ToastTools.showShort(this, "两次输入的密码不一致！");
                        return;
                    } else {
                        present.modPwd(acount, "forgot", yan, Md5Utils.encode(pwd), Md5Utils.encode(pwd2));
                    }
                }
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void getCode(ResultInfo resultInfo) {
        int code = resultInfo.code;
        String message = resultInfo.message;
        if (code == 0) {
            TimeCount time = new TimeCount(60000, 1000, tv_get);
            time.start();//开启倒计时
            ToastTools.showShort(this, true, "验证码已成功发送，注意查收！");
        } else {
            ToastTools.showShort(this, message);
        }
    }

    @Override
    public void changePwd(ResultInfo resultInfo) {
        int code = resultInfo.code;
        String message = resultInfo.message;
        switch (code) {
            case 0:
                ToastTools.showForget(this, true, "修改成功！");
                if (flag == 2) {
                    mAm.finishAllActivity();
                    SharedPreferencesUtil.getInstance(this).setString("passWord", "");
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    mAm.finishAllActivity();
                    SharedPreferencesUtil.getInstance(this).setString("passWord", "");
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case 1:
                ToastTools.showShort(this, message);
                break;
            case 4:
                ToastTools.showShort(this, message);
                break;
        }
    }
}