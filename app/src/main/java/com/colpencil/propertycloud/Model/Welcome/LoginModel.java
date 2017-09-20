package com.colpencil.propertycloud.Model.Welcome;


import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.LoginInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Model.Imples.ILoginModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 登陆
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/7/15
 */
public class LoginModel implements ILoginModel {

    private Observable<Boolean> booleanObservable;
    private Observable<ResultInfo<LoginInfo>> resultInfoObservable;

    @Override
    public void login(final String acount, final String passWord) {
//        booleanObservable = Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(final Subscriber<? super Boolean> subscriber) {
//                CluodApplaction.getInstance().execRunnable(new Runnable() {
//                    @Override
//                    public void run() {
//                        boolean connServer = XmppTools.getInstance().connServer();
//                        if (connServer){//连接上了服务器
//                            boolean login = XmppTools.getInstance().login(acount, passWord);
//                            if (login) {//登陆成功
//                                XmppTools.getInstance().registRecvFileListener();//注册接收器
//                                subscriber.onNext(true);
//                            } else {
//                                subscriber.onNext(false);
//                            }
//                        }else {
//                            subscriber.onNext(false);
//                        }
//
//                    }
//                });
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<Boolean> subscriber) {
        booleanObservable.subscribe(subscriber);
    }

    @Override
    public void loginServer(String mobile, String passWord) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("password", passWord);
        resultInfoObservable = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .login(params)
                .map(new Func1<ResultInfo<LoginInfo>, ResultInfo<LoginInfo>>() {
                    @Override
                    public ResultInfo<LoginInfo> call(ResultInfo<LoginInfo> resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public void sub(Subscriber<ResultInfo<LoginInfo>> subscriber) {
        resultInfoObservable.subscribe(subscriber);
    }
}