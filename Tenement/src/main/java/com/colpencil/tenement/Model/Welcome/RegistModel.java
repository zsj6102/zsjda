package com.colpencil.tenement.Model.Welcome;

import com.colpencil.tenement.Model.Imples.Welcome.IRegistModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.Tools.XmppTools;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 注册业务逻辑
 * @Email DramaScript@outlook.com
 * @date 2016/7/17
 */
public class RegistModel implements IRegistModel {

    private Observable<Integer> regist;

    @Override
    public void regist(final String acount, final String passWord) {
        ColpencilLogger.e("------------------------4");
        regist = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
              /*  TenementApplication.getInstance().execRunnable(new Runnable() {
                    @Override
                    public void run() {
                        int regist = XmppTools.getInstance().regist(acount, passWord);
                        subscriber.onNext(regist);
                    }
                });*/

            }
        }).observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        ColpencilLogger.e("------------------------5");
    }

    @Override
    public void sub(Observer<Integer> subscriber) {
        regist.subscribe(subscriber);
    }
}