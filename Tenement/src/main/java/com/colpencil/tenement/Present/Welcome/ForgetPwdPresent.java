package com.colpencil.tenement.Present.Welcome;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Result;
import com.colpencil.tenement.Model.Imples.Welcome.IForgetPwdModel;
import com.colpencil.tenement.Model.Welcome.ForgetPwdModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.ForgetPwdView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Subscriber;

/**
 * @Description: 忘记密码
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/29
 */
public class ForgetPwdPresent extends ColpencilPresenter<ForgetPwdView> {

    private IForgetPwdModel model;

    public ForgetPwdPresent(){
        model = new ForgetPwdModel();
    }

    public void changePwd(String phone,String yan,String pwd,String newPwe,String type){
        model.change(phone,yan,pwd,newPwe,type);
        Subscriber<EntityResult<String>> subscriber = new Subscriber<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.isChange(result);
            }
        };

        model.subChange(subscriber);
    }

    public void getCode(String phone,int flag){
        model.getCode(phone,flag);
        Subscriber<Result> subscriber = new Subscriber<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(Result result) {
                mView.getMessgae(result);
            }
        };
        model.subGet(subscriber);
    }

}
