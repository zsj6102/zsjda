package com.colpencil.propertycloud.Present;

import com.colpencil.propertycloud.Bean.LoginInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Model.Imples.ILoginModel;
import com.colpencil.propertycloud.Model.Welcome.LoginModel;
import com.colpencil.propertycloud.View.Imples.LoginView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;

import rx.Subscriber;

/**
 * @Description: 登陆
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/7/15
 */
public class LoginPresent extends ColpencilPresenter<LoginView> {

    private ILoginModel loginModel;

    public LoginPresent(){
        loginModel = new LoginModel();
    }

    public void login(String acount,String passWord){
        loginModel.login(acount,passWord);
        Subscriber<Boolean> subscriber = new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(Boolean aBoolean) {
                mView.login(aBoolean);
                ColpencilLogger.e("----------------aBoolean="+aBoolean);
            }
        };
        loginModel.sub(subscriber);
    }

    public void loginToServer(String mobile, String passWord){
        loginModel.loginServer(mobile,passWord);
        Subscriber<ResultInfo<LoginInfo>> login = new Subscriber<ResultInfo<LoginInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(ResultInfo<LoginInfo> resultInfo) {
                mView.loginForServer(resultInfo);
            }
        };
        loginModel.sub(login);
    }

}
