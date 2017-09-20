package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.Feed;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @author 汪 亮
 * @Description: 意见反馈
 * @Email DramaScript@outlook.com
 * @date 2016/9/5
 */
public interface FeedbackView extends ColpencilBaseView {

    void feed(ResultInfo resultInfo);

    void getFeed(ResultListInfo<Feed> resultInfo);
}
