package com.colpencil.propertycloud.Model.Imples.Home;

import com.colpencil.propertycloud.Bean.ListBean;
import com.colpencil.propertycloud.Bean.PayList;

import java.util.List;

import rx.Subscriber;

/**
 * @Description:
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/6
 */
public interface IPayListModel {

    void getList(int page,int pageSize,String payItemsId,String billIds);

    void sub(Subscriber<ListBean<PayList>> subscriber);

}
