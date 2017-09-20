package com.colpencil.propertycloud.Model.Imples.Home;

import com.colpencil.propertycloud.Bean.ReadInfo;

import rx.Subscriber;

/**
 * @Description: 阅读类的H5
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/8
 */
public interface IReadInfoModel {

    void getInfo();

    void sub(Subscriber<ReadInfo> subscriber);

}
