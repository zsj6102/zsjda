package com.colpencil.propertycloud.Model.Home;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ListBean;
import com.colpencil.propertycloud.Bean.PayList;
import com.colpencil.propertycloud.Model.Imples.Home.IPayListModel;
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
 * @Description: 缴费列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/6
 */
public class PayListModel implements IPayListModel {


    private Observable<ListBean<PayList>> listBeanObservable;

    @Override
    public void getList(int page, int pageSize, String payItemsId,String billIds) {
        HashMap<String,String> map = new HashMap<>();
        map.put("pageNo",page+"");
        map.put("pageSize",pageSize+"");
        map.put("payItemsId",payItemsId+"");
        map.put("billIds",billIds+"");
        listBeanObservable = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getPayFeesListDetail(map)
                .map(new Func1<ListBean<PayList>, ListBean<PayList>>() {
                    @Override
                    public ListBean<PayList> call(ListBean<PayList> payListListBean) {
                        return payListListBean;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ListBean<PayList>> subscriber) {
        listBeanObservable.subscribe(subscriber);
    }
}
