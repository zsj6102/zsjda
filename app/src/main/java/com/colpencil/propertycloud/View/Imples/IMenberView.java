package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.MenberInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @author 陈 宝
 * @Description:成员管理
 * @Email 1041121352@qq.com
 * @date 2016/11/23
 */
public interface IMenberView extends ColpencilBaseView {

    void loadResult(ResultListInfo<MenberInfo> result);

}
