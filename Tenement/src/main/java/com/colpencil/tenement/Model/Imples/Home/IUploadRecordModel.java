package com.colpencil.tenement.Model.Imples.Home;

import com.colpencil.tenement.Bean.EntityResult;
import com.lzy.imagepicker.bean.ImageItem;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.io.File;
import java.util.List;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:绿化保洁
 * @Email DramaScript@outlook.com
 * @date 2016/8/24
 */
public interface IUploadRecordModel extends ColpencilModel {

    /**
     * 上传工作记录
     */
    void upload(int type, String startTime, String endTime, String nowLocation,
                String workDetail, List<File> imglist, String communityId,String longitude,String latitude);

    void subUpload(Observer<EntityResult> observer);

}
