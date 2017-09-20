package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.SignInfo;
import com.colpencil.tenement.Bean.SignList;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @Description: 签到/签退记录
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/26
 */
public interface SignListView  extends ColpencilBaseView{

    void refresh(ListCommonResult<SignList> list);

    void loadMore(ListCommonResult<SignList> list);

    void loadError(String msg);

    void getSignState(EntityResult<SignInfo> result);
}
