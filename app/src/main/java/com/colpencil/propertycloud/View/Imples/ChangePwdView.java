package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @Description: 修改密码/忘记密码
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/10
 */
public interface ChangePwdView extends ColpencilBaseView {

    void getCode(ResultInfo resultInfo);

    void changePwd(ResultInfo resultInfo);
}
