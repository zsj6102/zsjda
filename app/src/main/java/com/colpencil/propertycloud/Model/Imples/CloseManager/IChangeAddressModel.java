package com.colpencil.propertycloud.Model.Imples.CloseManager;

import com.colpencil.propertycloud.Bean.AreaInfo;
import com.colpencil.propertycloud.Bean.Building;
import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.Bean.CityInfo;
import com.colpencil.propertycloud.Bean.Province;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RepairDetailVo;
import com.colpencil.propertycloud.Bean.RoomInfo;
import com.colpencil.propertycloud.Bean.Unit;
import com.colpencil.propertycloud.Bean.Village;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Subscriber;

/**
 * @author 陈 宝
 * @Description:报修详情
 * @Email 1041121352@qq.com
 * @date 2016/11/19
 */
public interface IChangeAddressModel extends ColpencilModel {

    /**
     * 获取小区
     */
    void loadVillage(int privinceId, int cityId);

    void sub(Subscriber<ResultListInfo<CellInfo>> subscriber);

    /**
     * 获取建筑
     */
    void loadBuilding(int id);

    void subBuild(Subscriber<ResultListInfo<Building>> subscriber);

    /**
     * 获取单元
     */
    void loadUnit(int id);

    void subUnit(Subscriber<ResultListInfo<Unit>> subscriber);

    /**
     * 获取房号
     */
    void loadHouse(int id);

    void subHouse(Subscriber<ResultListInfo<RoomInfo>> subscriber);

    /**
     * 获取省份
     */
    void loadProvince();

    void subProvince(Subscriber<ResultListInfo<Province>> subscriber);

    /**
     * 获取城市
     */
    void loadCity(int id);

    void subCity(Subscriber<ResultListInfo<CityInfo>> subscriber);

    /**
     * 获取区
     */
    void loadArea(int id);

    void subArea(Subscriber<ResultListInfo<AreaInfo>> subscriber);
}
