package com.colpencil.tenement.Model.Home;

import com.colpencil.tenement.Bean.Online;
import com.colpencil.tenement.Model.Imples.Home.IOnlineListModel;
import com.colpencil.tenement.Model.Imples.OnlineTalk.IOnlineTalkListModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.Tools.XmppTools;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description: 客服列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/24
 */
public class OnlineListModel implements IOnlineListModel {

    private Observable<List<Online>> listObservable;


    private List<Online> list = new ArrayList<>();

    @Override
    public void getOnlineList(final Roster roster) {
        listObservable = Observable.create(new Observable.OnSubscribe<List<Online>>() {
            @Override
            public void call(final Subscriber<? super List<Online>> subscriber) {
                /*TenementApplication.getInstance().execRunnable(new Runnable() {
                @Override
                public void run() {
                    List<RosterEntry> allEntrys = XmppTools.getInstance().getAllEntrys(roster);
                    for (int i=0;i<allEntrys.size();i++){
                        Online online = new Online();
                        online.date = "06-28 14:22";
                        online.last = 404+"";
                        online.messgae = "移动主管真帅！哦也！";
                        online.name = allEntrys.get(i).getName();
                        online.userId = allEntrys.get(i).getUser();
                        list.add(online);
                    }
                    subscriber.onNext(list);
                }
            });*/

        }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<List<Online>> onlineSubscriber) {
        listObservable.subscribe(onlineSubscriber);
    }
}
