package com.colpencil.propertycloud.Model.Imples.CloseManager;

import com.colpencil.propertycloud.Bean.AuthCode;

import rx.Subscriber;

/**
 * @Description: 获取验证码
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/9
 */
public interface ICodeModel {

    void getCode(String phone);

    void sub(Subscriber<AuthCode> subscriber);

}
