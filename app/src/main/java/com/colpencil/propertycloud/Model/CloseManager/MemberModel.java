package com.colpencil.propertycloud.Model.CloseManager;

import com.colpencil.propertycloud.Bean.Member;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IMemberModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description: 成员管理
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
public class MemberModel implements IMemberModel {

    private List<Member> list = new ArrayList<>();
    private Observable<List<Member>> listObservable;

    @Override
    public void getMemberList() {
        listObservable = Observable.create(new Observable.OnSubscribe<List<Member>>() {
            @Override
            public void call(Subscriber<? super List<Member>> subscriber) {
                for (int i = 0; i < 10; i++) {
                    Member member = new Member();
                    member.name = "表哥" + i;
                    list.add(member);
                }
                subscriber.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<List<Member>> subscriber) {
        listObservable.subscribe(subscriber);
    }
}
