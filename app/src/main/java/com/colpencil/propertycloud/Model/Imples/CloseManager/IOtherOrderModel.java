package com.colpencil.propertycloud.Model.Imples.CloseManager;

import com.colpencil.propertycloud.Bean.ListBean;
import com.colpencil.propertycloud.Bean.OtherOrder;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 其他订单
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/8
 */
public interface IOtherOrderModel {

    void getOrder(int pageNo,int pageSize);

    void sub(Subscriber<ListBean<OtherOrder>> subscriber);

}
