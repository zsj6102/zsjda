package com.colpencil.propertycloud.Present.CloseManager;

import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Model.CloseManager.ChangeInfoModel;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IChangeInfoModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.View.Imples.ChangeInfoView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Subscriber;

/**
 * @Description: 更改信息
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/11
 */
public class ChangeInfoPresent extends ColpencilPresenter<ChangeInfoView> {

    private IChangeInfoModel model;

    public ChangeInfoPresent(){
        model = new ChangeInfoModel();
    }

    public void change(int flag,String changeInfo){
        model.changeInfo(flag,changeInfo);
        Subscriber<ResultInfo> subscriber = new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误信息"+e.getMessage());
            }

            @Override
            public void onNext(ResultInfo resultInfo) {
                mView.isChange(resultInfo);
            }
        };
        model.sub(subscriber);
    }


}
