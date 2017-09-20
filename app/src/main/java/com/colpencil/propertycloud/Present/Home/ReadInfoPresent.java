package com.colpencil.propertycloud.Present.Home;

import com.colpencil.propertycloud.Bean.ReadInfo;
import com.colpencil.propertycloud.Model.Home.ReadInfoModel;
import com.colpencil.propertycloud.Model.Imples.Home.IReadInfoModel;
import com.colpencil.propertycloud.View.Imples.ReadView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * @Description: 阅读类的H5
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/8
 */
public class ReadInfoPresent extends ColpencilPresenter<ReadView> {

    private IReadInfoModel model;

    public ReadInfoPresent(){
        model = new ReadInfoModel();
    }

    public void getInfo(){
        model.getInfo();
        Subscriber<ReadInfo> subscriber = new Subscriber<ReadInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ReadInfo readInfo) {
                mView.readInfo(readInfo);
            }
        };
        model.sub(subscriber);
    }

}
