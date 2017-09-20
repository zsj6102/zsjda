package com.colpencil.propertycloud.Model.Home;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ComplaintHistroy;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.Imples.Home.IHistoryModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
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
 * @author 汪 亮
 * @Description: 历史投诉
 * @Email DramaScript@outlook.com
 * @date 2016/9/5
 */
public class HistroyModel implements IHistoryModel {

    private Observable<ResultListInfo<ComplaintHistroy>> listInfoObservable;

    @Override
    public void getList(int page, int type, int pageSize) {
        HashMap<String, String> map = new HashMap<>();
        map.put("pageNo", page + "");
        map.put("type", type + "");
        map.put("pageSize", pageSize + "");
        listInfoObservable = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getCompHistoryList(map)
                .map(new Func1<ResultListInfo<ComplaintHistroy>, ResultListInfo<ComplaintHistroy>>() {
                    @Override
                    public ResultListInfo<ComplaintHistroy> call(ResultListInfo<ComplaintHistroy> complaintHistroyResultListInfo) {
                        return complaintHistroyResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public void sub(Subscriber<ResultListInfo<ComplaintHistroy>> subscriber) {
        listInfoObservable.subscribe(subscriber);
    }
}
