package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.SignInfo;
import com.colpencil.tenement.Bean.TodayTask.TodayTaskItem;
import com.colpencil.tenement.Bean.TodayTask.TodayTaskItemResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface TodayTaskItemView extends ColpencilBaseView {

    /**
     * 首页加载
     */
    void refresh(TodayTaskItemResult list);

    /**
     * 加载更多
     */
    void loadMore(TodayTaskItemResult list);

    /**
     * 请求错误
     */
    void loadError(String msg);

    void getSignState(EntityResult<SignInfo> result);

    void taskFinsh(EntityResult entityResult);
}
