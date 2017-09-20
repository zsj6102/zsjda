package com.colpencil.propertycloud.Model.CloseManager;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IAddBefroeModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 添加成员之前
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/13
 */
public class AddBeforeModel implements IAddBefroeModel{

    private Observable<ResultInfo> getCode;

    @Override
    public void getCode(String mobile, int flag) {
        getCode = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getCode(mobile, flag,0)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<ResultInfo> subscriber) {
        getCode.subscribe(subscriber);
    }
}
