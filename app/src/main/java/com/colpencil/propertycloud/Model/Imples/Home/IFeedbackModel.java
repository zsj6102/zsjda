package com.colpencil.propertycloud.Model.Imples.Home;

import com.colpencil.propertycloud.Bean.Feed;
import com.colpencil.propertycloud.Bean.Feedback;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 意见反馈
 * @Email DramaScript@outlook.com
 * @date 2016/9/5
 */
public interface IFeedbackModel {

    void getFeedback(String orderId);

    void sub(Subscriber<ResultListInfo<Feed>> subscriber);

    void subimtFeed(String orderId, String feedbckTp, String feedScore, String detailDesc);

    void subFeed(Subscriber<ResultInfo> subscriber);

}
