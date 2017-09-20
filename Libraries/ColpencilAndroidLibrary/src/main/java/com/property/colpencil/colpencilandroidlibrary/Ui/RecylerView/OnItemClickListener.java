package com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView;

import android.view.View;
/**
 * @Description:点击长按的接口
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/19
 */
public interface OnItemClickListener {
    void onItemClick(View view, int position);
    void onItemLongClick(View view, int position);
}
