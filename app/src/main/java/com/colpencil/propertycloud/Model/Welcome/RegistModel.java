package com.colpencil.propertycloud.Model.Welcome;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.LoginInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Model.Imples.IRegistModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.Md5Utils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 注册业务逻辑
 * @Email DramaScript@outlook.com
 * @date 2016/7/17
 */
public class RegistModel implements IRegistModel {


    private Observable<ResultInfo> resultInfoObservable;

    private Observable<ResultInfo<LoginInfo>> login;
    private Observable<ResultInfo> getCode;

    @Override
    public void regist(String mobile, String passWord, String validcode, String conpassword) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("password", passWord);
        params.put("validcode", validcode);
        params.put("conpassword", conpassword);
        params.put("communityId", SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).getString("comuid"));
        resultInfoObservable = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .regist(params)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<ResultInfo> subscriber) {
        resultInfoObservable.subscribe(subscriber);
    }

    @Override
    public void loginServer(String mobile, String passWord) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("password", passWord);
        login = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .login(params)
                .map(new Func1<ResultInfo<LoginInfo>, ResultInfo<LoginInfo>>() {
                    @Override
                    public ResultInfo<LoginInfo> call(ResultInfo<LoginInfo> resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo<LoginInfo>> subscriber) {
        login.subscribe(subscriber);
    }

    @Override
    public void getCode(int flag, String mobile) {
        getCode = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getCode(mobile, flag, 0)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCode(Subscriber<ResultInfo> subscriber) {
        getCode.subscribe(subscriber);
    }
}