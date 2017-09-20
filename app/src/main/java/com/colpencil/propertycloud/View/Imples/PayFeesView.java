package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.Ad;
import com.colpencil.propertycloud.Bean.ListBean;
import com.colpencil.propertycloud.Bean.PayFees;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @Description: 物业缴费
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/6
 */
public interface PayFeesView extends ColpencilBaseView {

    void getInfo(ListBean<PayFees> list);

    void getAd(ListBean<Ad> list);

    void addCount(ResultInfo resultInfo);
}
