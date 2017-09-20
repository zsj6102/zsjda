package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.PaySure;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @Description: 确认缴费
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/6
 */
public interface PaySureView extends ColpencilBaseView{

    void paySure(List<PaySure> paySureList);

}
