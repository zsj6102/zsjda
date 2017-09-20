package com.colpencil.propertycloud.Model.Test;

import com.colpencil.propertycloud.Api.TestMvpApi;
import com.colpencil.propertycloud.Bean.ContentlistEntity;
import com.colpencil.propertycloud.Bean.TestMvpEntity;
import com.colpencil.propertycloud.Model.Imples.ITestMvpModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RxServiceModule;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * <p>
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * 作者：LigthWang
 * <p>
 * 描述：
 */
public class TestMvpModel implements ITestMvpModel {

    private Observable<List<ContentlistEntity>> listObservable;

    @Override
    public void loadData(int page) {
        listObservable = RetrofitManager
                .getInstance(1, CluodApplaction.getInstance(),"http://apis.baidu.com/showapi_open_bus/")
                .createApi(TestMvpApi.class).getJoke(page)
                .subscribeOn(Schedulers.io())
                .map(new Func1<TestMvpEntity, List<ContentlistEntity>>() {
                    @Override
                    public List<ContentlistEntity> call(TestMvpEntity testMvpEntity) {
                        return testMvpEntity.getShowapi_res_body().getContentlist();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<List<ContentlistEntity>> subscriber) {
        listObservable.subscribe(subscriber);
    }

}
