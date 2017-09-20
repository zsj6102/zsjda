package com.colpencil.propertycloud.Model.Home;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.Material;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Model.Imples.Home.IMaterialListModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 材料管理
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
public class MaterialListModel implements IMaterialListModel {

    private List<Material> list = new ArrayList<>();
    private String[] name = {"《装修管理规定》","《装修承诺书》","《装修申请表》","《物业公司资质》扫描件","装修设计资料扫描件","《装修人员登记表》"};
    private Observable<List<Material>> listObservable;
    private Observable<ResultInfo> submitObser;

    @Override
    public void getList() {
        listObservable = Observable.create(new Observable.OnSubscribe<List<Material>>() {
            @Override
            public void call(Subscriber<? super List<Material>> subscriber) {
                for (int i = 0; i < name.length; i++) {
                    Material material = new Material();
                    material.name = name[i];
                    list.add(material);
                }
                subscriber.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Subscriber<List<Material>> subscriber) {
        listObservable.subscribe(subscriber);
    }

    @Override
    public void submit(String houseId, String approveid, String decortCoNm, String decortHead, String peopleNum,
                       String qualifiNo, String decortCoTel, String decortbeginTm, String decortEndTm,
                       String decortDesc, File decortDsnInfo, File decortManReguSign, List<File> decortCommitBookSign,
                       File decortCoQua, String isrentcam, String camnum) {
        Map<String, RequestBody> map = new HashMap<>();

        submitObser = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .submitDecortApprove(map)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void subSubmit(Subscriber<ResultInfo> subscriber) {
        submitObser.subscribe(subscriber);
    }
}
