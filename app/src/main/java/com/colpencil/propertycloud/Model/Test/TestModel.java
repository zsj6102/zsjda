package com.colpencil.propertycloud.Model.Test;

import com.colpencil.propertycloud.Api.TestApi;
import com.colpencil.propertycloud.Api.TestMvpApi;
import com.colpencil.propertycloud.Bean.ContentlistEntity;
import com.colpencil.propertycloud.Bean.ListEntity;
import com.colpencil.propertycloud.Bean.TestEntity;
import com.colpencil.propertycloud.Bean.TestMvpEntity;
import com.colpencil.propertycloud.Model.Imples.ITestModel;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RxServiceModule;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RxTestServiceModule;

import java.util.ArrayList;
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
 * <p/>
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p/>
 * 作者：LigthWang
 * <p/>
 * 描述：
 */
public class TestModel implements ITestModel {

    private Observable<List<ListEntity>> listObservable;

    @Override
    public void loadData(int pageNo,int pageSize) {
        listObservable= RxTestServiceModule.createApi(TestApi.class)
                .getJoke(pageNo,pageSize)
                .subscribeOn(Schedulers.io())
                .map(new Func1<TestEntity, List<ListEntity>>() {

                    @Override
                    public List<ListEntity> call(TestEntity testEntity) {

                        return testEntity.getResult().getList();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<List<ListEntity>> subscriber) {
        listObservable.subscribe(subscriber);
    }
}
