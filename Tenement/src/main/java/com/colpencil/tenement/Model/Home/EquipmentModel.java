package com.colpencil.tenement.Model.Home;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.Equipment;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.Home.IEquipmentModel;
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
 * @Description: 设备管理
 * @Email DramaScript@outlook.com
 * @date 2016/8/23
 */
public class EquipmentModel implements IEquipmentModel {

    private Observable<ListCommonResult<Equipment>> listObservable;

    @Override
    public void getEquipmentList(String communityId,int type, int page, int pageSize,String devCode,String devName,int self,String startDate,String endDate) {
        listObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .loadEquipment(communityId,type, page, pageSize,devCode,devName,self,startDate,endDate)
                .map(new Func1<ListCommonResult<Equipment>, ListCommonResult<Equipment>>() {
                    @Override
                    public ListCommonResult<Equipment> call(ListCommonResult<Equipment> equipmentListCommonResult) {
                        return equipmentListCommonResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ListCommonResult<Equipment>> subscriber) {
        listObservable.subscribe(subscriber);
    }
}
