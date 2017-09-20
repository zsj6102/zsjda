package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.LoginInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @Description: 注册
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/7/17
 */
public interface RegistView extends ColpencilBaseView{

    void regist(ResultInfo resultInfo);

    void loginForServer(ResultInfo<LoginInfo> resultInfo);

    void getCode(ResultInfo resultInfo);
}
