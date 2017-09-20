package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.CleanRecord;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Village;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface CleanRecordView extends ColpencilBaseView {

    /**
     * 刷新
     */
    void refresh(ListCommonResult<CleanRecord> list);

    /**
     * 加载更多
     */
    void loadMore(ListCommonResult<CleanRecord> list);

    /**
     * 加载错误
     */
    void loadError(String msg);

    void loadVillageError(String msg);

    void loadCommunity(ListCommonResult<Village> result);
}
