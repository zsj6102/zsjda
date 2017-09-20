package com.colpencil.propertycloud.Present.Home;

import com.colpencil.propertycloud.Bean.PaySure;
import com.colpencil.propertycloud.Model.Home.PaySureModel;
import com.colpencil.propertycloud.Model.Imples.Home.IPaySureModel;
import com.colpencil.propertycloud.View.Imples.PaySureView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 确认缴费
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/6
 */
public class PaySurePresent extends ColpencilPresenter<PaySureView> {

    private IPaySureModel model;

    public PaySurePresent(){
        model = new PaySureModel();
    }

    public void getPay(){
        model.getPayInfo();
        Subscriber<List<PaySure>> sureSubscriber = new Subscriber<List<PaySure>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<PaySure> paySureList) {
                mView.paySure(paySureList);
            }
        };
        model.sub(sureSubscriber);
    }

}
