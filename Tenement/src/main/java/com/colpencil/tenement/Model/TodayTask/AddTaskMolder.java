package com.colpencil.tenement.Model.TodayTask;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.TodayTask.IAddTaskModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 添加任务
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/26
 */
public class AddTaskMolder implements IAddTaskModel {

    private Observable<EntityResult> observa;

    @Override
    public void addTask(String taskDesc, String taskTime, String remindTime, String communityId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("taskDesc", taskDesc);
        params.put("taskTime", taskTime);
        params.put("remindTime", remindTime);
        params.put("communityId", communityId);
        observa = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .addTask(params)
                .map(new Func1<EntityResult, EntityResult>() {
                    @Override
                    public EntityResult call(EntityResult entityResult) {
                        return entityResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<EntityResult> subscriber) {
        observa.subscribe(subscriber);
    }
}
