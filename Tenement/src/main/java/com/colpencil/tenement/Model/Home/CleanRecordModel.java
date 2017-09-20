package com.colpencil.tenement.Model.Home;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.CleanRecord;
import com.colpencil.tenement.Bean.CleanRecordResult;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.Home.ICleanRecordModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 获取巡逻/保洁记录
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/17
 */
public class CleanRecordModel implements ICleanRecordModel {

    private Observable<ListCommonResult<CleanRecord>> resultObservable;
    private Observable<ListCommonResult<Village>> observable;

    /**
     * 获取保洁记录
     *
     * @param type
     * @param page
     * @param pageSize
     */
    @Override
    public void loadRecords(String communityId,int type, int page, int pageSize,String beginTime,String endTime, int self) {
        resultObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .loadCleanRecord(communityId,type, page, pageSize,beginTime,endTime,self)
                .map(new Func1<ListCommonResult<CleanRecord>, ListCommonResult<CleanRecord>>() {
                    @Override
                    public ListCommonResult call(ListCommonResult<CleanRecord> listCommonResult) {
                        return listCommonResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subRecords(Observer<ListCommonResult<CleanRecord>> observer) {
        resultObservable.subscribe(observer);
    }

    @Override
    public void loadVillage() {
        HashMap<String,String> map = new HashMap<>();
        observable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .getVillage(map)
                .map(new Func1<ListCommonResult<Village>, ListCommonResult<Village>>() {
                    @Override
                    public ListCommonResult<Village> call(ListCommonResult<Village> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subVillage(Subscriber<ListCommonResult<Village>> subscriber) {
        observable.subscribe(subscriber);
    }
}
