package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.ListBean;
import com.colpencil.propertycloud.Bean.PayList;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 汪 亮
 * @Description: 缴费
 * @Email DramaScript@outlook.com
 * @date 2016/9/6
 */
public interface PayListView extends ColpencilBaseView {

    void refresh(ListBean<PayList> list);

    void loadMore(ListBean<PayList> list);

    void loadError(String msg);
}
