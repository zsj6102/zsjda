package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.Mine;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @Description: 我的个人信息
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/13
 */
public interface MineView extends ColpencilBaseView{

    void loginOut(ResultInfo resultInfo);

    void getMineInfo(ResultInfo<Mine> mineResultInfo);

    void changeHead(ResultInfo resultInfo);
}
