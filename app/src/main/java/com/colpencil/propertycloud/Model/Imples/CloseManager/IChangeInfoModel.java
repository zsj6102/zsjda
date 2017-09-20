package com.colpencil.propertycloud.Model.Imples.CloseManager;

import com.colpencil.propertycloud.Bean.ResultInfo;

import java.util.HashMap;

import rx.Subscriber;

/**
 * @Description: 更改信息
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/11
 */
public interface IChangeInfoModel {

    void changeInfo(int flag,String changeInfo);

    void sub(Subscriber<ResultInfo> subscriber);

}