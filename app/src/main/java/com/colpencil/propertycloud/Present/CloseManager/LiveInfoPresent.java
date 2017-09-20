package com.colpencil.propertycloud.Present.CloseManager;

import com.colpencil.propertycloud.Bean.LiveInfoList;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.CloseManager.LiveInfoModel;
import com.colpencil.propertycloud.Model.Imples.CloseManager.ILiveInfoModel;
import com.colpencil.propertycloud.View.Imples.LiveInfoListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 居住情况
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
public class LiveInfoPresent extends ColpencilPresenter<LiveInfoListView> {

    private ILiveInfoModel model;

    public LiveInfoPresent(){
        model = new LiveInfoModel();
    }

    public void getLiveInfo(){
        model.getLiveInfo();
        Subscriber<ResultListInfo<LiveInfoList>> subscriber = new Subscriber<ResultListInfo<LiveInfoList>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(ResultListInfo<LiveInfoList> liveInfoLists) {
                int code = liveInfoLists.code;
                String message = liveInfoLists.message;
                if (code==0||code==2||code==3){
                    mView.liveInfoList(liveInfoLists);
                }else {
                    mView.loadError(message);
                }
            }
        };
        model.sub(subscriber);
    }

}
