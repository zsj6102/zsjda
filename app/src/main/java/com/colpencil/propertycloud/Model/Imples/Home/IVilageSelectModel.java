package com.colpencil.propertycloud.Model.Imples.Home;

import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.Vilage;
import com.colpencil.propertycloud.Bean.Village;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 小区选择
 * @Email DramaScript@outlook.com
 * @date 2016/9/9
 */
public interface IVilageSelectModel {

    void getList(int provinceId, int cityId, int areaId);

    void sub(Subscriber<ResultListInfo<CellInfo>> subscriber);

    void select(String memberId, String comuId);

    void subSelect(Subscriber<ResultInfo<String>> subscriber);

    void getOrderCell(boolean isProperty);

    void subOrderCells(Subscriber<ResultListInfo<CellInfo>> subscriber);

    void loadCellByName(String cityName);

    void subLoadCellByName(Subscriber<ResultListInfo<CellInfo>> subscriber);

}
