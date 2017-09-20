package com.colpencil.propertycloud.Model.CloseManager;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ListBean;
import com.colpencil.propertycloud.Bean.OtherOrder;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IOtherOrderModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 其他订单
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/8
 */
public class OtherOrderModel implements IOtherOrderModel {


    private Observable<ListBean<OtherOrder>> listBeanObservable;

    @Override
    public void getOrder(int pageNo,int pageSize) {
        HashMap<String,String> map = new HashMap<>();
        map.put("pageNo",pageNo+"");
        map.put("pageSize",pageSize+"");
        listBeanObservable = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getOtherOrder(map)
                .map(new Func1<ListBean<OtherOrder>, ListBean<OtherOrder>>() {
                    @Override
                    public ListBean<OtherOrder> call(ListBean<OtherOrder> otherOrderListBean) {
                        return otherOrderListBean;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ListBean<OtherOrder>> subscriber) {
        listBeanObservable.subscribe(subscriber);
    }
}
