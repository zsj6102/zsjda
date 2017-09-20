package com.colpencil.propertycloud.Present.Home;

import com.colpencil.propertycloud.Bean.OrderPayInfo;
import com.colpencil.propertycloud.Model.Home.OrderPayModel;
import com.colpencil.propertycloud.Model.Imples.Home.IOrderPayModer;
import com.colpencil.propertycloud.View.Imples.OrderPayView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * @Description: 订单支付
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/6
 */
public class OrderPayPresent extends ColpencilPresenter<OrderPayView> {

    private IOrderPayModer moder;

    public OrderPayPresent(){
        moder = new OrderPayModel();
    }

    public void getOrderInfo(){
        moder.getInfo();
        Subscriber<OrderPayInfo> subscriber = new Subscriber<OrderPayInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(OrderPayInfo orderPayInfo) {
                mView.getOrderInfo(orderPayInfo);
            }
        };
        moder.sub(subscriber);
    }

}
