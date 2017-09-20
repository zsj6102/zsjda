package com.colpencil.tenement.Model.TodayTask;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Sign;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Model.Imples.TodayTask.ISignModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.OkhttpUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 签到/签退
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/23
 */
public class SignModel implements ISignModel {

    private Observable<EntityResult<Sign>> observableIn;
    private Observable<EntityResult<Sign>> observableOut;

    @Override
    public void signIn(String location, File signImage) {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("location", OkhttpUtils.toRequestBody(location));
        map.put("signImage\";filename=\""+".png",RequestBody.create(MediaType.parse("image/png"), signImage));
        observableIn = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .signIn(map)
                .map(new Func1<EntityResult<Sign>, EntityResult<Sign>>() {
                    @Override
                    public EntityResult<Sign> call(EntityResult<Sign> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subIn(Subscriber<EntityResult<Sign>> subscriber) {
        observableIn.subscribe(subscriber);
    }

    @Override
    public void signOut(String location, File signImage) {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("location", OkhttpUtils.toRequestBody(location));
        map.put("signImage\";filename=\""+"signOut",RequestBody.create(MediaType.parse("image/png"), signImage));
        observableOut = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .signOut(map)
                .map(new Func1<EntityResult<Sign>, EntityResult<Sign>>() {
                    @Override
                    public EntityResult<Sign> call(EntityResult<Sign> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subOut(Subscriber<EntityResult<Sign>> subscriber) {
        observableOut.subscribe(subscriber);
    }
}
