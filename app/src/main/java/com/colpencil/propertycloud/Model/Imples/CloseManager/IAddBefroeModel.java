package com.colpencil.propertycloud.Model.Imples.CloseManager;

import com.colpencil.propertycloud.Bean.ResultInfo;

import rx.Subscriber;

/**
 * @Description: 添加成员之前
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/13
 */
public interface IAddBefroeModel {

    void getCode(String mobile,int flag);

    void sub(Subscriber<ResultInfo> subscriber);

}
