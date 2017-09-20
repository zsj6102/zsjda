package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.OnlineListUser;
import com.colpencil.tenement.Bean.Village;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @Description: 在线对讲列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/18
 */
public interface OnlineTalkListView extends ColpencilBaseView {

    void getOnlineList(ListCommonResult<OnlineListUser> onlineListUsers);

    void showError(String msg);

    void showVillageError(String msg);

    void loadCommunity(ListCommonResult<Village> result);
}
