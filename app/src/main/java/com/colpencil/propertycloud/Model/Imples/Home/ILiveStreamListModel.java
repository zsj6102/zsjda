package com.colpencil.propertycloud.Model.Imples.Home;

import com.colpencil.propertycloud.Bean.LiveStreaming;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 摄像头列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
public interface ILiveStreamListModel {

    void getList();

    void sub(Subscriber<List<LiveStreaming>> subscriber);

}
