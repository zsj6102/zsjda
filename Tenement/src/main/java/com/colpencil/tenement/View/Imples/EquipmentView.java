package com.colpencil.tenement.View.Imples;

import com.colpencil.tenement.Bean.Equipment;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 汪 亮
 * @Description: 设备管理
 * @Email DramaScript@outlook.com
 * @date 2016/8/23
 */
public interface EquipmentView extends ColpencilBaseView {

    /**
     * 刷新
     *
     * @param result
     */
    void refresh(ListCommonResult<Equipment> result);

    /**
     * 加载更多
     *
     * @param result
     */
    void loadMore(ListCommonResult<Equipment> result);

    /**
     * 加载错误
     *
     * @param msg
     */
    void loadError(String msg);

    /**
     * 设备数
     * @param count
     */
    void count(int count);

    /**
     * 状态码
     * @param code
     */
    void code(int code);
}
