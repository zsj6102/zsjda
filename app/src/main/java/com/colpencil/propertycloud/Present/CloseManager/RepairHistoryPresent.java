package com.colpencil.propertycloud.Present.CloseManager;

import com.colpencil.propertycloud.Bean.RepairHistory;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.CloseManager.RepairHisoryModel;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IRepairHistoryModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.View.Imples.RepailHistoryView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 历史报修
 * @Email DramaScript@outlook.com
 * @date 2016/10/11
 */
public class RepairHistoryPresent extends ColpencilPresenter<RepailHistoryView> {

    private IRepairHistoryModel model;

    public RepairHistoryPresent() {
        model = new RepairHisoryModel();
    }

    public void getHistory(final int pageNo, int pageSize) {
        model.getHistory(pageNo, pageSize);
        Subscriber<ResultListInfo<RepairHistory>> subscriber = new Subscriber<ResultListInfo<RepairHistory>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                ToastTools.showShort(CluodApplaction.getInstance(), "服务器错误信息" + e.getMessage());
            }

            @Override
            public void onNext(ResultListInfo<RepairHistory> repairHistoryResultListInfo) {
                int code = repairHistoryResultListInfo.code;
                String message = repairHistoryResultListInfo.message;
                if (code == 0 || code == 2 || code == 3) {
                    if (pageNo == 1) {
                        mView.refresh(repairHistoryResultListInfo);
                    } else {
                        mView.loadMore(repairHistoryResultListInfo);
                    }
                } else {
                    mView.loadError(message);
                }
            }
        };
        model.sub(subscriber);
    }

}
