package com.colpencil.tenement.Model.Home;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.LastRecord;
import com.colpencil.tenement.Bean.Result;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.Home.IStartPolingModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 开始保养/巡检/维修
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/23
 */
public class StartPolingModel implements IStartPolingModel {

    private Observable<EntityResult<LastRecord>> resultObservable;
    private Observable<Result> observable;
    private Observable<Result> linkObservable;


    @Override
    public void post(int type, String eqId, String communityId, String currTime, String content, String lastTime,
                     String lastEmp,String maintainId,String eqType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type+"");
        params.put("eqId", eqId);
//        params.put("communityId", communityId);
        params.put("content", content);
        params.put("lastTime", lastTime);
        params.put("lastEmp", lastEmp);
        params.put("maintainId", maintainId);
        params.put("eqType", eqType);
        observable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .postRecord(params)
                .map(new Func1<Result, Result>() {
                    @Override
                    public Result call(Result result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<Result> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void getLast(String eqId,int type,String eqType) {
        resultObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .getLastRecord(eqId,type,eqType)
                .map(new Func1<EntityResult<LastRecord>, EntityResult<LastRecord>>() {
                    @Override
                    public EntityResult<LastRecord> call(EntityResult<LastRecord> lastRecordEntityResult) {
                        return lastRecordEntityResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLast(Subscriber<EntityResult<LastRecord>> subscriber) {
        resultObservable.subscribe(subscriber);
    }

    /**
     * 关联设备
     * @param devId
     * @param orderId
     */
    @Override
    public void linkDev(String devId, String orderId,String content,String eqType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("devId", devId);
        params.put("orderId", orderId);
        params.put("content", content);
        params.put("eqType", eqType);
        linkObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .linkDev(params)
                .map(new Func1<Result, Result>() {
                    @Override
                    public Result call(Result result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLinkDev(Subscriber<Result> subscriber) {
        linkObservable.subscribe(subscriber);
    }
}
