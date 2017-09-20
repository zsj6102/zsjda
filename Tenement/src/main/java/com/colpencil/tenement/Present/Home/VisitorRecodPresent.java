package com.colpencil.tenement.Present.Home;

import com.colpencil.tenement.Model.Home.VisitorRecodModel;
import com.colpencil.tenement.Model.Imples.Home.IVisitorRecordModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.VisitorRecordView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Subscriber;

/**
 * @Description: 访客登记
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/26
 */
public class VisitorRecodPresent extends ColpencilPresenter<VisitorRecordView>{

    private IVisitorRecordModel model;

    public VisitorRecodPresent(){
        model = new VisitorRecodModel();
    }

    public void getImgeUrl(){
        model.getimageUrl();
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(String s) {
                mView.getReacod(s);
            }
        };

        model.sub(subscriber);
    }

}
