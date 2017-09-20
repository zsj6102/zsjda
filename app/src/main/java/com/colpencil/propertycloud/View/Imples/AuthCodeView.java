package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.AuthCode;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @Description: 获取验证码
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/9
 */
public interface AuthCodeView extends ColpencilBaseView{

    void getCode(AuthCode authCode);

}
