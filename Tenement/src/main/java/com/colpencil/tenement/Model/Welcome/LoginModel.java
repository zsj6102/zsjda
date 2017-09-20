package com.colpencil.tenement.Model.Welcome;


import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.TenementComp;
import com.colpencil.tenement.Bean.UserInfo;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.Welcome.ILoginModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 登陆
 * @Email DramaScript@outlook.com
 * @date 2016/7/15
 */
public class LoginModel implements ILoginModel {

    private Observable<Boolean> booleanObservable;
    private Observable<EntityResult<UserInfo>> loginObservable;
    private Observable<ListCommonResult<TenementComp>> compOber;

    @Override
    public void login(final String acount, final String passWord) {
        booleanObservable = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
               /* TenementApplication.getInstance().execRunnable(new Runnable() {
                    @Override
                    public void run() {
                        boolean connServer = XmppTools.getInstance().connServer();
                        if (connServer) {//连接上了服务器
                            boolean login = XmppTools.getInstance().login(acount, passWord);
                            if (login) {//登陆成功
                                XmppTools.getInstance().registRecvFileListener();//注册接收器
                                subscriber.onNext(true);
                            } else {
                                subscriber.onNext(false);
                            }
                        } else {
                            subscriber.onNext(false);
                        }

                    }
                });*/
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<Boolean> subscriber) {
        booleanObservable.subscribe(subscriber);
    }

    @Override
    public void loginToServer(String acount, String passWord,String propertyId,String deviceId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("account",  acount);
        params.put("pwd", passWord);
        params.put("propertyId", propertyId);
        params.put("deviceId", deviceId);
        loginObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .loginToServer(params)
                .map(new Func1<EntityResult<UserInfo>, EntityResult<UserInfo>>() {
                    @Override
                    public EntityResult<UserInfo> call(EntityResult<UserInfo> userInfoEntityResult) {
                        return userInfoEntityResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public void sublogin(Observer<EntityResult<UserInfo>> observer) {
        loginObservable.subscribe(observer);
    }

    @Override
    public void getCompList() {
        HashMap<String, String> params = new HashMap<>();
        compOber = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .getTenementCompList(params)
                .map(new Func1<ListCommonResult<TenementComp>, ListCommonResult<TenementComp>>() {
                    @Override
                    public ListCommonResult<TenementComp> call(ListCommonResult<TenementComp> tenementCompListCommonResult) {
                        return tenementCompListCommonResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ListCommonResult<TenementComp>> subscriber) {
        compOber.subscribe(subscriber);
    }

    private Observable<ListCommonResult<Village>> observable;

    @Override
    public void loadVillage() {
        HashMap<String,String> map = new HashMap<>();
        observable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .getVillage(map)
                .map(new Func1<ListCommonResult<Village>, ListCommonResult<Village>>() {
                    @Override
                    public ListCommonResult<Village> call(ListCommonResult<Village> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subVillage(Subscriber<ListCommonResult<Village>> subscriber) {
        observable.subscribe(subscriber);
    }
}