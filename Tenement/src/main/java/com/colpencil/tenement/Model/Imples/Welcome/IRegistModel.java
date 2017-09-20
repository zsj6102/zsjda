package com.colpencil.tenement.Model.Imples.Welcome;

import rx.Observer;

/**
 * @Description: 注册
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/7/17
 */
public interface IRegistModel {

    //注册操作
    void regist(String acount, String passWord);

    //注册观察者
    void sub(Observer<Integer> subscriber);
}
