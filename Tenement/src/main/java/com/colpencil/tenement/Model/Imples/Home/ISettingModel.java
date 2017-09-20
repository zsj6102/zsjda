package com.colpencil.tenement.Model.Imples.Home;

import com.colpencil.tenement.Bean.EntityResult;

import rx.Subscriber;

/**
 * @Description: 设置
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/29
 */
public interface ISettingModel {

    void loginOut(String communityId);

    void sub(Subscriber<EntityResult<String>> subscriber);

}
