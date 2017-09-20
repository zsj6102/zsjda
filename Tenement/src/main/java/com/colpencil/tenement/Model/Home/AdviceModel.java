package com.colpencil.tenement.Model.Home;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.Advice;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.Home.IAdviceModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 投诉建议
 * @Email DramaScript@outlook.com
 * @date 2016/8/26
 */
public class AdviceModel implements IAdviceModel {

    private Observable<ListCommonResult<Advice>> listObservable;

    @Override
    public void getAdivceList(String communityId,int type,int page,int pageSize,int self) {
        listObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .loadAdvice(communityId,type, page, pageSize, self)
                .map(new Func1<ListCommonResult<Advice>, ListCommonResult<Advice>>() {
                    @Override
                    public ListCommonResult<Advice> call(ListCommonResult<Advice> adviceListCommonResult) {
                        return adviceListCommonResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ListCommonResult<Advice>> subscriber) {
        listObservable.subscribe(subscriber);
    }
}
