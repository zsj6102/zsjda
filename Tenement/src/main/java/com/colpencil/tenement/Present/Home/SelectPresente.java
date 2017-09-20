package com.colpencil.tenement.Present.Home;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Model.Home.SelectModel;
import com.colpencil.tenement.Model.Imples.Home.ISelectModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.SelectView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:小区选择
 * @Email DramaScript@outlook.com
 * @date 2016/8/31
 */
public class SelectPresente extends ColpencilPresenter<SelectView> {

    private ISelectModel model;

    public SelectPresente() {
        model = new SelectModel();
    }

    public void loadVillages() {
        ColpencilLogger.e("-----------volige");
        model.loadVilliage();
        Observer<ListCommonResult<Village>> observer = new Observer<ListCommonResult<Village>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(ListCommonResult<Village> result) {
                ColpencilLogger.e("小区选择信息："+result.toString());
                int code = result.getCode();
                mView.loadCode(code);
                mView.loadGreet(result.getGreet());
                if (code == 0) {
                    mView.loadMore(result.getData());
                }else {
                    mView.loadError(result.getMessage());
                }
            }
        };
        model.sub(observer);
    }

}
