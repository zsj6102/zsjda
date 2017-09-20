package com.colpencil.tenement.Present.TodayTask;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Sign;
import com.colpencil.tenement.Model.Imples.TodayTask.ISignModel;
import com.colpencil.tenement.Model.TodayTask.SignModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.SignView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;

import rx.Subscriber;

/**
 * @Description: 签到/签退
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/23
 */
public class SignPresent extends ColpencilPresenter<SignView> {

    private ISignModel model;

    public SignPresent(){
        model = new SignModel();
    }

    public void siginIn(String location, File signImage){
        model.signIn(location,signImage);
        Subscriber<EntityResult<Sign>> subscriber = new Subscriber<EntityResult<Sign>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(EntityResult<Sign> result) {
                mView.signIn(result);
            }
        };
        model.subIn(subscriber);
    }

    public void siginOut(String location, File signImage){
        model.signOut(location,signImage);
        Subscriber<EntityResult<Sign>> subscriber = new Subscriber<EntityResult<Sign>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(EntityResult<Sign> result) {
                mView.signOut(result);
            }
        };
        model.subOut(subscriber);
    }
}
