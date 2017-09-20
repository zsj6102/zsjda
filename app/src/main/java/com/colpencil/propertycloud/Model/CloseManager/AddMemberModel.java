package com.colpencil.propertycloud.Model.CloseManager;

import com.colpencil.propertycloud.Bean.Room;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IAddMemberModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description: 添加成员
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
public class AddMemberModel implements IAddMemberModel {

    private Observable<List<Room>> listObservable;
    private List<Room> list = new ArrayList<>();

    @Override
    public void add() {

    }

    @Override
    public void sub(Subscriber<Boolean> subscriber) {

    }

    @Override
    public void getRoomList() {
        listObservable = Observable.create(new Observable.OnSubscribe<List<Room>>() {
            @Override
            public void call(Subscriber<? super List<Room>> subscriber) {
                for (int i=0;i<6;i++){
                    Room room = new Room();
                    room.name = "天河第"+i+"号";
                    list.add(room);
                }
                subscriber.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subRoom(Subscriber<List<Room>> subscriber) {
        listObservable.subscribe(subscriber);
    }
}
