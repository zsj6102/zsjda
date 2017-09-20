package com.colpencil.tenement.Model.Welcome;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Result;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.Welcome.IForgetPwdModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 忘记密码
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/29
 */
public class ForgetPwdModel implements IForgetPwdModel {

    private Observable<EntityResult<String>> changeObservable;
    private Observable<Result> getCodeObser;

    @Override
    public void change(String phone, String yan, String pwd, String newPwe,String type) {
        HashMap<String,String> map = new HashMap<>();
        map.put("mobile",phone);
        map.put("password",pwd);
        map.put("validateCode",yan);
        map.put("type",type);
        changeObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .changePwd(map)
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subChange(Subscriber<EntityResult<String>> subscriber) {
        changeObservable.subscribe(subscriber);
    }

    @Override
    public void getCode(String phone,int flag) {
        HashMap<String,String> map = new HashMap<>();
        map.put("mobile",phone);
        map.put("flag",flag+"");
        map.put("terminal",1+"");
        getCodeObser = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .getCode(map)
                .map(new Func1<Result, Result>() {
                    @Override
                    public Result call(Result result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subGet(Subscriber<Result> subscriber) {
        getCodeObser.subscribe(subscriber);
    }
}
