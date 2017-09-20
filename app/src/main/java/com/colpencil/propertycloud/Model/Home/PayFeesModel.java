package com.colpencil.propertycloud.Model.Home;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.Ad;
import com.colpencil.propertycloud.Bean.ListBean;
import com.colpencil.propertycloud.Bean.PayFees;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Model.Imples.Home.IPayFeesModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 物业缴费
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/6
 */
public class PayFeesModel implements IPayFeesModel {

    private Observable<ListBean<PayFees>> listBeanObservable;
    private Observable<ListBean<Ad>> adObser;
    private Observable<ResultInfo> adCountObser;

    @Override
    public void getInfo() {
        HashMap<String,String> map = new HashMap<>();
        listBeanObservable = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getPayFeesList(map)
                .map(new Func1<ListBean<PayFees>, ListBean<PayFees>>() {
                    @Override
                    public ListBean<PayFees> call(ListBean<PayFees> payFeesListBean) {
                        return payFeesListBean;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ListBean<PayFees>> subscriber) {
        listBeanObservable.subscribe(subscriber);
    }

    @Override
    public void getAd(String advTp, String advCode) {
        HashMap<String,String> map = new HashMap<>();
        map.put("advTp",advTp);
        map.put("advCode",advCode);
        adObser = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getAd(map)
                .map(new Func1<ListBean<Ad>, ListBean<Ad>>() {
                    @Override
                    public ListBean<Ad> call(ListBean<Ad> listBean) {
                        return listBean;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subAd(Subscriber<ListBean<Ad>> subscriber) {
        adObser.subscribe(subscriber);
    }

    @Override
    public void adCount(int aid) {
        HashMap<String,String> map = new HashMap<>();
        map.put("aid",aid+"");
        adCountObser = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .adCount(map)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subAdCount(Subscriber<ResultInfo> subscriber) {
        adCountObser.subscribe(subscriber);
    }

}
