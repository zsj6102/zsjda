package com.colpencil.propertycloud.Model.CloseManager;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.LiveInfoList;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.Imples.CloseManager.ILiveInfoModel;
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
 * @Description: 居住情况
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
public class LiveInfoModel implements ILiveInfoModel {


    private Observable<ResultListInfo<LiveInfoList>> observable;

    @Override
    public void getLiveInfo() {
        HashMap<String,String> map = new HashMap<>();
        observable = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .livingCond(map)
                .map(new Func1<ResultListInfo<LiveInfoList>, ResultListInfo<LiveInfoList>>() {
                    @Override
                    public ResultListInfo<LiveInfoList> call(ResultListInfo<LiveInfoList> liveInfoListResultListInfo) {
                        return liveInfoListResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultListInfo<LiveInfoList>> subscriber) {
        observable.subscribe(subscriber);
    }
}
