package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.OrderPayInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @Description: 订单支付
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/6
 */
public interface OrderPayView extends ColpencilBaseView {

    void getOrderInfo(OrderPayInfo orderPayInfo);
}
