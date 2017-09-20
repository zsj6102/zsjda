package com.colpencil.tenement.Model.Home;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.Home.ISettingModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 设置
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/29
 */
public class SetingModel implements ISettingModel {

    private Observable<EntityResult<String>> resultObservable;

    @Override
    public void loginOut(String communityId) {
        HashMap<String,String> map = new HashMap<>();
        resultObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .loginOut(map)
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<EntityResult<String>> subscriber) {
        resultObservable.subscribe(subscriber);
    }
}
