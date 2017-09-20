package com.colpencil.tenement.Present.TodayTask;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Model.Imples.TodayTask.IAddTaskModel;
import com.colpencil.tenement.Model.TodayTask.AddTaskMolder;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.AddTaskView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Subscriber;

/**
 * @Description: 添加任务
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/26
 */
public class AddTaskPresent extends ColpencilPresenter<AddTaskView> {

    private IAddTaskModel model;

    public AddTaskPresent(){
        model = new AddTaskMolder();
    }

    public void addTask(String taskDesc,String taskTime,String remindTime,String communityId){
        model.addTask(taskDesc,taskTime,remindTime,communityId);
        Subscriber<EntityResult> subscriber = new Subscriber<EntityResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(EntityResult entityResult) {
                mView.result(entityResult);
            }
        };
        model.sub(subscriber);
    }

}
