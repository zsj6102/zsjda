package com.colpencil.tenement.Model.OnlineTalk;

import android.util.Log;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.OnlineListUser;
import com.colpencil.tenement.Bean.OnlineUser;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.OnlineTalk.IOnlineTalkListModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 在线对讲列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/18
 */
public class OnlineTalkListModel implements IOnlineTalkListModel{


    private Observable<ListCommonResult<OnlineListUser>> listCommonResultObservable;

    private Observable<ListCommonResult<Village>> observable;

    @Override
    public void getOnlineTalkList(String communityId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("communityId", communityId);
        listCommonResultObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .getOnline(params)
                .map(new Func1<ListCommonResult<OnlineListUser>, ListCommonResult<OnlineListUser>>() {
                    @Override
                    public ListCommonResult<OnlineListUser> call(ListCommonResult<OnlineListUser> onlineListUserListCommonResult) {
                        return onlineListUserListCommonResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ListCommonResult<OnlineListUser>> subscriber) {
        listCommonResultObservable.subscribe(subscriber);
    }

    @Override
    public void loadVillage() {
        HashMap<String,String> map = new HashMap<>();
        observable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .getVillage(map)
                .map(new Func1<ListCommonResult<Village>, ListCommonResult<Village>>() {
                    @Override
                    public ListCommonResult<Village> call(ListCommonResult<Village> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subVillage(Subscriber<ListCommonResult<Village>> subscriber) {
        observable.subscribe(subscriber);
    }

}