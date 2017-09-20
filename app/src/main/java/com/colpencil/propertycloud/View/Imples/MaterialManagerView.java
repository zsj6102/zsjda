package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.Material;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 汪 亮
 * @Description: 材料管理
 * @Email DramaScript@outlook.com
 * @date 2016/9/7
 */
public interface MaterialManagerView extends ColpencilBaseView {

    void materialList(List<Material> list);

    void submit(ResultInfo resultInfo);
}
