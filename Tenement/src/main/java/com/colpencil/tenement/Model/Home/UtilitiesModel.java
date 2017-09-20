package com.colpencil.tenement.Model.Home;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.Building;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.ReadingNum;
import com.colpencil.tenement.Bean.Room;
import com.colpencil.tenement.Bean.Unit;
import com.colpencil.tenement.Bean.UtilitiesRecord;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.Home.IUtilitiesModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:水电费抄表
 * @Email DramaScript@outlook.com
 * @date 2016/9/2
 */
public class UtilitiesModel implements IUtilitiesModel {

    private Observable<ListCommonResult<Building>> buildObservable;
    private Observable<ListCommonResult<Room>> roomObservable;
    private Observable<ListCommonResult<UtilitiesRecord>> recordObservable;
    private Observable<EntityResult<ReadingNum>> numObservable;
    private Observable<ListCommonResult<Village>> villageObser;
    private Observable<ListCommonResult<Unit>> uintObser;

    /**
     * 加载楼宇
     */
    @Override
    public void loadBuilds(String communityId) {
        buildObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .loadBuilds(communityId)
                .map(new Func1<ListCommonResult<Building>, ListCommonResult<Building>>() {
                    @Override
                    public ListCommonResult<Building> call(ListCommonResult<Building> buildingListCommonResult) {
                        return buildingListCommonResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subBuilds(Observer<ListCommonResult<Building>> observer) {
        buildObservable.subscribe(observer);
    }

    /**
     * 加载单元
     * @param buildingId
     */
    @Override
    public void loadUnit(String buildingId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("buildingId",buildingId);
        uintObser = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .getUnit(params)
                .map(new Func1<ListCommonResult<Unit>, ListCommonResult<Unit>>() {
                    @Override
                    public ListCommonResult<Unit> call(ListCommonResult<Unit> unitListCommonResult) {
                        return unitListCommonResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subUnit(Subscriber<ListCommonResult<Unit>> subscriber) {
        uintObser.subscribe(subscriber);
    }

    /**
     * 获取房间
     * @param buildId
     */
    @Override
    public void loadRooms(String buildId) {
        roomObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .loadRooms(buildId)
                .map(new Func1<ListCommonResult<Room>, ListCommonResult<Room>>() {
                    @Override
                    public ListCommonResult<Room> call(ListCommonResult<Room> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subRooms(Observer<ListCommonResult<Room>> observer) {
        roomObservable.subscribe(observer);
    }

    /**
     *
     * @param self  0:查询所有数据,1:只查询自己抄表数据
     * @param type  0:水表,1:电表，多个用逗号隔开
     * @param communityId  小区id
     * @param buildingId   楼宇id
     * @param houseCode   房间id
     * @param page
     * @param pageSize
     * @param dan   单元id
     */
    @Override
    public void loadRecord(int self,String type,String dan,String communityId,String buildingId, String houseCode, int page, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("self",self+"");
        params.put("type",type);
        params.put("unitId",dan);
        params.put("communityId",communityId);
        params.put("buildingId",buildingId);
        params.put("houseId",houseCode);
        params.put("page",page+"");
        params.put("pageSize",pageSize+"");
        recordObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .loadUtilitiesRecord(params)
                .map(new Func1<ListCommonResult<UtilitiesRecord>, ListCommonResult<UtilitiesRecord>>() {
                    @Override
                    public ListCommonResult<UtilitiesRecord> call(ListCommonResult<UtilitiesRecord> result) {
                        ColpencilLogger.e("抄表记录的信息：" + result.getData().size());
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subRecord(Observer<ListCommonResult<UtilitiesRecord>> observer) {
        recordObservable.subscribe(observer);
    }

    @Override
    public void loadNum(String communityId,String buildingId,String unitId,String houseId,String type) {
        HashMap<String,String> map = new HashMap<>();
        map.put("communityId",communityId);
        map.put("buildingId",buildingId);
        map.put("unitId",unitId);
        map.put("houseId",houseId);
        map.put("type",type);
        numObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .loadUtilitiesNum(map)
                .map(new Func1<EntityResult<ReadingNum>, EntityResult<ReadingNum>>() {
                    @Override
                    public EntityResult<ReadingNum> call(EntityResult<ReadingNum> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subNum(Observer<EntityResult<ReadingNum>> observer) {
        numObservable.subscribe(observer);
    }

    @Override
    public void loadVillage() {
        HashMap<String,String> map = new HashMap<>();
        villageObser = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
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
        villageObser.subscribe(subscriber);
    }
}
