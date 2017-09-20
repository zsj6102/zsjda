package com.colpencil.tenement.View.Activitys.Welcome;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.TenementComp;
import com.colpencil.tenement.Bean.UserInfo;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Present.Welcome.LoginPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Ui.BottomDialog;
import com.colpencil.tenement.View.Activitys.MainActivity;
import com.colpencil.tenement.View.Imples.LoginView;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ClickUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.Md5Utils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * @author 汪 亮
 * @Description: 登陆界面
 * @Email DramaScript@outlook.com
 * @date 2016/7/15
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_login
)
public class LoginActivity extends ColpencilActivity implements LoginView {

    @Bind(R.id.et_acount)
    EditText et_acount;

    @Bind(R.id.et_passWord)
    EditText et_passWord;

    @Bind(R.id.btn_login)
    Button btn_login;

    @Bind(R.id.rl_select_comp)
    RelativeLayout rl_select_comp;

    @Bind(R.id.tv_select)
    TextView tv_select;

    @Bind(R.id.tv_forget)
    TextView tv_forget;


    private LoginPresent loginPresent;
    private String acount;
    private String passWord;
    private int propertyId;

    private String communityId;

    @Override
    protected void initViews(View view) {
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        String acount = SharedPreferencesUtil.getInstance(this).getString("acount");
        String passWord = SharedPreferencesUtil.getInstance(this).getString("passWord");
        String property = SharedPreferencesUtil.getInstance(this).getString("property");
        propertyId = SharedPreferencesUtil.getInstance(this).getInt("propertyId", 0);
        et_acount.setText(acount);
        et_passWord.setText(passWord);
        tv_select.setText(property);

        //登陆
        btn_login.setOnClickListener((v -> {

            if (ClickUtils.isFastDoubleClick() == false) {

                this.acount = et_acount.getText().toString().trim();
                this.passWord = et_passWord.getText().toString().trim();
                if (TextUtils.isEmpty(this.acount)) {
                    ToastTools.showShort(LoginActivity.this, "请输入用户名！");
                    return;
                } else if (TextUtils.isEmpty(this.passWord)) {
                    ToastTools.showShort(LoginActivity.this, "请输入密码！");
                    return;
                } else {
                    if (TextUtils.isEmpty(this.propertyId + "")) {
                        ToastTools.showShort(LoginActivity.this, "请选择物业公司！");
                        return;
                    }
//                    if (TextStringUtils.isMobileNO(acount)==false){
//                        ToastTools.showShort(this,"您未输入手机号码不合法");
//                        return;
//                    }
                    DialogTools.showLoding(LoginActivity.this, "登陆", "正在登陆中。。。");
                    String phoneId = getPhoneId();
                    SharedPreferencesUtil.getInstance(this).setString("deviceId",phoneId);
                    loginPresent.loginToServer(this.acount, Md5Utils.encode(this.passWord), this.propertyId + "", phoneId);
                }
            }

        }));

        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickUtils.isFastDoubleClick() == false) {
                    Intent intent = new Intent(LoginActivity.this, ForgetPwdActivity.class);
                    intent.putExtra("flag", 3);
                    startActivity(intent);
                }
//                Intent intent = new Intent(LoginActivity.this, TestHotFix.class);
//                startActivity(intent);
            }
        });

        rl_select_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickUtils.isFastDoubleClick() == false) {
                    loginPresent.getCompList();
                    DialogTools.showLoding(LoginActivity.this, "温馨提示", "正在获取物业公司。。。");
                }
            }
        });
    }

    /**
     * 登陆环信
     *
     * @param talkname
     * @param pwd
     */
    private void loginHuanXin(String talkname, String pwd) {
        ColpencilLogger.e("talkname=" + talkname + "pwd=" + pwd);
        if (TextUtils.isEmpty(talkname) && TextUtils.isEmpty(talkname)) {
            Toast.makeText(getApplicationContext(), "账号或密码不能为空！", Toast.LENGTH_LONG).show();
            return;
        }
        //这里先进行自己服务器的登录操作
        //自己服务器登录成功后再执行环信服务器的登录操作
        EMChatManager.getInstance().login(talkname, pwd, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        EMGroupManager.getInstance().loadAllGroups();
                        EMChatManager.getInstance().loadAllConversations();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.e("TAG", "登陆聊天服务器中 " + "progress:" + progress + " status:" + status);
            }

            @Override
            public void onError(int code, String message) {
                ColpencilLogger.e("登录在操作中返回失败 " + code + "即时通讯登陆失败" + message);
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "即时通讯登陆失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        loginPresent = new LoginPresent();
        return loginPresent;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void login(boolean isLogin) {
        if (isLogin == true) {
            ToastTools.showShort(this, true, "登陆成功");
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            ToastTools.showShort(this, false, "登陆失败");
        }
        DialogTools.dissmiss();
    }

    @Override
    public void loginResult(EntityResult<UserInfo> result) {
        ColpencilLogger.e("登陆信息：" + result.toString());
        DialogTools.dissmiss();
        if (result.getCode() == 0) {
            loginHuanXin(result.getData().getTalkname(), passWord);
//            loginHuanXin("test","123456");
            ToastTools.showShort(this, true, result.getMessage());

            UserInfo userinfo = result.getData();
            SharedPreferencesUtil.getInstance(this).setInt(StringConfig.USERID, userinfo.getUser_id());
            SharedPreferencesUtil.getInstance(this).setBoolean("isLogin", true);
            SharedPreferencesUtil.getInstance(this).setString("acount", acount);
            SharedPreferencesUtil.getInstance(this).setString("passWord", passWord);
            SharedPreferencesUtil.getInstance(this).setString("talkname", result.getData().getTalkname());
            SharedPreferencesUtil.getInstance(this).setString("nick_name", result.getData().getNickname());
            ColpencilLogger.e("nick_name=" + result.getData().getNickname());
            SharedPreferencesUtil.getInstance(this).setInt("user_id", result.getData().getUser_id());

            SharedPreferencesUtil.getInstance(this).setString("plot", result.getData().getCommunityName());
            SharedPreferencesUtil.getInstance(this).setString("plotId", result.getData().getCommunityId()+"");
            SharedPreferencesUtil.getInstance(this).setString("shortName", result.getData().getShortName());

            JPushInterface.setAlias(this, //上下文对象
                    acount, //别名
                    new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {
                            ColpencilLogger.e("set alias result is=" + i);
                        }
                    });
//            loginPresent.loadVillage();
            mAm.finishAllActivity();
            startActivity(new Intent(this, MainActivity.class));

        } else {
            ToastTools.showShort(this, false, result.getMessage());
            SharedPreferencesUtil.getInstance(this).setBoolean("isLogin", false);
        }
    }

    @Override
    public void compList(ListCommonResult<TenementComp> result) {
        int code = result.getCode();
        String message = result.getMessage();
        switch (code) {
            case 0:
                List<String> list = new ArrayList<>();
                for (TenementComp tenementComp : result.getData()) {
                    list.add(tenementComp.propertyName);
                }
                new BottomDialog.Builder(LoginActivity.this)
                        .setTitle("选择物业公司")
                        .setDataList(list)
                        .setPositiveText("确认")
                        .setNegativeText("取消")
                        .onPositive(dialog -> {
                            tv_select.setText(list.get(dialog.position));
                            ColpencilLogger.e("position=" + dialog.position);
                            propertyId = result.getData().get(dialog.position).propertyId;
                            SharedPreferencesUtil.getInstance(LoginActivity.this).setString("property", list.get(dialog.position));
                            SharedPreferencesUtil.getInstance(LoginActivity.this).setInt("propertyId", result.getData().get(dialog.position).propertyId);
                        })
                        .onNegative(dialog -> {

                        }).show();
                break;
            case 1:
                ToastTools.showShort(this, false, message);
                break;
            case 2:
                ToastTools.showShort(LoginActivity.this, message);
                break;
        }
        DialogTools.dissmiss();
    }

    @Override
    public void loadCommunity(ListCommonResult<Village> result) {
        int code = result.getCode();
        String message = result.getMessage();
        switch (code) {
            case 0:
                List<Village> data = result.getData();
                SharedPreferencesUtil.getInstance(this).setString("plot", data.get(0).plot);
                SharedPreferencesUtil.getInstance(this).setString("plotId", data.get(0).plotId);
                SharedPreferencesUtil.getInstance(this).setString("shortName", data.get(0).shortName);
                HashSet<String> set = new HashSet<>();
                set.add(data.get(0).plotId);
                JPushInterface.setTags(this, set, new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        ColpencilLogger.e("设置Tag=" + i);
                    }
                });
                break;
            case 1:
                ToastTools.showShort(this, false, message);
                break;
            case 2:
                ToastTools.showShort(this, false, message);
                break;
            case 3:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    @Override
    public void loadError(String msg) {
        DialogTools.dissmiss();
        ToastTools.showShort(this, false, "请求失败！");
    }

    /**
     * 获取设备的唯一标识
     * @return
     */
    private String getPhoneId() {
        //IMEI
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        ColpencilLogger.e("IMEI=" + szImei);

        //. Pseudo-Unique ID
        String m_szDevIDShort = "35" + //we make this look like a valid IMEI

                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 digits

        //The Android ID
        String m_szAndroidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        //The WLAN MAC Address string
        WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
        //  The BT MAC Address string
        BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
        m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String m_szBTMAC = m_BluetoothAdapter.getAddress();

        String m_szLongID = szImei + m_szDevIDShort
                + m_szAndroidID + m_szWLANMAC + m_szBTMAC;

        return Md5Utils.encode(m_szLongID);
    }

}