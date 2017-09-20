package com.colpencil.propertycloud.Present.CloseManager;

import com.colpencil.propertycloud.Bean.MenberInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.CloseManager.MenberModel;
import com.colpencil.propertycloud.Model.Imples.IMenberModel;
import com.colpencil.propertycloud.View.Imples.IMenberView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

public class MenberPresent extends ColpencilPresenter<IMenberView> {

    private IMenberModel model;

    public MenberPresent() {
        model = new MenberModel();
    }

    public void loadData(int hseid) {
        model.loadData(hseid);
        Subscriber<ResultListInfo<MenberInfo>> subscriber = new Subscriber<ResultListInfo<MenberInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultListInfo<MenberInfo> resultListInfo) {
                mView.loadResult(resultListInfo);
            }
        };
        model.sub(subscriber);
    }
}
