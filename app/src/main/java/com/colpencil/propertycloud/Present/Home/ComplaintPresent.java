package com.colpencil.propertycloud.Present.Home;

import com.colpencil.propertycloud.Bean.ComplaintType;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Model.Home.ComplaintModel;
import com.colpencil.propertycloud.Model.Imples.Home.IComplaintModel;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.View.Imples.IComplaintView;
import com.lzy.imagepicker.bean.ImageItem;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;
import java.util.List;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 物业投诉
 * @Email DramaScript@outlook.com
 * @date 2016/9/5
 */
public class ComplaintPresent extends ColpencilPresenter<IComplaintView> {

    private IComplaintModel model;

    public ComplaintPresent() {
        model = new ComplaintModel();
    }

    public void comit(int catId, String detailDesc, String audioUrl, int type, List<File> files, long time) {
        model.complaint(catId, detailDesc, audioUrl, type, files, time);
        Subscriber<ResultInfo> subscriber = new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误：" + e.getMessage());
            }

            @Override
            public void onNext(ResultInfo resultInfo) {
                mView.comit(resultInfo);
            }
        };
        model.sub(subscriber);
    }

    public void getCompType(int type, String commuId) {
        model.getCompType(type, commuId);
        Subscriber<ResultListInfo<ComplaintType>> subscriber = new Subscriber<ResultListInfo<ComplaintType>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResultListInfo<ComplaintType> resultInfo) {
                mView.getCompType(resultInfo);
            }
        };
        model.subType(subscriber);
    }
}
