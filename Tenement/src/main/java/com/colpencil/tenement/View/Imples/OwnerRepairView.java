package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.OwnerRepair;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface OwnerRepairView extends ColpencilBaseView {

    /**
     * 刷新
     *
     * @param list
     */
    void refresh(ListCommonResult<OwnerRepair> list);

    /**
     * 加载更多
     *
     * @param list
     */
    void loadMore(ListCommonResult<OwnerRepair> list);

    /**
     * 加载错误
     *
     * @param msg
     */
    void loadError(String msg);
}
