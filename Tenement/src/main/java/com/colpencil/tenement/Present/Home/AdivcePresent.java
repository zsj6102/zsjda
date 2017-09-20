package com.colpencil.tenement.Present.Home;

import android.util.Log;

import com.colpencil.tenement.Bean.Advice;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Model.Home.AdviceModel;
import com.colpencil.tenement.Model.Imples.Home.IAdviceModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.AdviceView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 投诉建议
 * @Email DramaScript@outlook.com
 * @date 2016/8/26
 */
public class AdivcePresent extends ColpencilPresenter<AdviceView> {

    private IAdviceModel model;

    public AdivcePresent() {
        model = new AdviceModel();
    }

    public void loadAdviceList(String communityId,int type, final int page, int pageSize, int self) {
        model.getAdivceList(communityId,type, page, pageSize, self);
        Subscriber<ListCommonResult<Advice>> subscriber = new Subscriber<ListCommonResult<Advice>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<Advice> result) {
                int code = result.getCode();
                String message = result.getMessage();
                if (code==0||code==2||code==3){
                    if (page==1){
                        mView.refresh(result);
                    }else {
                        mView.loadMore(result);
                    }
                }else {
                    mView.loadError(message);
                }
            }
        };
        model.sub(subscriber);
    }

}
