package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.ComplaintType;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @Description:  物业投诉
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/5
 */
public interface IComplaintView extends ColpencilBaseView {

    void comit(ResultInfo result);

    void getCompType(ResultListInfo<ComplaintType> resultInfo);
}
