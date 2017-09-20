package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.Online;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @Description: 在线客服列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/24
 */
public interface OnlineListView extends ColpencilBaseView{

    void onlineList(List<Online> list);

}
