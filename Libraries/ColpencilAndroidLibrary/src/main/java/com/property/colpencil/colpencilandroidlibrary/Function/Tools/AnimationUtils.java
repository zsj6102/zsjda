package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * @author 汪 亮
 * @Description: view的动画效果
 * @Email DramaScript@outlook.com
 * @date 2016/8/26
 */
public class AnimationUtils {

    public static AlphaAnimation mHideAnimation = null;

    public static AlphaAnimation mShowAnimation = null;

    /**
     * View渐隐动画效果
     */

    public static void setHideAnimation(View view, int duration) {
        if (null == view || duration < 0) {
            return;
        }
        if (null != mHideAnimation) {
            mHideAnimation.cancel();
        }
        mHideAnimation = new AlphaAnimation(1.0f, 0.0f);
        mHideAnimation.setDuration(duration);
        mHideAnimation.setFillAfter(true);
        view.startAnimation(mHideAnimation);
    }

    /**
     * View渐现动画效果
     */

    public static void setShowAnimation(View view, int duration) {
        if (null == view || duration < 0) {
            return;
        }
        if (null != mShowAnimation) {
            mShowAnimation.cancel();
        }
        mShowAnimation = new AlphaAnimation(0.0f, 1.0f);
        mShowAnimation.setDuration(duration);
        mShowAnimation.setFillAfter(true);
        view.startAnimation(mShowAnimation);
    }

}
