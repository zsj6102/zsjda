package com.colpencil.propertycloud.Model.CloseManager;

import android.text.TextUtils;
import android.util.Log;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.Bean.HouseInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IRepairsModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
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
 * @Description: 物业报修
 * @Email DramaScript@outlook.com
 * @date 2016/10/11
 */
public class RepairsModel implements IRepairsModel {

    private Observable<ResultInfo> observable;
    private Observable<ResultListInfo<HouseInfo>> estate;
    private Observable<ResultListInfo<CellInfo>> cells;

    @Override
    public void submitRepair(int type, String describe, String url_audio, String book_time, String book_site, String book_mobile,
                             String license, String comuId, List<File> files, int build_id, int unit_id, int house_id, String audio_duration) {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("type", OkhttpUtils.toRequestBody(type + ""));
        map.put("describe", OkhttpUtils.toRequestBody(describe));
        map.put("book_time", OkhttpUtils.toRequestBody(book_time));
        map.put("book_site", OkhttpUtils.toRequestBody(book_site));
        map.put("book_mobile", OkhttpUtils.toRequestBody(book_mobile));
        map.put("license", OkhttpUtils.toRequestBody(license));
        map.put("comuId", OkhttpUtils.toRequestBody(comuId));
        if (!TextUtils.isEmpty(url_audio)) {
            File audioFile = new File(url_audio);
            map.put("url_audio\";filename=\"" + audioFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), audioFile));
            map.put("audio_duration", OkhttpUtils.toRequestBody(audio_duration));
        }
        if (files.size() != 0) {
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                map.put("photos\";filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
            }
        }
        if (type == 0) {
            map.put("building_id", OkhttpUtils.toRequestBody(build_id + ""));
            map.put("unit_id", OkhttpUtils.toRequestBody(unit_id + ""));
            map.put("house_id", OkhttpUtils.toRequestBody(house_id + ""));
        }
        observable = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .submitRepairs(map)
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
    public void loadEstate() {
        estate = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadEstate(new HashMap<String, String>())
                .map(new Func1<ResultListInfo<HouseInfo>, ResultListInfo<HouseInfo>>() {
                    @Override
                    public ResultListInfo<HouseInfo> call(ResultListInfo<HouseInfo> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subEstate(Subscriber<ResultListInfo<HouseInfo>> subscriber) {
        estate.subscribe(subscriber);
    }

    @Override
    public void loadCell() {
        cells = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadCell(new HashMap<String, String>())
                .map(new Func1<ResultListInfo<CellInfo>, ResultListInfo<CellInfo>>() {
                    @Override
                    public ResultListInfo<CellInfo> call(ResultListInfo<CellInfo> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subCell(Subscriber<ResultListInfo<CellInfo>> subscriber) {
        cells.subscribe(subscriber);
    }

}
