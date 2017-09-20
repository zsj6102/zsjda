package com.colpencil.propertycloud.Model.Imples.Home;

import com.colpencil.propertycloud.Bean.Ad;
import com.colpencil.propertycloud.Bean.ListBean;
import com.colpencil.propertycloud.Bean.PayFees;
import com.colpencil.propertycloud.Bean.ResultInfo;

import rx.Subscriber;

/**
 * @Description:  物业缴费
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/6
 */
public interface IPayFeesModel {

    void getInfo();

    void sub(Subscriber<ListBean<PayFees>> subscriber);

    void getAd(String advTp,String advCode);

    void subAd(Subscriber<ListBean<Ad>> subscriber);

    void adCount(int aid);

    void subAdCount(Subscriber<ResultInfo> subscriber);

}
