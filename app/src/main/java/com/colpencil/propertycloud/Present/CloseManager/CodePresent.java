package com.colpencil.propertycloud.Present.CloseManager;

import com.colpencil.propertycloud.Bean.AuthCode;
import com.colpencil.propertycloud.Model.CloseManager.CodeModel;
import com.colpencil.propertycloud.Model.Imples.CloseManager.ICodeModel;
import com.colpencil.propertycloud.View.Imples.AuthCodeView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Subscriber;

/**
 * @Description: 获取验证码
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/9
 */
public class CodePresent extends ColpencilPresenter<AuthCodeView> {

    private ICodeModel model;

    public CodePresent(){
        model = new CodeModel();
    }

    public void getCode(String phone){
        model.getCode(phone);
        Subscriber<AuthCode> subscriber = new Subscriber<AuthCode>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AuthCode authCode) {
                mView.getCode(authCode);
            }
        };
        model.sub(subscriber);
    }

}
