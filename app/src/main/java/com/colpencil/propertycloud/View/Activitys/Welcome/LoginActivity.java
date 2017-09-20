package com.colpencil.propertycloud.View.Activitys.Welcome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.propertycloud.Bean.LoginInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Present.LoginPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Home.VilageSelectActivity;
import com.colpencil.propertycloud.View.Activitys.HomeActivity;
import com.colpencil.propertycloud.View.Imples.LoginView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.Md5Utils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.umeng.analytics.MobclickAgent;

import java.util.Set;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * @author 陈 宝
 * @Description:登录注册界面
 * @Email 1041121352@qq.com
 * @date 2016/11/18
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_logins
)
public class LoginActivity extends ColpencilActivity implements View.OnClickListener, LoginView {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.et_acount)
    EditText et_acount;
    @Bind(R.id.et_passWord)
    EditText et_passWord;
    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.tv_foget)
    TextView tv_foget;
    @Bind(R.id.tv_register)
    TextView tv_register;

    private LoginPresent present;
    private Intent startIntent;
    public static String StartType = "StartType";
    public static String StartType_Requestlogin = "toLogin";

    @Override
    protected void initViews(View view) {
        startIntent = getIntent();

        setStatusBaropen(true);
        setStatecolor(Color.parseColor("#0f3890"));
        tv_title.setText("登录");
        ll_left.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_foget.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        et_acount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String mobile = et_acount.getText().toString();
                    if (TextUtils.isEmpty(mobile)) {
                        ToastTools.showShort(LoginActivity.this, "手机号不能为空");
                    } else if (!TextStringUtils.isMobileNO(mobile)) {
                        ToastTools.showShort(LoginActivity.this, "请输入正确的手机号");
                    } else {
                        //不做操作
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
                        ToastTools.showShort(LoginActivity.this, "密码不能为空");
                    }
                }
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new LoginPresent();
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
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_foget:
                Intent intent = new Intent(this, ChangePwdActivity.class);
                intent.putExtra("flag", 3);
                startActivity(intent);
                break;
            case R.id.tv_register:
                Intent intent1 = new Intent(this, RegisterActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void login() {
        String mobile = et_acount.getText().toString();
        String password = et_passWord.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            ToastTools.showShort(LoginActivity.this, "手机号不能为空");
        } else if (!TextStringUtils.isMobileNO(mobile)) {
            ToastTools.showShort(LoginActivity.this, "请输入正确的手机号");
        } else if (TextUtils.isEmpty(password)) {
            ToastTools.showShort(LoginActivity.this, "密码不能为空");
        } else {
            DialogTools.showLoding(this, "登录", "正在登录中。。。");
            present.loginToServer(mobile, Md5Utils.encode(password));
        }
    }

    @Override
    public void login(boolean isLogin) {
    }

    @Override
    public void loginForServer(ResultInfo<LoginInfo> resultInfo) {
        DialogTools.dissmiss();
        String message = resultInfo.message;
        if (resultInfo.code == 0) {
            MobclickAgent.onProfileSignIn(et_acount.getText().toString());
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
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();   //登录成功
            RxBusMsg msg = new RxBusMsg();
            msg.setType(0);
            RxBus.get().post("mine", msg);
            RxBusMsg rxBusMsg = new RxBusMsg();
            rxBusMsg.setType(4);
            RxBus.get().post(VilageSelectActivity.class.getSimpleName(), rxBusMsg);

            JPushInterface.setAlias(this, //上下文对象
                    et_acount.getText().toString(), //别名
                    new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {
                            ColpencilLogger.e("set alias result is=" + i);
                        }
                    });


            if (StartType_Requestlogin.equals(startIntent.getStringExtra(StartType))) {
                ColpencilLogger.d(getClass().getSimpleName() + " login for result:");
                setResult(1);
                finish();
            } else if (getIntent().getIntExtra("requestLogin", 0) == 100) {
                Intent intent = new Intent();
                setResult(100, intent);
                finish();
            } else {
                Intent intent = new Intent(this, HomeActivity.class);
                ColpencilFrame.getInstance().finishAllActivity();
                startActivity(intent);
            }
        } else {
            ToastTools.showShort(this, message);
            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setBoolean("isLogin", false);
        }
    }
}
