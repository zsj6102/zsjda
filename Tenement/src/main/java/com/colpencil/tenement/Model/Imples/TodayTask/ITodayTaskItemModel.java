package com.colpencil.tenement.Model.Imples.TodayTask;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.SignInfo;
import com.colpencil.tenement.Bean.TodayTask.TodayTaskItemResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;
import rx.Subscriber;

public interface ITodayTaskItemModel extends ColpencilModel {

    /**
     * 获取今日任务列表
     */
    void loadTaskList(String communityId,int type, int page, int pageSize);

    void subTaskList(Observer<TodayTaskItemResult> observer);

    void getSignState();

    void subSign(Subscriber<EntityResult<SignInfo>> subscriber);

    void taskFinsh(String remindId);

    void subTask(Subscriber<EntityResult> subscriber);
}
