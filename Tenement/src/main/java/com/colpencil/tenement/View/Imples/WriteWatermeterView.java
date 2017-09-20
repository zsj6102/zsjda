package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Result;
import com.colpencil.tenement.Bean.WaterInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

/**
 * @author 陈宝
 * @Description:开始抄表
 * @Email DramaScript@outlook.com
 * @date 2016/9/2
 */
public interface WriteWatermeterView extends ColpencilBaseView {

    /**
     * 提交结果
     *
     * @param result
     */
    void submitResult(EntityResult<String> result);

    /**
     * 获取上个月的抄表数目
     * @param last
     */
    void getLast(EntityResult<WaterInfo> last);

    void loadError(String msg);
}
