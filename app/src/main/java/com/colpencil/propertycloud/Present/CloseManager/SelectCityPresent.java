package com.colpencil.propertycloud.Present.CloseManager;

import com.colpencil.propertycloud.Bean.AreaInfo;
import com.colpencil.propertycloud.Bean.Building;
import com.colpencil.propertycloud.Bean.CityInfo;
import com.colpencil.propertycloud.Bean.Province;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RoomInfo;
import com.colpencil.propertycloud.Bean.Unit;
import com.colpencil.propertycloud.Bean.Village;
import com.colpencil.propertycloud.Model.CloseManager.ChangeAddressModel;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IChangeAddressModel;
import com.colpencil.propertycloud.View.Imples.ChangeAddressView;
import com.colpencil.propertycloud.View.Imples.SelectCityView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * @author 陈 宝
 * @Description:报修详情
 * @Email 1041121352@qq.com
 * @date 2016/11/19
 */
public class SelectCityPresent extends ColpencilPresenter<SelectCityView> {

    private IChangeAddressModel model;

    public SelectCityPresent() {
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

    /**
     * 获取区
     *
     * @param cityId
     */
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

}
