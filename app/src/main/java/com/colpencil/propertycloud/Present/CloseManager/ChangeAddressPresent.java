package com.colpencil.propertycloud.Present.CloseManager;

import com.colpencil.propertycloud.Bean.AreaInfo;
import com.colpencil.propertycloud.Bean.Building;
import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.Bean.CityInfo;
import com.colpencil.propertycloud.Bean.Province;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RoomInfo;
import com.colpencil.propertycloud.Bean.Unit;
import com.colpencil.propertycloud.Bean.Village;
import com.colpencil.propertycloud.Model.CloseManager.ChangeAddressModel;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IChangeAddressModel;
import com.colpencil.propertycloud.View.Imples.ChangeAddressView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * @author 陈 宝
 * @Description:报修详情
 * @Email 1041121352@qq.com
 * @date 2016/11/19
 */
public class ChangeAddressPresent extends ColpencilPresenter<ChangeAddressView> {

    private IChangeAddressModel model;

    public ChangeAddressPresent() {
        model = new ChangeAddressModel();
    }

    /**
     * 获取省份
     */
    public void loadProvince() {
        model.loadProvince();
        Subscriber<ResultListInfo<Province>> subscriber = new Subscriber<ResultListInfo<Province>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultListInfo<Province> province) {
                mView.loadProvince(province);
            }
        };
        model.subProvince(subscriber);
    }

    /**
     * 获取城市
     */
    public void loadCity(int id) {
        model.loadCity(id);
        Subscriber<ResultListInfo<CityInfo>> subscriber = new Subscriber<ResultListInfo<CityInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultListInfo<CityInfo> cityinfo) {
                mView.loadCitys(cityinfo);
            }
        };
        model.subCity(subscriber);
    }

    public void loadArea(int cityId) {
        model.loadArea(cityId);
        Subscriber<ResultListInfo<AreaInfo>> subscriber = new Subscriber<ResultListInfo<AreaInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultListInfo<AreaInfo> areaInfoResultListInfo) {
                mView.loadArea(areaInfoResultListInfo);
            }
        };
        model.subArea(subscriber);
    }

    /**
     * 获取小区
     */
    public void loadVillage(int provinceId, int cityId) {
        model.loadVillage(provinceId, cityId);
        Subscriber<ResultListInfo<CellInfo>> subscriber = new Subscriber<ResultListInfo<CellInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultListInfo<CellInfo> result) {
                mView.loadVillages(result);
            }
        };
        model.sub(subscriber);
    }

    /**
     * 获取建筑
     *
     * @param id
     */
    public void loadBuilding(int id) {
        model.loadBuilding(id);
        Subscriber<ResultListInfo<Building>> subscriber = new Subscriber<ResultListInfo<Building>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultListInfo<Building> result) {
                mView.loadBuilds(result);
            }
        };
        model.subBuild(subscriber);
    }

    /**
     * 获取单元
     *
     * @param id
     */
    public void loadUnit(int id) {
        model.loadUnit(id);
        Subscriber<ResultListInfo<Unit>> subscriber = new Subscriber<ResultListInfo<Unit>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultListInfo<Unit> result) {
                mView.loadUnit(result);
            }
        };
        model.subUnit(subscriber);
    }

    /**
     * 获取房间
     *
     * @param id
     */
    public void loadRooms(int id) {
        model.loadHouse(id);
        Subscriber<ResultListInfo<RoomInfo>> subscriber = new Subscriber<ResultListInfo<RoomInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultListInfo<RoomInfo> result) {
                mView.loadRooms(result);
            }
        };
        model.subHouse(subscriber);
    }
}
