package com.colpencil.tenement.Present.Home;

import com.colpencil.tenement.Bean.CleanRecord;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Model.Home.CleanRecordModel;
import com.colpencil.tenement.Model.Imples.Home.ICleanRecordModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.CleanRecordView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Observer;
import rx.Subscriber;

public class CleanRecordPresenter extends ColpencilPresenter<CleanRecordView> {

    private ICleanRecordModel model;

    public CleanRecordPresenter() {
        model = new CleanRecordModel();
    }

    /**
     * 请求保洁记录
     *
     * @param type
     * @param page
     * @param pageSize
     */
    public void loadRecord(String communityId,int type, final int page, int pageSize,String beginTime,String endTime,int self) {
        model.loadRecords(communityId,type, page, pageSize,beginTime,endTime,self);
        Observer<ListCommonResult<CleanRecord>> observer = new Observer<ListCommonResult<CleanRecord>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<CleanRecord> result) {
                int code = result.getCode();
                String message = result.getMessage();
                if (code == 0||code==2||code==3) {
                    if (page == 1) {
                        mView.refresh(result);
                    } else {
                        mView.loadMore(result);
                        ColpencilLogger.e("加载更多。。。。。");
                    }
                } else {
                    mView.loadError(message);
                }
            }
        };
        model.subRecords(observer);
    }

    public void loadVillage(){
        model.loadVillage();
        Subscriber<ListCommonResult<Village>> subscriber = new Subscriber<ListCommonResult<Village>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadVillageError(e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<Village> result) {
                mView.loadCommunity(result);
            }
        };
        model.subVillage(subscriber);
    }
}
