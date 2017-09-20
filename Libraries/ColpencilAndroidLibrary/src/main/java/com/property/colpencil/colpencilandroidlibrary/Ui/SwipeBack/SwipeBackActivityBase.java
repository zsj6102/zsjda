

package com.property.colpencil.colpencilandroidlibrary.Ui.SwipeBack;


/**
 * @author 汪 亮
 * @Description:侧滑基类
 * @Email DramaScript@outlook.com
 * @date 16/6/23
 */
public interface SwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    public abstract void scrollToFinishActivity();
}
