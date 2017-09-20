package com.colpencil.propertycloud.Model.Imples.Home;

import com.colpencil.propertycloud.Bean.ComplaintType;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.File;
import java.util.List;

import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 物业投诉
 * @Email DramaScript@outlook.com
 * @date 2016/9/5
 */
public interface IComplaintModel {

    void complaint(int catId, String detailDesc, String audiourl, int type, List<File> files, long time);

    void sub(Subscriber<ResultInfo> subscriber);

    void getCompType(int type, String commuId);

    void subType(Subscriber<ResultListInfo<ComplaintType>> subscriber);
}
