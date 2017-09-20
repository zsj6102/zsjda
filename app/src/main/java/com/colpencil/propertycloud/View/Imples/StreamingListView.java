package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.LiveStreaming;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @Description: 摄像头列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
public interface StreamingListView extends ColpencilBaseView {

    void getList(List<LiveStreaming> list);

}
