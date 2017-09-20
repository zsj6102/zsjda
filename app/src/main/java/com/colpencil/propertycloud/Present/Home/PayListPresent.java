package com.colpencil.propertycloud.Present.Home;

import com.colpencil.propertycloud.Bean.ListBean;
import com.colpencil.propertycloud.Bean.PayList;
import com.colpencil.propertycloud.Model.Home.PayListModel;
import com.colpencil.propertycloud.Model.Imples.Home.IPayListModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.View.Imples.PayListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.List;

import rx.Subscriber;

/**
 * @Description:  物业缴费列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/6
 */
public class PayListPresent extends ColpencilPresenter<PayListView> {

    private IPayListModel model;

    public PayListPresent(){
        model = new PayListModel();
    }

    public void getList(final int page, int pageSize, String payItemsId,String billIds){
        model.getList(page,pageSize,payItemsId,billIds);
        Subscriber<ListBean<PayList>> subscriber = new Subscriber<ListBean<PayList>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(ListBean<PayList> result) {
                int code = result.code;
                String message = result.message;
                if (code == 0||code==2||code==3) {
                    if (page == 1) {
                        mView.refresh(result);
                    } else {
                        mView.loadMore(result);
                    }
                } else {
                    mView.loadError(message);
                }
            }
        };
        model.sub(subscriber);
    }

}
