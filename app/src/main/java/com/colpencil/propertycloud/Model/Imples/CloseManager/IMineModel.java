package com.colpencil.propertycloud.Model.Imples.CloseManager;

import com.colpencil.propertycloud.Bean.Mine;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.File;

import rx.Subscriber;

/**
 * @Description: 我的个人资料
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/13
 */
public interface IMineModel  {

    void loginOut(String memberId,String comuId);

    void subLoginOut(Subscriber<ResultInfo> subscriber);

    void getMineInfo();

    void subMineInfo(Subscriber<ResultInfo<Mine>> subscriber);

    void changeHead( File file);

    void subChangeHead(Subscriber<ResultInfo> subscriber);
}
