package com.colpencil.tenement.Model.Home;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Feedback;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.Welcome.IFeedbackModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import retrofit2.http.Url;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class FeedbackModel implements IFeedbackModel {

    private Observable<EntityResult<Feedback>> observable;

    @Override
    public void loadFeedback(int orderId, int type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("orderId", orderId + "");
        params.put("type", type + "");
        observable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .seeFeedback(params)
                .subscribeOn(Schedulers.io())
                .map(new Func1<EntityResult<Feedback>, EntityResult<Feedback>>() {
                    @Override
                    public EntityResult<Feedback> call(EntityResult<Feedback> result) {
                        return result;
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<EntityResult<Feedback>> observer) {
        observable.subscribe(observer);
    }
}
