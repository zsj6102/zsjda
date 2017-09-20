package com.colpencil.tenement.Present.Welcome;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.TenementComp;
import com.colpencil.tenement.Bean.UserInfo;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Model.Imples.Welcome.ILoginModel;
import com.colpencil.tenement.Model.Welcome.LoginModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.LoginView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Observer;
import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 登陆
 * @Email DramaScript@outlook.com
 * @date 2016/7/15
 */
public class LoginPresent extends ColpencilPresenter<LoginView> {

    private ILoginModel loginModel;

    public LoginPresent() {
        loginModel = new LoginModel();
    }

    public void login(String acount, String passWord) {
        loginModel.login(acount, passWord);
        Subscriber<Boolean> subscriber = new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(Boolean aBoolean) {
                mView.login(aBoolean);
            }
        };
        loginModel.sub(subscriber);
    }

    public void loginToServer(String acount, String passWord,String propertyId,String deviceId) {
        loginModel.loginToServer(acount, passWord,propertyId,deviceId);
        Observer<EntityResult<UserInfo>> observer = new Observer<EntityResult<UserInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(EntityResult<UserInfo> entityResult) {
                mView.loginResult(entityResult);
            }
        };
        loginModel.sublogin(observer);
    }

    public void getCompList(){
        loginModel.getCompList();
        Subscriber<ListCommonResult<TenementComp>> subscriber = new Subscriber<ListCommonResult<TenementComp>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<TenementComp> tenementCompListCommonResult) {
                mView.compList(tenementCompListCommonResult);
            }
        };
        loginModel.sub(subscriber);
    }

    public void loadVillage(){
        loginModel.loadVillage();
        Subscriber<ListCommonResult<Village>> subscriber = new Subscriber<ListCommonResult<Village>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<Village> result) {
                mView.loadCommunity(result);
            }
        };
        loginModel.subVillage(subscriber);
    }
}
