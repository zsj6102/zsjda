package com.colpencil.propertycloud.Present.Home;

import android.util.Log;

import com.colpencil.propertycloud.Bean.Feed;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.Home.FeedbackModel;
import com.colpencil.propertycloud.Model.Imples.Home.IFeedbackModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.View.Imples.FeedbackView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 意见反馈
 * @Email DramaScript@outlook.com
 * @date 2016/9/5
 */
public class FeedBackPresent extends ColpencilPresenter<FeedbackView> {

    private IFeedbackModel model;

    public FeedBackPresent() {
        model = new FeedbackModel();
    }

    public void getFeed(String orderId) {
        model.getFeedback(orderId);
        Subscriber<ResultListInfo<Feed>> subscriber = new Subscriber<ResultListInfo<Feed>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.getMessage());
            }

            @Override
            public void onNext(ResultListInfo<Feed> resultInfo) {
                mView.getFeed(resultInfo);
            }
        };
        model.sub(subscriber);
    }

    public void submitFeed(String orderId, String feedbckTp, String feedScore, String detailDesc) {
        model.subimtFeed(orderId, feedbckTp, feedScore, detailDesc);
        Subscriber<ResultInfo> subscriber = new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastTools.showShort(CluodApplaction.getInstance(), "服务器错误信息" + e.getMessage());
            }

            @Override
            public void onNext(ResultInfo resultInfo) {
                mView.feed(resultInfo);
            }
        };
        model.subFeed(subscriber);
    }
}