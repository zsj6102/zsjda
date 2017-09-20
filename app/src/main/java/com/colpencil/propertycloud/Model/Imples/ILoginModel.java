package com.colpencil.propertycloud.Model.Imples;

import com.colpencil.propertycloud.Bean.LoginInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;

import rx.Observer;
import rx.Subscriber;

/**
 * @Description: 登陆
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/7/15
 */
public interface ILoginModel {

    //登陆操作
    void login(String acount, String passWord);

    //注册观察者
    void sub(Observer<Boolean> subscriber);

    void loginServer(String mobile, String passWord);

    void sub(Subscriber<ResultInfo<LoginInfo>> subscriber);

}
