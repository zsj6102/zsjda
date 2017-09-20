package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Feedback;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @author 陈 宝
 * @Description:查看反馈
 * @Email 1041121352@qq.com
 * @date 2016/9/30
 */
public interface FeedbackView extends ColpencilBaseView {

    /**
     * 查看反馈结果
     *
     * @param result
     */
    void feekBackResult(EntityResult<Feedback> result);

    void loadError(String msg);

}
