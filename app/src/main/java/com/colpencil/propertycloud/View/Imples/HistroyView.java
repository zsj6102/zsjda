package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.ComplaintHistroy;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @Description: 投诉历史
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/5
 */
public interface HistroyView extends ColpencilBaseView {

    /**
     * 刷新
     *
     * @param list
     */
    void refresh(ResultListInfo<ComplaintHistroy> list);

    /**
     * 加载更多
     *
     * @param list
     */
    void loadMore(ResultListInfo<ComplaintHistroy> list);

    /**
     * 加载错误
     *
     * @param msg
     */
    void loadError(String msg);
}
