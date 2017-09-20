package com.colpencil.tenement.Model.Home;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.OnlineListUser;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.Home.IAssignEmployeeListModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/13.
 */

public class AssignEmployeeListModel implements IAssignEmployeeListModel {

    private Observable<ListCommonResult<OnlineListUser>> observableUserList;

    @Override
    public void getAssignEmployeeList(String communityId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("communityId", communityId);
        observableUserList = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
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
        observableUserList.subscribe(subscriber);
    }
}
