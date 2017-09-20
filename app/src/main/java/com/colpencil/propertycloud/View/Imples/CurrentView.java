package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.Current;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @Description: 装修 当前进度
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
public interface CurrentView extends ColpencilBaseView{

    void getInfoList(List<Current> list);

}
