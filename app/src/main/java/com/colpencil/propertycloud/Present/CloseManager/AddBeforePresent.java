package com.colpencil.propertycloud.Present.CloseManager;

import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Model.CloseManager.AddBeforeModel;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IAddBefroeModel;
import com.colpencil.propertycloud.View.Imples.AddBeforeView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * @Description: 添加成员之前
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/13
 */
public class AddBeforePresent extends ColpencilPresenter<AddBeforeView> {

    private IAddBefroeModel model;

    public AddBeforePresent(){
        model = new AddBeforeModel();
    }

    public void getCode(String mobile, int flag){
        model.getCode(mobile, flag);
        Subscriber<ResultInfo> subscriber = new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo s) {
                mView.codeInfo(s);
            }
        };
        model.sub(subscriber);
    }


}
