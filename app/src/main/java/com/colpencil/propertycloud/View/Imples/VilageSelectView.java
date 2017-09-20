package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.Vilage;
import com.colpencil.propertycloud.Bean.Village;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @Description: 小区选择
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/9
 */
public interface VilageSelectView extends ColpencilBaseView {


    /**
     * 刷新
     *
     * @param result
     */
    void refresh(ResultListInfo<CellInfo> result);

    /**
     * 加载错误
     *
     * @param msg
     */
    void loadError(String msg);

    /**
     * 记住默认小区
     * @param resultInfo
     */
    void select(ResultInfo<String> resultInfo);
}
