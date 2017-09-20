package com.colpencil.tenement.Model.Home;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Result;
import com.colpencil.tenement.Bean.WaterInfo;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.Home.IWriteWatermeterModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:开始抄表
 * @Email DramaScript@outlook.com
 * @date 2016/9/2
 */
public class WriteWatermeterModel implements IWriteWatermeterModel {

    private Observable<EntityResult<String>> observable;
    private Observable<EntityResult<WaterInfo>> lastObservable;

    @Override
    public void submit(String roomId, String ownerName, String waterId, String monthCoast, int type,String lastRecord,String communityId,String recordId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("roomId", roomId);
//        params.put("ownerName", ownerName);
        params.put("waterId", waterId);
        params.put("monthCoast", monthCoast);
        params.put("type", type + "");
        params.put("lastRecord", lastRecord);
        params.put("recordId", recordId);
//        params.put("communityId", communityId);
        observable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .submitWater(params)
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public void sub(Observer<EntityResult<String>> observer) {
        observable.subscribe(observer);
    }

    @Override
    public void getLast(String devId,String type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("devId", devId);
        params.put("type", type);
        lastObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .getLast(params)
                .map(new Func1<EntityResult<WaterInfo>, EntityResult<WaterInfo>>() {
                    @Override
                    public EntityResult<WaterInfo> call(EntityResult<WaterInfo> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLast(Subscriber<EntityResult<WaterInfo>> subscriber) {
        lastObservable.subscribe(subscriber);
    }
}
