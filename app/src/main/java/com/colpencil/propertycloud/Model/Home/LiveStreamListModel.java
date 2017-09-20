package com.colpencil.propertycloud.Model.Home;

import com.colpencil.propertycloud.Bean.LiveStreaming;
import com.colpencil.propertycloud.Model.Imples.Home.ILiveStreamListModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description: 摄像头列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
public class LiveStreamListModel implements ILiveStreamListModel {

    private List<LiveStreaming> list = new ArrayList<>();
    private Observable<List<LiveStreaming>> listObservable;

    @Override
    public void getList() {
        listObservable = Observable.create(new Observable.OnSubscribe<List<LiveStreaming>>() {
            @Override
            public void call(Subscriber<? super List<LiveStreaming>> subscriber) {
                for (int i = 0; i < 8; i++) {
                    LiveStreaming liveStreaming = new LiveStreaming();
                    liveStreaming.name = "摄像头" + i;
                    list.add(liveStreaming);
                }
                subscriber.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<List<LiveStreaming>> subscriber) {
        listObservable.subscribe(subscriber);
    }
}
