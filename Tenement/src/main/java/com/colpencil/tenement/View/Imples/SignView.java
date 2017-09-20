package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Sign;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @Description: 签到/签退
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/23
 */
public interface SignView extends ColpencilBaseView{

    void signIn(EntityResult<Sign> result);

    void signOut(EntityResult<Sign> result);

    void loadError(String msg);

}
