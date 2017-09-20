package com.colpencil.propertycloud.Model.Home;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.AdviceList;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.Imples.Home.IAdviceListModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 物业建议列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
public class AdviceListModel implements IAdviceListModel {


    private Observable<ResultListInfo<AdviceList>> observable;

    @Override
    public void getList(int page,int pageSize) {
        HashMap<String,String> map = new HashMap<>();
        observable = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .opinion(map)
                .map(new Func1<ResultListInfo<AdviceList>, ResultListInfo<AdviceList>>() {
                    @Override
                    public ResultListInfo<AdviceList> call(ResultListInfo<AdviceList> adviceListResultListInfo) {
                        return adviceListResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultListInfo<AdviceList>> subscriber) {
        observable.subscribe(subscriber);
    }
}
