package com.colpencil.tenement.Model.Imples.Welcome;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Feedback;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @author 陈 宝
 * @Description:查看反馈
 * @Email 1041121352@qq.com
 * @date 2016/9/30
 */
public interface IFeedbackModel extends ColpencilModel {

    /**
     * 查看反馈
     *
     * @param orderId
     * @param type
     */
    void loadFeedback(int orderId, int type);

    void sub(Observer<EntityResult<Feedback>> observer);
}
