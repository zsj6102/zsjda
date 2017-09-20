package com.colpencil.propertycloud.Model.Imples.Home;

import com.colpencil.propertycloud.Bean.OrderPayInfo;

import rx.Subscriber;

/**
 * @Description: 订单支付
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/6
 */
public interface IOrderPayModer {

    void getInfo();

    void sub(Subscriber<OrderPayInfo> subscriber);

}
