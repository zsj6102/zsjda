package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Visitor;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 汪 亮
 * @Description: 访客管理
 * @Email DramaScript@outlook.com
 * @date 2016/8/25
 */
public interface VisitorView extends ColpencilBaseView {

    /**
     * 刷新
     *
     * @param result
     */
    void refresh(ListCommonResult<Visitor> result);

    /**
     * 加载更多
     *
     * @param result
     */
    void loadMore(ListCommonResult<Visitor> result);

    /**
     * 加载错误
     *
     * @param msg
     */
    void loadError(String msg);
}
