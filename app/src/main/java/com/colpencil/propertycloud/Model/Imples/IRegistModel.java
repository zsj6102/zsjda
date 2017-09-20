package com.colpencil.propertycloud.Model.Imples;

import com.colpencil.propertycloud.Bean.LoginInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;

import rx.Observer;
import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 注册
 * @Email DramaScript@outlook.com
 * @date 2016/7/17
 */
public interface IRegistModel {

    //注册操作
    void regist(String mobile, String passWord, String validcode, String conpassword);

    //注册观察者
    void sub(Observer<ResultInfo> subscriber);

    //登陆
    void loginServer(String mobile, String passWord);

    void sub(Subscriber<ResultInfo<LoginInfo>> subscriber);

    //获取验证码
    void getCode(int flag, String mobile);

    void subCode(Subscriber<ResultInfo> subscriber);

}
