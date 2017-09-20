package com.colpencil.tenement.Present.Home;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Result;
import com.colpencil.tenement.Bean.WaterInfo;
import com.colpencil.tenement.Model.Home.WriteWatermeterModel;
import com.colpencil.tenement.Model.Imples.Home.IWriteWatermeterModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.WriteWatermeterView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Observer;
import rx.Subscriber;

/**
 * @author 陈宝
 * @Description:开始抄表
 * @Email DramaScript@outlook.com
 * @date 2016/9/2
 */
public class WriteWatermeterPresent extends ColpencilPresenter<WriteWatermeterView> {

    private IWriteWatermeterModel model;

    public WriteWatermeterPresent() {
        model = new WriteWatermeterModel();
    }

    public void submit(String roomId, String ownerName, String waterId, String monthCoast, int type,String lastRecord,String communityId,String recordId) {
        model.submit(roomId, ownerName, waterId, monthCoast, type,lastRecord,communityId,recordId);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.submitResult(result);
            }
        };
        model.sub(observer);
    }

    public void getLast(String dev,String type){
        model.getLast(dev,type);
        Subscriber<EntityResult<WaterInfo>> subscriber = new Subscriber<EntityResult<WaterInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误数据："+e.getMessage());
            }

            @Override
            public void onNext(EntityResult<WaterInfo> result) {
                mView.getLast(result);
            }
        };
        model.subLast(subscriber);
    }

}
