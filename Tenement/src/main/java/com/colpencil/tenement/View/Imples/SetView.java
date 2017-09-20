package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.EntityResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @Description: 设置界面
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/29
 */
public interface SetView extends ColpencilBaseView{

    void loginOut(EntityResult<String> result);

    void loadError(String msg);

}
