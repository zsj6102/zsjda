package com.colpencil.propertycloud.Model.Imples.Home;

import com.colpencil.propertycloud.Bean.PaySure;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 确认缴费
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/6
 */
public interface IPaySureModel  {

    void getPayInfo();

    void sub(Subscriber<List<PaySure>> subscriber);

}
