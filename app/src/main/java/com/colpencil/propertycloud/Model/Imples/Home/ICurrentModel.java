package com.colpencil.propertycloud.Model.Imples.Home;

import com.colpencil.propertycloud.Bean.Current;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 装修当前进度
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
public interface ICurrentModel {

    void getList();

    void sub(Subscriber<List<Current>> subscriber);

}
