package com.colpencil.propertycloud.Model.Home;

import com.colpencil.propertycloud.Bean.PaySure;
import com.colpencil.propertycloud.Model.Imples.Home.IPaySureModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description: 确认缴费
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/6
 */
public class PaySureModel implements IPaySureModel {

    String type[] = {"水费","电费","物业费","垃圾处理费","车位出租费"};
    private List<PaySure> list = new ArrayList<>();
    private Observable<List<PaySure>> listObservable;

    @Override
    public void getPayInfo() {
        listObservable = Observable.create(new Observable.OnSubscribe<List<PaySure>>() {
            @Override
            public void call(Subscriber<? super List<PaySure>> subscriber) {
                for (int i = 0; i < type.length; i++) {
                    PaySure paySure = new PaySure();
                    paySure.type = type[i];
                    list.add(paySure);
                }
                subscriber.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<List<PaySure>> subscriber) {
        listObservable.subscribe(subscriber);
    }
}
