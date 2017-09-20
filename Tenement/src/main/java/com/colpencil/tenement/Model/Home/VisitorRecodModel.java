package com.colpencil.tenement.Model.Home;

import com.colpencil.tenement.Bean.Visitor;
import com.colpencil.tenement.Model.Imples.Home.IVisitorModel;
import com.colpencil.tenement.Model.Imples.Home.IVisitorRecordModel;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description: 访客登记
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/26
 */
public class VisitorRecodModel implements IVisitorRecordModel{


    private Observable<String> stringObservable;

    @Override
    public void getimageUrl() {
        stringObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<String> subscriber) {
        stringObservable.subscribe(subscriber);
    }
}
