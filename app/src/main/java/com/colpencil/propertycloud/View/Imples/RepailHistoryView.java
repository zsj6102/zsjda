package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.ComplaintHistroy;
import com.colpencil.propertycloud.Bean.RepairHistory;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @Description: 报修历史
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/11
 */
public interface RepailHistoryView extends ColpencilBaseView{

    /**
     * 刷新
     *
     * @param list
     */
    void refresh(ResultListInfo<RepairHistory> list);

    /**
     * 加载更多
     *
     * @param list
     */
    void loadMore(ResultListInfo<RepairHistory> list);

    /**
     * 加载错误
     *
     * @param msg
     */
    void loadError(String msg);
}
