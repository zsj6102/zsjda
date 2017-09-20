package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.AdviceList;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.Vilage;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @Description: 物业建议列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
public interface AdviceListView extends ColpencilBaseView {

    /**
     * 刷新
     *
     * @param result
     */
    void refresh(ResultListInfo<AdviceList> result);

    /**
     * 加载更多
     *
     * @param result
     */
    void loadMore(ResultListInfo<AdviceList> result);

    /**
     * 加载错误
     *
     * @param msg
     */
    void loadError(String msg);

}
