package com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP;

import android.view.View;

/**
 * @Description:子view的预留接口
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 16/6/23
 */
public interface ColpencilBaseView {


    void showLoading(String msg);

    void hideLoading();

    void showError(String msg, View.OnClickListener onClickListener);

    void showEmpty(String msg, View.OnClickListener onClickListener);

    void showEmpty(String msg, View.OnClickListener onClickListener, int imageId);

    void showNetError(View.OnClickListener onClickListener);
}
