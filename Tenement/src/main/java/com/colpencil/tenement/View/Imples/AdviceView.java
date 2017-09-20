package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.Advice;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 汪 亮
 * @Description: 投诉建议
 * @Email DramaScript@outlook.com
 * @date 2016/8/26
 */
public interface AdviceView extends ColpencilBaseView {

    /**
     * 刷新
     *
     * @param list
     */
    void refresh(ListCommonResult<Advice> list);

    /**
     * 加载更多
     *
     * @param list
     */
    void loadMore(ListCommonResult<Advice> list);

    /**
     * 加载错误
     *
     * @param msg
     */
    void loadError(String msg);

}
