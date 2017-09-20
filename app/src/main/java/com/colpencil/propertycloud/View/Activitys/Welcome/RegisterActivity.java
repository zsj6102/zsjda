package com.colpencil.propertycloud.View.Activitys.Welcome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.Layout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.propertycloud.Bean.LoginInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Present.RegistPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.ApiCloudActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.AddMemberActivity;
import com.colpencil.propertycloud.View.Activitys.Home.LoadUriActivity;
import com.colpencil.propertycloud.View.Activitys.HomeActivity;
import com.colpencil.propertycloud.View.Imples.RegistView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.Md5Utils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeCount;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import butterknife.Bind;

import static com.colpencil.propertycloud.Overall.HostUrl.BASE_HOST_PATH;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_register
)
public class RegisterActivity extends ColpencilActivity implements View.OnClickListener, RegistView {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.et_acount)
    EditText et_acount;
    @Bind(R.id.et_yan)
    EditText et_yan;
    @Bind(R.id.et_passWord)
    EditText et_passWord;
    @Bind(R.id.et_passWord2)
    EditText et_passWord2;
    @Bind(R.id.tv_get)
    TextView tv_get;
    @Bind(R.id.btn_regist)
    Button btn_regist;

    @Bind(R.id.ll_read)
    LinearLayout ll_read;

    @Bind(R.id.iv_agree)
    ImageView iv_agree;

    @Bind(R.id.tv_xie)
    TextView tv_xie;

    private boolean isAgree = true;

    private RegistPresent present;

    @Override
    protected void initViews(View view) {
        setStatusBaropen(true);
        setStatecolor(Color.parseColor("#0f3890"));
        tv_title.setText("注册");
        ll_left.setOnClickListener(this);
        tv_get.setOnClickListener(this);
        btn_regist.setOnClickListener(this);
        et_acount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String mobile = et_acount.getText().toString();
                    if (TextUtils.isEmpty(mobile)) {
                        ToastTools.showShort(RegisterActivity.this, "手机号码不能为空");
                    } else if (!TextStringUtils.isMobileNO(mobile)) {
                        ToastTools.showShort(RegisterActivity.this, "请输入正确的手机号");
                    } else {

                    }
                }
            }
        });
        et_yan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String yan = et_yan.getText().toString();
                    if (TextUtils.isEmpty(yan)) {
                        ToastTools.showShort(RegisterActivity.this, "验证码不能为空");
                    }
                }
            }
        });
        et_passWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String password = et_passWord.getText().toString();
                    if (TextUtils.isEmpty(password)) {
                        ToastTools.showShort(RegisterActivity.this, "密码不能为空");
                    } else if (password.length() < 6) {
                        ToastTools.showShort(RegisterActivity.this, "密码长度不能少于6位");
                    }
                }
            }
        });
        et_passWord2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String password = et_passWord2.getText().toString();
                    if (TextUtils.isEmpty(password)) {
                        ToastTools.showShort(RegisterActivity.this, "确认密码不能为空");
                    } else if (password.length() < 6) {
                        ToastTools.showShort(RegisterActivity.this, "确认密码长度不能少于6位");
                    }
                }
            }
        });

        ll_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAgree) {
                    iv_agree.setImageDrawable(getResources().getDrawable(R.mipmap.fit_uncheck));
                    isAgree = false;
                } else {
                    iv_agree.setImageDrawable(getResources().getDrawable(R.mipmap.fit_check));
                    isAgree = true;
                }
            }
        });
        tv_xie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoadUriActivity.class);
                intent.putExtra("url", BASE_HOST_PATH + "/property/agreement/user-agreement.do?flag=register");
                intent.putExtra("title", "用户协议");
                startActivity(intent);
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new RegistPresent();
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
            case R.id.tv_get:
                if (tv_get.getText().toString().contains("验证码")) {
                    String mobile = et_acount.getText().toString();
                    if (TextUtils.isEmpty(mobile)) {
                        ToastTools.showShort(this, "手机号码不能为空");
                    } else if (!TextStringUtils.isMobileNO(mobile)) {
                        ToastTools.showShort(this, "请输入正确的手机号");
                    } else {
                        present.getCode(1, mobile);
                    }
                }
                break;
            case R.id.btn_regist:
                if (!isAgree) {
                    ToastTools.showShort(this, "请先阅读上方协议");
                    return;
                }
                String mobile = et_acount.getText().toString();
                String checknum = et_yan.getText().toString();
                String password1 = et_passWord.getText().toString();
                String password2 = et_passWord2.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    ToastTools.showShort(this, "手机号码不能为空");
                    return;
                } else if (!TextStringUtils.isMobileNO(mobile)) {
                    ToastTools.showShort(this, "请输入正确的手机号");
                    return;
                } else if (TextUtils.isEmpty(checknum)) {
                    ToastTools.showShort(this, "验证码不能为空");
                    return;
                } else if (TextUtils.isEmpty(password1)) {
                    ToastTools.showShort(this, "密码不能为空");
                    return;
                } else if (TextUtils.isEmpty(password2)) {
                    ToastTools.showShort(this, "确认密码不能为空");
                    return;
                } else if (password1.length() < 6) {
                    ToastTools.showShort(this, "密码长度不能少于6位");
                    return;
                } else if (password2.length() < 6) {
                    ToastTools.showShort(this, "确认密码长度不能少于6位");
                    return;
                } else if (!password1.equals(password2)) {
                    ToastTools.showShort(this, "两次输入的密码不一致");
                    return;
                } else {
                    present.regist(mobile, Md5Utils.encode(password1), checknum, Md5Utils.encode(password2));
                }
                break;
        }

    }

    @Override
    public void regist(ResultInfo resultInfo) {
        int code = resultInfo.code;
        String message = resultInfo.message;
        String mobile = et_acount.getText().toString();
        String passWord = et_passWord.getText().toString();
        if (code == 0) {
            DialogTools.showLoding(this, "温馨提示", "注册成功，正在为您自动登录中");
            present.login(mobile, Md5Utils.encode(passWord));
        } else {
            ToastTools.showShort(this, message);
        }
    }

    @Override
    public void loginForServer(ResultInfo<LoginInfo> resultInfo) {
        SystemClock.sleep(1000);
        DialogTools.dissmiss();
        String message = resultInfo.message;
        if (resultInfo.code == 0) {
            if (!TextUtils.isEmpty(resultInfo.data.comuId)) {
                SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("comuid", resultInfo.data.comuId);
                SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("shortnm", resultInfo.data.shortNm);
                SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("propertytel", resultInfo.data.propertytel);
                SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("servicetel", resultInfo.data.servicetel);
            }
            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setBoolean("isLogin", true);
            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("memberId", resultInfo.data.memberId);
            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("usrNm", resultInfo.data.usrNm);
            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("mobile", et_acount.getText().toString());
            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("passWord", et_passWord.getText().toString());
            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("addr", resultInfo.data.addr);
            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setBoolean("isProprietor", resultInfo.data.isProprietor);
            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setInt("userType", resultInfo.data.userType);
            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setBoolean("hasShen", resultInfo.data.hasShen);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            RxBusMsg msg = new RxBusMsg();
            msg.setType(0);
            RxBus.get().post("mine", msg);

            mAm.finishAllActivity();
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {
            ToastTools.showShort(this, message);
            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setBoolean("isLogin", false);
        }
    }

    @Override
    public void getCode(ResultInfo resultInfo) {
        int code = resultInfo.code;
        String message = resultInfo.message;
        if (code == 0) {
            if (resultInfo.data_sources == 1) {
                showDialog(resultInfo.prompt_message);
            } else {
                TimeCount time = new TimeCount(60000, 1000, tv_get);
                time.start();//开启倒计时
                ToastTools.showShort(this, true, "验证码已成功发送，注意查收！");
            }
        } else {
            if (resultInfo.data_sources == 1) {
                showDialog(resultInfo.prompt_message);
                return;
            } else {
                ToastTools.showShort(this, message);
            }
        }
    }

    private void showDialog(String msg) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_info, null);
        final DialogPlus dialogPlus = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(view))
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setExpanded(false)
                .create();
        TextView tv = (TextView) view.findViewById(R.id.tv_info);
        tv.setText(msg);
        TextView tv_know = (TextView) view.findViewById(R.id.tv_know);
        tv_know.setText("好的");
        tv_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, ChangePwdActivity.class);
                intent.putExtra("flag", 3);
                startActivity(intent);
                dialogPlus.dismiss();
            }
        });
        dialogPlus.show();
    }
}
