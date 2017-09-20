package com.colpencil.propertycloud.Model.Home;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.Feed;
import com.colpencil.propertycloud.Bean.Feedback;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.Imples.Home.IFeedbackModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 意见反馈
 * @Email DramaScript@outlook.com
 * @date 2016/9/5
 */
public class FeedbackModel implements IFeedbackModel {

    private Observable<ResultListInfo<Feed>> getObser;
    private Observable<ResultInfo> submitObser;

    @Override
    public void getFeedback(String orderId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        getObser = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getFeed(map)
                .map(new Func1<ResultListInfo<Feed>, ResultListInfo<Feed>>() {
                    @Override
                    public ResultListInfo<Feed> call(ResultListInfo<Feed> feedResultListInfo) {
                        return feedResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultListInfo<Feed>> subscriber) {
        getObser.subscribe(subscriber);
    }

    @Override
    public void subimtFeed(String orderId, String feedbckTp, String feedScore, String detailDesc) {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("feedbckTp", feedbckTp);
        map.put("feedScore", feedScore);
        map.put("detailDesc", detailDesc);
        submitObser = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .submitFeed(map)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subFeed(Subscriber<ResultInfo> subscriber) {
        submitObser.subscribe(subscriber);
    }
}
