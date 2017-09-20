package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.LastRecord;
import com.colpencil.tenement.Bean.Result;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @Description: 开始保养/巡检/维修
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/23
 */
public interface StartPolingView extends ColpencilBaseView{

    void post(Result result);

    void getLast(EntityResult<LastRecord> result);

    void linkDev(Result result);

    void loadError(String msg);
}
