package com.colpencil.propertycloud.Model.Imples.CloseManager;

import com.colpencil.propertycloud.Bean.LiveInfoList;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;

import java.util.List;

import rx.Subscriber;

/**
 * @Description: 居住情况
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
public interface ILiveInfoModel {

    void getLiveInfo();

    void sub(Subscriber<ResultListInfo<LiveInfoList>> subscriber);

}
