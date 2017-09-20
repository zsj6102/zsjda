package com.colpencil.propertycloud.Present.Home;

import com.colpencil.propertycloud.Bean.LiveStreaming;
import com.colpencil.propertycloud.Model.Home.LiveStreamListModel;
import com.colpencil.propertycloud.Model.Imples.Home.ILiveStreamListModel;
import com.colpencil.propertycloud.View.Imples.StreamingListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 摄像头列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
public class LiveStreamListPresent extends ColpencilPresenter<StreamingListView> {

    private ILiveStreamListModel model;

    public LiveStreamListPresent(){
        model = new LiveStreamListModel();
    }

    public void getList(){
        model.getList();
        Subscriber<List<LiveStreaming>> subscriber = new Subscriber<List<LiveStreaming>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<LiveStreaming> list) {
                mView.getList(list);
            }
        };
        model.sub(subscriber);
    }
}
