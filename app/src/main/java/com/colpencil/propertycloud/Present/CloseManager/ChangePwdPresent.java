package com.colpencil.propertycloud.Present.CloseManager;

import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Model.CloseManager.ChangePwdModel;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IChangePwdMode;
import com.colpencil.propertycloud.View.Imples.ChangePwdView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 忘记密码/修改密码
 * @Email DramaScript@outlook.com
 * @date 2016/11/10
 */
public class ChangePwdPresent extends ColpencilPresenter<ChangePwdView> {

    public IChangePwdMode mode;

    public ChangePwdPresent() {
        mode = new ChangePwdModel();
    }

    public void modPwd(String mobile, String wtd, String validCd, String password, String conpassword){
        mode.modPwd(mobile,wtd,validCd,password,conpassword);
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
                mView.changePwd(resultInfo);
            }
        };
        mode.subModPwd(subscriber);
    }

    public void getCode(int flag, String mobile){
        mode.getCode(flag,mobile);
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
                mView.getCode(resultInfo);
            }
        };
        mode.subCode(subscriber);
    }
}
