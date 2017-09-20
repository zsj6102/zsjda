package com.colpencil.tenement.Present.Home;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Record;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Model.Home.VillageSelectModel;
import com.colpencil.tenement.Model.Imples.Home.IVillageSelectModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.VillageSelectView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Subscriber;

/**
 * @Description: 小区选择
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/14
 */
public class VillageSelectPresent extends ColpencilPresenter<VillageSelectView>{

    private IVillageSelectModel model;

    public VillageSelectPresent(){
        model = new VillageSelectModel();
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
            }

            @Override
            public void onNext(ListCommonResult<Village> result) {
                mView.loadCommunity(result);
            }
        };
        model.subVillage(subscriber);
    }


    public void loadVillageDefu(){
        model.loadVillage();
        Subscriber<ListCommonResult<Village>> subscriber = new Subscriber<ListCommonResult<Village>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<Village> result) {
                mView.loadCommunityDefu(result);
            }
        };
        model.subVillage(subscriber);
    }

    public void getMarker(String communityId){
        model.loadRecord(communityId);
        Subscriber<ListCommonResult<Record>> subscriber = new Subscriber<ListCommonResult<Record>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListCommonResult<Record> recordListCommonResult) {
                mView.loadMarker(recordListCommonResult);
            }
        };

        model.subRecord(subscriber);
    }
}
