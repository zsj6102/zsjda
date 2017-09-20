package com.colpencil.propertycloud.Model.Home;

import android.text.TextUtils;
import android.util.Log;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ComplaintType;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.Imples.Home.IComplaintModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.lzy.imagepicker.bean.ImageItem;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.OkhttpUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 物业投诉
 * @Email DramaScript@outlook.com
 * @date 2016/9/5
 */
public class ComplaintModel implements IComplaintModel {

    private Observable<ResultListInfo<ComplaintType>> typeoOservable;
    private Observable<ResultInfo> observable;

    @Override
    public void complaint(int catId, String detailDesc, String audioUrl, int type, List<File> files, long time) {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("catId", OkhttpUtils.toRequestBody(catId + ""));
        map.put("detailDesc", OkhttpUtils.toRequestBody(detailDesc));
        map.put("type", OkhttpUtils.toRequestBody(type + ""));
        for (int i = 0; i < files.size(); i++) {
            map.put("photos\";filename=\"" + files.get(i).getName(), RequestBody.create(MediaType.parse("image/png"), files.get(i)));
        }
        if (!TextUtils.isEmpty(audioUrl)) {
            File file = new File(audioUrl);
            Log.e("file", file.getAbsolutePath());
            map.put("url_audio\";filename=\"" + file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            map.put("audio_duration", OkhttpUtils.toRequestBody(time + ""));
        }
        observable = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .submitComplite(map)
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
        observable.subscribe(subscriber);
    }

    @Override
    public void getCompType(int type, String commuId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", type + "");
        map.put("commuId", commuId);
        typeoOservable = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getCompTypeList(map)
                .map(new Func1<ResultListInfo<ComplaintType>, ResultListInfo<ComplaintType>>() {
                    @Override
                    public ResultListInfo<ComplaintType> call(ResultListInfo<ComplaintType> resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subType(Subscriber<ResultListInfo<ComplaintType>> subscriber) {
        typeoOservable.subscribe(subscriber);
    }
}
