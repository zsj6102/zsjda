package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @Description: 忘记密码
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/29
 */
public interface ForgetPwdView extends ColpencilBaseView {

    void isChange(EntityResult<String> result);

    void getMessgae(Result result);

    void loadError(String msg);
}
