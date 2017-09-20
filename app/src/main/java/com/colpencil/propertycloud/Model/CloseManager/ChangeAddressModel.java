package com.colpencil.propertycloud.Model.CloseManager;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.AreaInfo;
import com.colpencil.propertycloud.Bean.Building;
import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.Bean.CityInfo;
import com.colpencil.propertycloud.Bean.Province;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RoomInfo;
import com.colpencil.propertycloud.Bean.Unit;
import com.colpencil.propertycloud.Bean.Village;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IChangeAddressModel;
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
 * @author 陈 宝
 * @Description:报修详情
 * @Email 1041121352@qq.com
 * @date 2016/11/19
 */
public class ChangeAddressModel implements IChangeAddressModel {

    private Observable<ResultListInfo<CellInfo>> observable;
    private Observable<ResultListInfo<Building>> building;
    private Observable<ResultListInfo<Unit>> unit;
    private Observable<ResultListInfo<RoomInfo>> roomInfo;
    private Observable<ResultListInfo<Province>> province;
    private Observable<ResultListInfo<CityInfo>> citys;
    private Observable<ResultListInfo<AreaInfo>> areas;

    @Override
    public void loadVillage(int provinceId, int cityId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("provinceId", provinceId + "");
        params.put("cityId", cityId + "");
        observable = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadVillageById(params)
                .map(new Func1<ResultListInfo<CellInfo>, ResultListInfo<CellInfo>>() {
                    @Override
                    public ResultListInfo<CellInfo> call(ResultListInfo<CellInfo> villageResultListInfo) {
                        return villageResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultListInfo<CellInfo>> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void loadBuilding(int id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("communityId", id + "");
        building = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadBuildingById(params)
                .map(new Func1<ResultListInfo<Building>, ResultListInfo<Building>>() {
                    @Override
                    public ResultListInfo<Building> call(ResultListInfo<Building> info) {
                        return info;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subBuild(Subscriber<ResultListInfo<Building>> subscriber) {
        building.subscribe(subscriber);
    }

    @Override
    public void loadUnit(int id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("buildingId", id + "");
        unit = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadUnitById(params)
                .map(new Func1<ResultListInfo<Unit>, ResultListInfo<Unit>>() {
                    @Override
                    public ResultListInfo<Unit> call(ResultListInfo<Unit> unitResultListInfo) {
                        return unitResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subUnit(Subscriber<ResultListInfo<Unit>> subscriber) {
        unit.subscribe(subscriber);
    }

    @Override
    public void loadHouse(int id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("unitId", id + "");
        roomInfo = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadRoomsById(params)
                .map(new Func1<ResultListInfo<RoomInfo>, ResultListInfo<RoomInfo>>() {
                    @Override
                    public ResultListInfo<RoomInfo> call(ResultListInfo<RoomInfo> roomInfoResultListInfo) {
                        return roomInfoResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subHouse(Subscriber<ResultListInfo<RoomInfo>> subscriber) {
        roomInfo.subscribe(subscriber);
    }

    @Override
    public void loadProvince() {
        HashMap<String, String> params = new HashMap<>();
        province = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadProvince(params)
                .map(new Func1<ResultListInfo<Province>, ResultListInfo<Province>>() {
                    @Override
                    public ResultListInfo<Province> call(ResultListInfo<Province> provinceResultListInfo) {
                        return provinceResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subProvince(Subscriber<ResultListInfo<Province>> subscriber) {
        province.subscribe(subscriber);
    }

    @Override
    public void loadCity(int id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("provinceId", id + "");
        citys = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadCity(params)
                .map(new Func1<ResultListInfo<CityInfo>, ResultListInfo<CityInfo>>() {
                    @Override
                    public ResultListInfo<CityInfo> call(ResultListInfo<CityInfo> cityInfoResultListInfo) {
                        return cityInfoResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCity(Subscriber<ResultListInfo<CityInfo>> subscriber) {
        citys.subscribe(subscriber);
    }

    @Override
    public void loadArea(int id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cityId", id + "");
        areas = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadAreasById(params)
                .map(new Func1<ResultListInfo<AreaInfo>, ResultListInfo<AreaInfo>>() {
                    @Override
                    public ResultListInfo<AreaInfo> call(ResultListInfo<AreaInfo> areaInfoResultListInfo) {
                        return areaInfoResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subArea(Subscriber<ResultListInfo<AreaInfo>> subscriber) {
        areas.subscribe(subscriber);
    }
}
