package com.colpencil.tenement.Model.Home;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.OwnerRepair;
import com.colpencil.tenement.Bean.RepairOrderAssign;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.IOwnerRepairModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:业主报修
 * @Email DramaScript@outlook.com
 * @date 2016/8/25
 */
public class OwnerRepairModel implements IOwnerRepairModel {

    private Observable<ListCommonResult<OwnerRepair>> repairList;

    @Override
    public void loadRepairList(String communityId,int type, int page, int pageSize, int self) {
        repairList = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .loadRepairList(communityId,type,page,pageSize,self)
                .map(new Func1<ListCommonResult<OwnerRepair>, ListCommonResult<OwnerRepair>>() {
                    @Override
                    public ListCommonResult call(ListCommonResult<OwnerRepair> listCommonResult) {
                        return listCommonResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subRepairList(Observer<ListCommonResult<OwnerRepair>> observer) {
        repairList.subscribe(observer);
    }

}
