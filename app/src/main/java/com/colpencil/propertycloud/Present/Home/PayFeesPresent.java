package com.colpencil.propertycloud.Present.Home;

import com.colpencil.propertycloud.Bean.Ad;
import com.colpencil.propertycloud.Bean.ListBean;
import com.colpencil.propertycloud.Bean.PayFees;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Model.Home.PayFeesModel;
import com.colpencil.propertycloud.Model.Imples.Home.IPayFeesModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.View.Imples.PayFeesView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Subscriber;

/**
 * @Description: 缴费
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/6
 */
public class PayFeesPresent extends ColpencilPresenter<PayFeesView> {

    private IPayFeesModel model;

    public PayFeesPresent(){
        model = new PayFeesModel();
    }

    public void getInfo(){
        model.getInfo();
        Subscriber<ListBean<PayFees>> subscriber = new Subscriber<ListBean<PayFees>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误日志："+e.getMessage());
            }

            @Override
            public void onNext(ListBean<PayFees> payFees) {
                mView.getInfo(payFees);
            }
        };
        model.sub(subscriber);
    }

    public void getAd(String advTp, String advCode){
        model.getAd(advTp,advCode);
        Subscriber<ListBean<Ad>> subscriber = new Subscriber<ListBean<Ad>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误日志："+e.getMessage());
            }

            @Override
            public void onNext(ListBean<Ad> listBean) {
                mView.getAd(listBean);
            }
        };
        model.subAd(subscriber);
    }

    public void adCount(int aid ){
        model.adCount(aid);
        Subscriber<ResultInfo> subscriber = new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo resultInfo) {
                mView.addCount(resultInfo);
            }
        };
        model.subAdCount(subscriber);
    }

}
