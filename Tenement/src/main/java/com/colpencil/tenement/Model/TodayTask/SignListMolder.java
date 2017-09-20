package com.colpencil.tenement.Model.TodayTask;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.SignInfo;
import com.colpencil.tenement.Bean.SignList;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.TodayTask.ISignListModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 签到/签退记录
 * @Email DramaScript@outlook.com
 * @date 2016/9/26
 */
public class SignListMolder implements ISignListModel {

    private Observable<ListCommonResult<SignList>> observable;
    private Observable<EntityResult<SignInfo>> observableSign;

    @Override
    public void getSignList(int currentPage, int pageSize, int type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("currentPage", currentPage + "");
        params.put("pageSize", pageSize + "");
        observable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .getSignList(params)
                .map(new Func1<ListCommonResult<SignList>, ListCommonResult<SignList>>() {
                    @Override
                    public ListCommonResult<SignList> call(ListCommonResult<SignList> signListEntityResult) {
                        return signListEntityResult;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ListCommonResult<SignList>> subscriber) {
        observable.subscribe(subscriber);
    }

    @Override
    public void getSignState() {
        observableSign = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
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
        observableSign.subscribe(subscriber);
    }
}
