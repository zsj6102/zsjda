package com.colpencil.tenement.Model.Imples.Home;

import rx.Subscriber;

/**
 * @Description: 访客登记
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/26
 */
public interface IVisitorRecordModel {

    void getimageUrl();

    void sub(Subscriber<String> subscriber);

}
