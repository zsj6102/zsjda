package com.colpencil.propertycloud.View.Imples;

import com.colpencil.propertycloud.Bean.LiveInfoList;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @Description: 居住情况
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
public interface LiveInfoListView extends ColpencilBaseView {

    void liveInfoList(ResultListInfo<LiveInfoList> liveInfoListViews);

    void loadError(String msg);
}
