package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.WorkBeach;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @Description:  获取工作台的列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/17
 */
public interface WorkbeachView extends ColpencilBaseView{

    void loadWorkBeach(List<WorkBeach> workBeachList);

}
