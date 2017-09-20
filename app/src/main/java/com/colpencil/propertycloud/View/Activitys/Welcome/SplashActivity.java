package com.colpencil.propertycloud.View.Activitys.Welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.LoginInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Service.LocationService;
import com.colpencil.propertycloud.View.Activitys.HomeActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.Md5Utils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SplashActivity extends Activity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (SharedPreferencesUtil.getInstance(SplashActivity.this).getBoolean("guide", false)
                && TextUtils.isEmpty(SharedPreferencesUtil.getInstance(this).getString("shortnm"))) {
            startService(new Intent(this, LocationService.class));
        }
        login();
        new Thread() {
            @Override
            public void run() {
                super.run();
                SystemClock.sleep(2000);
                boolean guide = SharedPreferencesUtil.getInstance(SplashActivity.this).getBoolean("guide", false);
                if (guide == false) {
                    intent = new Intent(SplashActivity.this, GuideActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
    }

    public void login() {
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", SharedPreferencesUtil.getInstance(this).getString("mobile"));
        params.put("password", Md5Utils.encode(SharedPreferencesUtil.getInstance(this).getString("passWord")));
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .login(params)
                .map(new Func1<ResultInfo<LoginInfo>, ResultInfo<LoginInfo>>() {
                    @Override
                    public ResultInfo<LoginInfo> call(ResultInfo<LoginInfo> loginInfoResultInfo) {
                        return loginInfoResultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<LoginInfo>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultInfo<LoginInfo> resultInfo) {
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
                            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("addr", resultInfo.data.addr);
                            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setBoolean("isProprietor", resultInfo.data.isProprietor);
                            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setInt("userType", resultInfo.data.userType);
                            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setBoolean("hasShen", resultInfo.data.hasShen);

                            RxBusMsg msg = new RxBusMsg();
                            msg.setType(0);
                            RxBus.get().post("mine", msg);
                        } else {
                            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setBoolean("isLogin", false);
                        }
                    }
                });
    }

}
