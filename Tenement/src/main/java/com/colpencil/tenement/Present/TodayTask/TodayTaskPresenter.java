package com.colpencil.tenement.Present.TodayTask;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.SignInfo;
import com.colpencil.tenement.Bean.TodayTask.TodayTaskItemResult;
import com.colpencil.tenement.Model.Imples.TodayTask.ITodayTaskItemModel;
import com.colpencil.tenement.Model.TodayTask.TodayTaskItemModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.TodayTaskItemView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Observer;
import rx.Subscriber;

public class TodayTaskPresenter extends ColpencilPresenter<TodayTaskItemView> {

    private ITodayTaskItemModel model;

    public TodayTaskPresenter() {
        model = new TodayTaskItemModel();
    }

    /**
     * 获取任务列表
     */
    public void loadTaskList(String communityId,int type, final int page, int pageSize) {
        model.loadTaskList(communityId,type, page, pageSize);
        Observer<TodayTaskItemResult> observer = new Observer<TodayTaskItemResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(TodayTaskItemResult result) {
                int code = result.getCode();
                String message = result.getMessage();
                if (code == 0||code==2||code==3) {
                    if (page == 1) {
                        mView.refresh(result);
                    } else {
                        mView.loadMore(result);
                    }
                } else {
                    mView.loadError(message);
                }
            }
        };
        model.subTaskList(observer);
    }

    public void getSignState(){
        model.getSignState();
        Subscriber<EntityResult<SignInfo>> subscriber = new Subscriber<EntityResult<SignInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(EntityResult<SignInfo> signInfoEntityResult) {
                mView.getSignState(signInfoEntityResult);
            }
        };
        model.subSign(subscriber);
    }

    public void taskFish(String remindId){
        model.taskFinsh(remindId);
        Subscriber<EntityResult> subscriber = new Subscriber<EntityResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(EntityResult entityResult) {
                mView.taskFinsh(entityResult);
            }
        };
        model.subTask(subscriber);
    }
}
