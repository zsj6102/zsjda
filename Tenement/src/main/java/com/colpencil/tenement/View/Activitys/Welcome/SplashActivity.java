package com.colpencil.tenement.View.Activitys.Welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.MainActivity;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

public class SplashActivity extends Activity {

    private Intent intent;

    private static final int sleepTime = 2000;
    private String talkname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Thread(){

            private String passWord;

            @Override
            public void run() {
                super.run();
                SystemClock.sleep(2000);
                boolean isLogin = SharedPreferencesUtil.getInstance(SplashActivity.this).getBoolean("isLogin", false);
                boolean guide = SharedPreferencesUtil.getInstance(SplashActivity.this).getBoolean("guide", false);
                talkname = SharedPreferencesUtil.getInstance(SplashActivity.this).getString("talkname");
                passWord = SharedPreferencesUtil.getInstance(SplashActivity.this).getString("passWord");
                if (guide==false){
                    intent = new Intent(SplashActivity.this, GuideActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    if (isLogin==true){
                        if (!TextUtils.isEmpty(talkname)&&!TextUtils.isEmpty(passWord)){
                            if (!EMChatManager.getInstance().isConnected()) {
                                loginHuanXin(talkname,passWord);
                            }
                        }
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        }.start();

    }


    /**
     * 登陆环信
     * @param talkname
     * @param pwd
     */
    private void loginHuanXin(String talkname,String pwd){
        ColpencilLogger.e("talkname="+talkname);
        if (TextUtils.isEmpty(talkname)||TextUtils.isEmpty(pwd)){
            Toast.makeText(getApplicationContext(),"账号或密码不能为空！",Toast.LENGTH_LONG).show();
            return ;
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
                ColpencilLogger.e("登录在操作中返回失败 " + code);
                runOnUiThread(new Runnable() {
                    public void run() {
                        ToastTools.showShort(SplashActivity.this,true,"即时通讯登陆失败" + message);
                    }
                });
            }
        });
    }
}
