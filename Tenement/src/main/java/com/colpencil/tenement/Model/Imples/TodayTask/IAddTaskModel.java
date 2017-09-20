package com.colpencil.tenement.Model.Imples.TodayTask;

import com.colpencil.tenement.Bean.EntityResult;

import rx.Subscriber;

/**
 * @Description: 添加任务
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/26
 */
public interface IAddTaskModel {

    void addTask(String taskDesc,String taskTime,String remindTime,String communityId);

    void sub(Subscriber<EntityResult> subscriber);
}
