package com.colpencil.propertycloud.View.Imples;

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
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @author 陈 宝
 * @Description:报修详情
 * @Email 1041121352@qq.com
 * @date 2016/11/19
 */
public interface ChangeAddressView extends ColpencilBaseView {

    /**
     * 小区列表
     */
    void loadVillages(ResultListInfo<CellInfo> resultListInfo);

    /**
     * 楼宇列表
     */
    void loadBuilds(ResultListInfo<Building> resultListInfo);

    /**
     * 楼宇列表
     */
    void loadUnit(ResultListInfo<Unit> resultListInfo);

    /**
     * 楼宇列表
     */
    void loadRooms(ResultListInfo<RoomInfo> resultListInfo);

    /**
     * 省份
     */
    void loadProvince(ResultListInfo<Province> resultListInfo);

    /**
     * 城市
     */
    void loadCitys(ResultListInfo<CityInfo> resultListInfo);

    void loadArea(ResultListInfo<AreaInfo> resultListInfo);
}
