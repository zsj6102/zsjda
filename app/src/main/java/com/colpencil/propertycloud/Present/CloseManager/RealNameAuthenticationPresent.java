package com.colpencil.propertycloud.Present.CloseManager;

import com.colpencil.propertycloud.Model.CloseManager.RealNameAuthenticationModel;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IRealNameAuthenticationModel;
import com.colpencil.propertycloud.View.Imples.RealNameAuthenticationView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * @Description: 实名认证
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
public class RealNameAuthenticationPresent extends ColpencilPresenter<RealNameAuthenticationView> {

    private IRealNameAuthenticationModel model;

    public RealNameAuthenticationPresent(){
       model = new RealNameAuthenticationModel();
    }

    public void getInfo(){
        model.getResult();
        Subscriber<Boolean> subscriber = new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                mView.info(aBoolean);
            }
        };
        model.sub(subscriber);
    }

}
