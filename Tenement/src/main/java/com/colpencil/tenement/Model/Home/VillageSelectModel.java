package com.colpencil.tenement.Model.Home;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Record;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.Home.IVillageSelectModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 小区选择
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/14
 */
public class VillageSelectModel implements IVillageSelectModel {

    private Observable<ListCommonResult<Village>> observable;
    private Observable<ListCommonResult<Record>> listCommonResultObservable;

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

    @Override
    public void loadRecord(String communityId) {
        HashMap<String,String> map = new HashMap<>();
        map.put("communityId",communityId);
        listCommonResultObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .getMarker(map)
                .map(new Func1<ListCommonResult<Record>, ListCommonResult<Record>>() {
                    @Override
                    public ListCommonResult<Record> call(ListCommonResult<Record> recordListCommonResult) {
                        return recordListCommonResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subRecord(Subscriber<ListCommonResult<Record>> subscriber) {
        listCommonResultObservable.subscribe(subscriber);
    }
}
