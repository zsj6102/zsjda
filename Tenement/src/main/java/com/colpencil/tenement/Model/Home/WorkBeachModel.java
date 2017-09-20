package com.colpencil.tenement.Model.Home;

import com.colpencil.tenement.Bean.WorkBeach;
import com.colpencil.tenement.Model.Imples.Home.IWorkBeachModel;
import com.colpencil.tenement.R;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description: 工作台
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/17
 */
public class WorkBeachModel implements IWorkBeachModel {

    private Observable<List<WorkBeach>> listObservable;
    String[] name = {"水电抄表","设备管理","绿化保洁","巡逻保安","公共报修","投诉建议","访客管理","物业客服","小区视频","考勤签到","附近的员工"};
    int[] image = {R.mipmap.meter_reading,R.mipmap.equipment,R.mipmap.cleaning,R.mipmap.patrol,R.mipmap.paid_service,
            R.mipmap.complaint,R.mipmap.visitor,R.mipmap.customer_service,R.mipmap.community_video,R.mipmap.kaoqin,R.mipmap.ner_manger};
    List<WorkBeach> workBeachList = new ArrayList<>();

    @Override
    public void loadWorkBeach() {
        listObservable = Observable.create(new Observable.OnSubscribe<List<WorkBeach>>() {
            @Override
            public void call(Subscriber<? super List<WorkBeach>> subscriber) {
                for (int i=0;i<name.length;i++){
                    WorkBeach workBeach = new WorkBeach();
                    workBeach.imageId = image[i];
                    workBeach.name = name[i];
                    workBeachList.add(workBeach);
                }
                subscriber.onNext(workBeachList);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sub(Observer<List<WorkBeach>> subscriber) {
        listObservable.subscribe(subscriber);
    }
}
