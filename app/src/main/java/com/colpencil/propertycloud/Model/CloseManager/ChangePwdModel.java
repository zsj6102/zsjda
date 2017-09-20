package com.colpencil.propertycloud.Model.CloseManager;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IChangePwdMode;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 忘记密码/修改密码
 * @Email DramaScript@outlook.com
 * @date 2016/11/10
 */
public class ChangePwdModel implements IChangePwdMode {

    private Observable<ResultInfo> getCode;
    private Observable<ResultInfo> observable;

    @Override
    public void modPwd(String mobile, String wtd, String validCd, String password, String conpassword) {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("wtd", wtd);
        map.put("validCd", validCd);
        map.put("password", password);
        map.put("conpasswd", conpassword);
        observable = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .modPwd(map)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subModPwd(Subscriber<ResultInfo> subscriber) {
        observable.subscribe(subscriber);
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