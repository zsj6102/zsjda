package com.colpencil.propertycloud.Present.CloseManager;

import com.colpencil.propertycloud.Bean.Mine;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Model.CloseManager.MineModel;
import com.colpencil.propertycloud.Model.Imples.CloseManager.IMineModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.View.Imples.MineView;
import com.lzy.imagepicker.bean.ImageItem;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 我的资料
 * @Email DramaScript@outlook.com
 * @date 2016/9/13
 */
public class MinePresent extends ColpencilPresenter<MineView> {

    private IMineModel model;

    public MinePresent() {
        model = new MineModel();
    }

    public void loginOut(String memberId, String comuId) {
        model.loginOut(memberId, comuId);
        Subscriber<ResultInfo> subscriber = new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误信息" + e.getMessage());
            }

            @Override
            public void onNext(ResultInfo resultInfo) {
                mView.loginOut(resultInfo);
            }
        };
        model.subLoginOut(subscriber);
    }

    public void getMineInfo() {
        model.getMineInfo();
        Subscriber<ResultInfo<Mine>> subscriber = new Subscriber<ResultInfo<Mine>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误信息" + e.getMessage());
            }

            @Override
            public void onNext(ResultInfo<Mine> mineResultInfo) {
                mView.getMineInfo(mineResultInfo);
            }
        };
        model.subMineInfo(subscriber);
    }

    public void changeHead( File file) {
        model.changeHead(file);
        Subscriber<ResultInfo> subscriber = new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误信息" + e.getMessage());
            }

            @Override
            public void onNext(ResultInfo resultInfo) {
                mView.changeHead(resultInfo);
            }
        };

        model.subChangeHead(subscriber);
    }
}