package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.LoginInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @Description: 登陆
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/7/15
 */
public interface LoginView extends ColpencilBaseView{

    void login(boolean isLogin);

    void loginForServer(ResultInfo<LoginInfo> resultInfo);
}
