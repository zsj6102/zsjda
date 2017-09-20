package com.colpencil.propertycloud.Model.Imples;

import com.colpencil.propertycloud.Bean.MenberInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Subscriber;

/**
 * @author 陈 宝
 * @Description:成员管理
 * @Email 1041121352@qq.com
 * @date 2016/11/23
 */
public interface IMenberModel extends ColpencilModel {

    void loadData(int hseid);

    void sub(Subscriber<ResultListInfo<MenberInfo>> subscriber);

}
