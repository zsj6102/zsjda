package com.colpencil.tenement.Present.Home;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Feedback;
import com.colpencil.tenement.Model.Imples.Welcome.IFeedbackModel;
import com.colpencil.tenement.Model.Home.FeedbackModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.FeedbackView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Observer;

/**
 * @author 陈 宝
 * @Description:查看反馈
 * @Email 1041121352@qq.com
 * @date 2016/9/30
 */
public class FeedbackPresent extends ColpencilPresenter<FeedbackView> {

    private IFeedbackModel model;

    public FeedbackPresent() {
        model = new FeedbackModel();
    }

    public void seeFeedback(int orderId, int type) {
        model.loadFeedback(orderId, type);
        Observer<EntityResult<Feedback>> observer = new Observer<EntityResult<Feedback>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(EntityResult<Feedback> result) {
                mView.feekBackResult(result);
            }
        };
        model.sub(observer);
    }
}
