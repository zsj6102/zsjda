package com.colpencil.tenement.Present.Home;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Model.Home.UploadRecordModel;
import com.colpencil.tenement.Model.Imples.Home.IUploadRecordModel;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.View.Imples.UploadRecordView;
import com.lzy.imagepicker.bean.ImageItem;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;
import java.util.List;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:绿化保洁
 * @Email DramaScript@outlook.com
 * @date 2016/8/24
 */
public class UploadRecordPresenter extends ColpencilPresenter<UploadRecordView> {

    private IUploadRecordModel model;

    public UploadRecordPresenter() {
        model = new UploadRecordModel();
    }

    /**
     * 上传工作记录
     */
    public void upload(int type, String startTime, String endTime, String nowLocation, String workDetail,
                       List<File> imglist, String communityId,String longitude,String latitude) {
        model.upload(type, startTime, endTime, nowLocation, workDetail,imglist,communityId,longitude,latitude);
        Observer<EntityResult> observer = new Observer<EntityResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
                mView.loadError(e.getMessage());
            }

            @Override
            public void onNext(EntityResult commonResult) {
                mView.uploadResult(commonResult);
            }
        };
        model.subUpload(observer);
    }

}
