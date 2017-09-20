package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.Village;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface SelectView extends ColpencilBaseView {

    /**
     * 加载数据
     *
     * @param result
     */
    void loadMore(List<Village> result);

    /**
     * 加载错误
     *
     * @param msg
     */
    void loadError(String msg);

    void loadCode(int code);

    void loadGreet(String greet);
}
