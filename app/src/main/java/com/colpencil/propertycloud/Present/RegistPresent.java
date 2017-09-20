package com.colpencil.propertycloud.Present;

import com.colpencil.propertycloud.Bean.LoginInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Model.Imples.IRegistModel;
import com.colpencil.propertycloud.Model.Welcome.RegistModel;
import com.colpencil.propertycloud.View.Imples.RegistView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 注册
 * @Email DramaScript@outlook.com
 * @date 2016/7/17
 */
public class RegistPresent extends ColpencilPresenter<RegistView> {

    private IRegistModel iRegistModel;

    public RegistPresent() {
        iRegistModel = new RegistModel();
    }

    public void regist(String mobile, String passWord, String validcode, String conpassword) {
        iRegistModel.regist(mobile, passWord, validcode, conpassword);
        Subscriber<ResultInfo> subscriber = new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误：" + e.getMessage());
            }

            @Override
            public void onNext(ResultInfo resultInfo) {
                mView.regist(resultInfo);
            }
        };
        iRegistModel.sub(subscriber);
    }

    public void login(String mobile, String pwd) {
        iRegistModel.loginServer(mobile, pwd);
        Subscriber<ResultInfo<LoginInfo>> subscriber = new Subscriber<ResultInfo<LoginInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误：" + e.getMessage());
            }

            @Override
            public void onNext(ResultInfo<LoginInfo> resultInfo) {
                mView.loginForServer(resultInfo);
            }
        };
        iRegistModel.sub(subscriber);
    }

    public void getCode(int flag, String phone) {
        iRegistModel.getCode(flag, phone);
        Subscriber<ResultInfo> subscriber = new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误：" + e.getMessage());
            }

            @Override
            public void onNext(ResultInfo resultInfo) {
                mView.getCode(resultInfo);
            }
        };
        iRegistModel.subCode(subscriber);
    }
}
