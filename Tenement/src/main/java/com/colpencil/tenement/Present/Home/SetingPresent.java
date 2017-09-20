package com.colpencil.tenement.Present.Home;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Model.Home.SetingModel;
import com.colpencil.tenement.Model.Imples.Home.ISettingModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.SetView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Subscriber;

/**
 * @Description: 设置界面
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/29
 */
public class SetingPresent extends ColpencilPresenter<SetView> {

    private ISettingModel model;

    public SetingPresent(){
        model = new SetingModel();
    }

    public void loginOut(String coumitiId){
        model.loginOut(coumitiId);
        Subscriber<EntityResult<String>> subscriber = new Subscriber<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.loadError("服务器异常");
            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.loginOut(result);
            }
        };
        model.sub(subscriber);
    }

}
