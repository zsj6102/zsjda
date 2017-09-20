package com.colpencil.tenement.Model.Home;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.Home.ISelectModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:小区选择
 * @Email DramaScript@outlook.com
 * @date 2016/8/31
 */
public class SelectModel implements ISelectModel {

    private Observable<ListCommonResult<Village>> observable;

    @Override
    public void loadVilliage() {
        observable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .loadVillageList()
                .map(new Func1<ListCommonResult<Village>, ListCommonResult<Village>>() {
                    @Override
                    public ListCommonResult<Village> call(ListCommonResult<Village> villageListCommonResult) {
                        return villageListCommonResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<ListCommonResult<Village>> observer) {
        observable.subscribe(observer);
    }
}
