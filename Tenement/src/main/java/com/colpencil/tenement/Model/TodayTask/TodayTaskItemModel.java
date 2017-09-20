package com.colpencil.tenement.Model.TodayTask;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.SignInfo;
import com.colpencil.tenement.Bean.TodayTask.TodayTaskItem;
import com.colpencil.tenement.Bean.TodayTask.TodayTaskItemResult;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.TodayTask.ITodayTaskItemModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:今日任务子界面
 * @Email DramaScript@outlook.com
 * @date 2016/8/23
 */
public class TodayTaskItemModel implements ITodayTaskItemModel {

    private Observable<TodayTaskItemResult> resultObservable;
    private Observable<EntityResult<SignInfo>> observable;
    private Observable<EntityResult> taskObser;

    /**
     * 获取任务列表
     */
    @Override
    public void loadTaskList(String communityId,int type, int page, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type+"");
        params.put("communityId", communityId);
        params.put("page", page+"");
        params.put("pageSize", pageSize+"");
        resultObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .getTodayTask(params)
                .map(new Func1<TodayTaskItemResult, TodayTaskItemResult>() {
                    @Override
                    public TodayTaskItemResult call(TodayTaskItemResult todayTaskItemResult) {
                        return todayTaskItemResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subTaskList(Observer<TodayTaskItemResult> observer) {
        resultObservable.subscribe(observer);
    }

    @Override
    public void getSignState() {
        observable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .getSignState()
                .map(new Func1<EntityResult<SignInfo>, EntityResult<SignInfo>>() {
                    @Override
                    public EntityResult<SignInfo> call(EntityResult<SignInfo> signInfoEntityResult) {
                        return signInfoEntityResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public void subSign(Subscriber<EntityResult<SignInfo>> subscriber) {
            observable.subscribe(subscriber);
    }

    @Override
    public void taskFinsh(String remindId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("remindId",remindId);
        taskObser = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .taskFinsh(params)
                .map(new Func1<EntityResult, EntityResult>() {
                    @Override
                    public EntityResult call(EntityResult entityResult) {
                        return entityResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public void subTask(Subscriber<EntityResult> subscriber) {
        taskObser.subscribe(subscriber);
    }
}
