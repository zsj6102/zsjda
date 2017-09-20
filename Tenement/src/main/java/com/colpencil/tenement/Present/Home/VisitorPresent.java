package com.colpencil.tenement.Present.Home;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Visitor;
import com.colpencil.tenement.Model.Home.VisitorModel;
import com.colpencil.tenement.Model.Imples.Home.IVisitorModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.VisitorView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.List;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 访客管理
 * @Email DramaScript@outlook.com
 * @date 2016/8/25
 */
public class VisitorPresent extends ColpencilPresenter<VisitorView> {

    private IVisitorModel model;

    public VisitorPresent() {
        model = new VisitorModel();
    }

    public void getVisitorList(String communityId, int page, int pageSize) {
        model.getVisitor(communityId,page, pageSize);
        Subscriber<ListCommonResult<Visitor>> subscriber = new Subscriber<ListCommonResult<Visitor>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<Visitor> result) {
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
        model.sub(subscriber);
    }

}
