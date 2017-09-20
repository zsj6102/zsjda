package com.colpencil.propertycloud.Present.CloseManager;

import com.colpencil.propertycloud.Bean.ListBean;
import com.colpencil.propertycloud.Bean.OtherOrder;
import com.colpencil.propertycloud.Model.CloseManager.OtherOrderModel;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IOtherOrderModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.View.Imples.OtherOrderView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.List;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 其他订单
 * @Email DramaScript@outlook.com
 * @date 2016/9/8
 */
public class OtherOrderPresent extends ColpencilPresenter<OtherOrderView> {

    private IOtherOrderModel model;

    public OtherOrderPresent() {
        model = new OtherOrderModel();
    }

    public void getOrderList(final int pageNo, int pageSize) {
        model.getOrder(pageNo, pageSize);
        Subscriber<ListBean<OtherOrder>> subscriber = new Subscriber<ListBean<OtherOrder>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastTools.showShort(CluodApplaction.getInstance(), "服务器错误信息" + e.getMessage());
            }

            @Override
            public void onNext(ListBean<OtherOrder> list) {
                int code = list.code;
                String message = list.message;
                if (code == 0) {
                    if (pageNo == 1) {
                        mView.refresh(list);
                    } else {
                        mView.loadMore(list);
                    }
                } else {
                    mView.loadError(message);
                }
            }
        };
        model.sub(subscriber);
    }

}
