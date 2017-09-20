package com.colpencil.propertycloud.Present.Home;

import com.colpencil.propertycloud.Bean.Current;
import com.colpencil.propertycloud.Model.Home.CurrentModel;
import com.colpencil.propertycloud.Model.Imples.Home.ICurrentModel;
import com.colpencil.propertycloud.View.Imples.CurrentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 装修当前进度
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
public class CurrentPresent extends ColpencilPresenter<CurrentView> {

    private ICurrentModel model;

    public CurrentPresent(){
        model = new CurrentModel();
    }

    public void getCurrent(){
        model.getList();
        Subscriber<List<Current>> subscriber = new Subscriber<List<Current>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Current> list) {
                mView.getInfoList(list);
            }
        };
        model.sub(subscriber);
    }

}
