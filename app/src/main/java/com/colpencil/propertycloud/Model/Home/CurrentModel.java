package com.colpencil.propertycloud.Model.Home;

import com.colpencil.propertycloud.Bean.Current;
import com.colpencil.propertycloud.Model.Imples.Home.ICurrentModel;
import com.colpencil.propertycloud.R;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description: 装修当前进度
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
public class CurrentModel implements ICurrentModel {

    private int[] state = {0,1,2,3,4,5};
    private List<Current> list = new ArrayList<>();
    private Observable<List<Current>> listObservable;
    private int[] pic = {R.mipmap.colpencil,R.mipmap.ic_launcher};

    @Override
    public void getList() {
//        listObservable = Observable.create(new Observable.OnSubscribe<List<Current>>() {
//            @Override
//            public void call(Subscriber<? super List<Current>> subscriber) {
//                for (int i = 0; i < state.length; i++) {
//                    Current current = new Current();
//                    current.state = state[i];
//                    if (i==4){
//                        for (int j=0;j<pic.length;j++){
//                            current.picList.add(pic[j]);
//                        }
//                    }
//                    list.add(current);
//                }
//                subscriber.onNext(list);
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<List<Current>> subscriber) {
        listObservable.subscribe(subscriber);
    }
}
