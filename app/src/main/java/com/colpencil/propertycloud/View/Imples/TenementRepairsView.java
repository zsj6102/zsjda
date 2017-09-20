package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.Bean.HouseInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @author 汪 亮
 * @Description: 物业报修
 * @Email DramaScript@outlook.com
 * @date 2016/10/11
 */
public interface TenementRepairsView extends ColpencilBaseView {

    void result(ResultInfo resultInfo);

    /**
     * 地址结果
     */
    void estateResult(ResultListInfo<HouseInfo> result);

    /**
     * 小区列表
     */
    void cellResult(ResultListInfo<CellInfo> result);
}
