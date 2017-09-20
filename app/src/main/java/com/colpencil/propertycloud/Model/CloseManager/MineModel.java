package com.colpencil.propertycloud.Model.CloseManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.Mine;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IMineModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.lzy.imagepicker.bean.ImageItem;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.BitmapUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

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
 * @Description: 我的资料
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/13
 */
public class MineModel implements IMineModel {

    private Observable<ResultInfo> loginOut;
    private Observable<ResultInfo<Mine>> mineObser;
    private File file;
    private Observable<ResultInfo> headObser;

    @Override
    public void loginOut(String memberId, String comuId) {
        loginOut = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loginOut(comuId)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subLoginOut(Subscriber<ResultInfo> subscriber) {
        loginOut.subscribe(subscriber);
    }

    @Override
    public void getMineInfo() {
        HashMap<String,String> map = new HashMap<>();
        String mobile = SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).getString("mobile");
        map.put("mobile",mobile);
        mineObser = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getMineInfo(map)
                .map(new Func1<ResultInfo<Mine>, ResultInfo<Mine>>() {
                    @Override
                    public ResultInfo<Mine> call(ResultInfo<Mine> mineResultInfo) {
                        return mineResultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subMineInfo(Subscriber<ResultInfo<Mine>> subscriber) {
        mineObser.subscribe(subscriber);
    }

    @Override
    public void changeHead(File file) {
        Map<String, RequestBody> map = new HashMap<>();
//        file = new File(imageItem.path);
        map.put("face\";filename=\""+"hh"+".jpg",RequestBody.create(MediaType.parse("image/png"), file));
        headObser = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .changeHead(map)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subChangeHead(Subscriber<ResultInfo> subscriber) {
        headObser.subscribe(subscriber);
    }
}
