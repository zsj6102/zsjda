package com.colpencil.propertycloud.Present.Home;

import com.colpencil.propertycloud.Bean.AdviceList;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.Home.AdviceListModel;
import com.colpencil.propertycloud.Model.Imples.Home.IAdviceListModel;
import com.colpencil.propertycloud.View.Imples.AdviceListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 物业建议列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
public class AdviceListPresent extends ColpencilPresenter<AdviceListView> {

    private IAdviceListModel model;

    public AdviceListPresent(){
        model = new AdviceListModel();
    }

    public void getList(final int page, int pageSize){
        model.getList(page,pageSize);
        Subscriber<ResultListInfo<AdviceList>> subscriber = new Subscriber<ResultListInfo<AdviceList>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e( "服务器错误：" + e.getMessage());
            }

            @Override
            public void onNext(ResultListInfo<AdviceList> list) {
                int code = list.code;
                String message = list.message;
                if (code==0||code==2||code ==3){
                    if (page==1){
                        mView.refresh(list);
                    }else {
                        mView.loadMore(list);
                    }
                }else {
                    mView.loadError(message);
                }
            }
        };
        model.sub(subscriber);
    }

}
