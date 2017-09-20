package com.colpencil.propertycloud.Model.Home;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.Imples.Home.IVilageSelectModel;
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
 * @Description: 小区选择
 * @Email DramaScript@outlook.com
 * @date 2016/9/9
 */
public class VilageSelectModel implements IVilageSelectModel {

    private Observable<ResultListInfo<CellInfo>> resultInfoObservable;
    private Observable<ResultListInfo<CellInfo>> cells;
    private Observable<ResultInfo<String>> selectOb;
    private Observable<ResultListInfo<CellInfo>> cellByName;

    @Override
    public void getList(int provinceId, int cityId, int areaId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("provinceId", provinceId + "");
        params.put("cityId", cityId + "");
        params.put("regionId", areaId + "");
        resultInfoObservable = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadVillageById(params)
                .map(new Func1<ResultListInfo<CellInfo>, ResultListInfo<CellInfo>>() {
                    @Override
                    public ResultListInfo<CellInfo> call(ResultListInfo<CellInfo> resultListInfo) {
                        return resultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultListInfo<CellInfo>> subscriber) {
        resultInfoObservable.subscribe(subscriber);
    }

    @Override
    public void select(String memberId, String comuId) {
        selectOb = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .selectVilage(memberId, comuId)
                .map(new Func1<ResultInfo<String>, ResultInfo<String>>() {
                    @Override
                    public ResultInfo<String> call(ResultInfo<String> resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subSelect(Subscriber<ResultInfo<String>> subscriber) {
        selectOb.subscribe(subscriber);
    }

    @Override
    public void getOrderCell(boolean isProperty) {
        cells = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getSelectVilage(1, 100, isProperty)
                .map(new Func1<ResultListInfo<CellInfo>, ResultListInfo<CellInfo>>() {
                    @Override
                    public ResultListInfo<CellInfo> call(ResultListInfo<CellInfo> resultListInfo) {
                        return resultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subOrderCells(Subscriber<ResultListInfo<CellInfo>> subscriber) {
        cells.subscribe(subscriber);
    }

    @Override
    public void loadCellByName(String cityName) {
        HashMap<String, String> params = new HashMap<>();
        params.put("regionName", cityName);
        cellByName = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadCellByName(params)
                .map(new Func1<ResultListInfo<CellInfo>, ResultListInfo<CellInfo>>() {
                    @Override
                    public ResultListInfo<CellInfo> call(ResultListInfo<CellInfo> resultListInfo) {
                        return resultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLoadCellByName(Subscriber<ResultListInfo<CellInfo>> subscriber) {
        cellByName.subscribe(subscriber);
    }
}
