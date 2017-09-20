package com.colpencil.propertycloud.Present.Home;

import android.util.Log;

import com.colpencil.propertycloud.Bean.ComplaintHistroy;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.Home.HistroyModel;
import com.colpencil.propertycloud.Model.Imples.Home.IHistoryModel;
import com.colpencil.propertycloud.View.Imples.HistroyView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 历史投诉
 * @Email DramaScript@outlook.com
 * @date 2016/9/5
 */
public class HistroyPresent extends ColpencilPresenter<HistroyView> {

    private IHistoryModel model;

    public HistroyPresent() {
        model = new HistroyModel();
    }

    public void getList(final int page, int type, int pageSize) {
        model.getList(page, type, pageSize);
        Subscriber<ResultListInfo<ComplaintHistroy>> subscriber = new Subscriber<ResultListInfo<ComplaintHistroy>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResultListInfo<ComplaintHistroy> complaintHistroyResultListInfo) {
                int code = complaintHistroyResultListInfo.code;
                String message = complaintHistroyResultListInfo.message;
                if (code == 1) {
                    mView.loadError(message);
                } else {
                    if (page == 1) {
                        mView.refresh(complaintHistroyResultListInfo);
                    } else {
                        mView.loadMore(complaintHistroyResultListInfo);
                    }
                }
            }
        };
        model.sub(subscriber);
    }

}
