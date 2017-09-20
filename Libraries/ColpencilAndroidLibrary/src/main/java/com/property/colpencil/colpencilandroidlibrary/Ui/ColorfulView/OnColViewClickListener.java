package com.property.colpencil.colpencilandroidlibrary.Ui.ColorfulView;

import android.view.View;

/**
 * @Description:点击界面图标的点击事件
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 16/6/23
 */
public interface OnColViewClickListener {
    /**
     * @param type {@link IColView#ERROR}, {@link IColView#DATA_NULL}, {@link IColView#LOADING}
     */
    public void onBtTempClick(View view, int type);
}