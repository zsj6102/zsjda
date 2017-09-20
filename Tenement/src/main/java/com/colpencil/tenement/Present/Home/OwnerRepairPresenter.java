package com.colpencil.tenement.Present.Home;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.OwnerRepair;
import com.colpencil.tenement.Model.Home.OwnerRepairModel;
import com.colpencil.tenement.Model.Imples.IOwnerRepairModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.OwnerRepairView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:业主报修
 * @Email DramaScript@outlook.com
 * @date 2016/8/25
 */
public class OwnerRepairPresenter extends ColpencilPresenter<OwnerRepairView> {

    private IOwnerRepairModel model;

    public OwnerRepairPresenter() {
        model = new OwnerRepairModel();
    }

    /**
     * 获取报修记录
     *
     * @param type
     * @param page
     * @param pageSize
     */
    public void loadRepairList(String communityId,int type, final int page, int pageSize, int self) {
        model.loadRepairList(communityId,type, page, pageSize, self);
        Observer<ListCommonResult<OwnerRepair>> observer =  new Observer<ListCommonResult<OwnerRepair>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<OwnerRepair> result) {
                int code = result.getCode();
                String message = result.getMessage();
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
        model.subRepairList(observer);
    }
}
