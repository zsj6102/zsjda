package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.ListBean;
import com.colpencil.propertycloud.Bean.OtherOrder;
import com.colpencil.propertycloud.Bean.PayList;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @Description: 其他订单
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/8
 */
public interface OtherOrderView extends ColpencilBaseView{

    void refresh(ListBean<OtherOrder> list);

    void loadMore(ListBean<OtherOrder> list);

    void loadError(String msg);

}
