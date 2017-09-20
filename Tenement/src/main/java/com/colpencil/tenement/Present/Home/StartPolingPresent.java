package com.colpencil.tenement.Present.Home;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.LastRecord;
import com.colpencil.tenement.Bean.Result;
import com.colpencil.tenement.Model.Home.StartPolingModel;
import com.colpencil.tenement.Model.Imples.Home.IStartPolingModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.StartPolingView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 开始保养/巡检/维修
 * @Email DramaScript@outlook.com
 * @date 2016/8/23
 */
public class StartPolingPresent extends ColpencilPresenter<StartPolingView> {

    private IStartPolingModel model;

    public StartPolingPresent() {
        model = new StartPolingModel();
    }

    public void post(int type, String eqId, String communityId, String currTime, String content, String lastTime,
                     String lastEmp, String maintainId,String eqType) {
        model.post(type, eqId, communityId, currTime, content, lastTime, lastEmp, maintainId,eqType);
        Subscriber<Result> subscriber = new Subscriber<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(Result result) {
                mView.post(result);
            }
        };
        model.sub(subscriber);
    }

    public void getLastRecord(String eqId,int type,String eqType) {
        model.getLast(eqId,type,eqType);
        Subscriber<EntityResult<LastRecord>> subscriber = new Subscriber<EntityResult<LastRecord>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(EntityResult<LastRecord> lastRecordEntityResult) {
                mView.getLast(lastRecordEntityResult);
            }
        };
        model.subLast(subscriber);
    }

    public void linkDev(String devId, String orderId,String content,String eqType) {
        model.linkDev(devId, orderId,content,eqType);
        Subscriber<Result> subscriber = new Subscriber<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(Result result) {
                mView.linkDev(result);
            }
        };
        model.subLinkDev(subscriber);
    }
}
