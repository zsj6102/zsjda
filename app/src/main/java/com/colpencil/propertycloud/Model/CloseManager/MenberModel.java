package com.colpencil.propertycloud.Model.CloseManager;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.MenberInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.Imples.IMenberModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈 宝
 * @Description:成员管理
 * @Email 1041121352@qq.com
 * @date 2016/11/23
 */
public class MenberModel implements IMenberModel {

    private Observable<ResultListInfo<MenberInfo>> observable;

    @Override
    public void loadData(int hseid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("hseid", hseid + "");
        observable = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .memberList(map)
                .map(new Func1<ResultListInfo<MenberInfo>, ResultListInfo<MenberInfo>>() {
                    @Override
                    public ResultListInfo<MenberInfo> call(ResultListInfo<MenberInfo> menberInfoResultListInfo) {
                        return menberInfoResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultListInfo<MenberInfo>> subscriber) {
        observable.subscribe(subscriber);
    }
}
