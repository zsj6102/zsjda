package com.colpencil.propertycloud.Model.Imples.CloseManager;

import rx.Subscriber;

/**
 * @Description: 实名认证
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
public interface IRealNameAuthenticationModel {

    void getResult();

    void sub(Subscriber<Boolean> subscriber);

}
