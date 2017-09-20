package com.colpencil.tenement.Present.Welcome;

import com.colpencil.tenement.Model.Imples.Welcome.IRegistModel;
import com.colpencil.tenement.Model.Welcome.RegistModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.RegistView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Subscriber;

/**
 * @Description: 注册
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/7/17
 */
public class RegistPresent extends ColpencilPresenter<RegistView> {

    private IRegistModel iRegistModel;
    private Subscriber<Integer> subscriber;

    public RegistPresent(){
        iRegistModel = new RegistModel();
    }

    public void regist(String acount, String passWord){
        ColpencilLogger.e("------------------------3");
        iRegistModel.regist(acount,passWord);
        subscriber = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                ColpencilLogger.e("------------------------6");
                mView.regist(integer);
            }
        };
        iRegistModel.sub(subscriber);
        ColpencilLogger.e("------------------------7");
    }
}
