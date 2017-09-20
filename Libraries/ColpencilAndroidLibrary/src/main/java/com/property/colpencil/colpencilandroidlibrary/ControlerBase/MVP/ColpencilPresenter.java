package com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP;

/**
 * @Description:所有present的基类
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 16/6/23
 */
public abstract class ColpencilPresenter<T extends ColpencilBaseView> {
    public T mView;

    //做绑定view的操作
    public void attach(T mView) {
        this.mView = mView;
    }

    //做分离view的操作
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }
}
