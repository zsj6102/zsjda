package com.colpencil.tenement.Model.Home;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Visitor;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.Home.IVisitorModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Url;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 访客管理
 * @Email DramaScript@outlook.com
 * @date 2016/8/25
 */
public class VisitorModel implements IVisitorModel {

    private Observable<ListCommonResult<Visitor>> listObservable;

    @Override
    public void getVisitor(String communityId,int page, int pageSize) {
        listObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .loadGuestList(communityId,page, pageSize)
                .map(new Func1<ListCommonResult<Visitor>, ListCommonResult<Visitor>>() {
                    @Override
                    public ListCommonResult<Visitor> call(ListCommonResult<Visitor> visitorListCommonResult) {
                        return visitorListCommonResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ListCommonResult<Visitor>> subscriber) {
        listObservable.subscribe(subscriber);
    }
}
