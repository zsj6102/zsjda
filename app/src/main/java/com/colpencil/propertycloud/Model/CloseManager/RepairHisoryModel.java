package com.colpencil.propertycloud.Model.CloseManager;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.RepairHistory;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IRepairHistoryModel;
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
 * @Description: 历史报修
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/11
 */
public class RepairHisoryModel implements IRepairHistoryModel{

    private Observable<ResultListInfo<RepairHistory>> observable;

    @Override
    public void getHistory(int pageNo, int pageSize) {
        HashMap<String,String> map = new HashMap<>();
        map.put("pageNo",pageNo+"");
        map.put("pageSize",pageSize+"");
        observable = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getRepairHistory(map)
                .map(new Func1<ResultListInfo<RepairHistory>, ResultListInfo<RepairHistory>>() {
                    @Override
                    public ResultListInfo<RepairHistory> call(ResultListInfo<RepairHistory> repairHistoryResultListInfo) {
                        return repairHistoryResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultListInfo<RepairHistory>> subscriber) {
        observable.subscribe(subscriber);
    }
}
