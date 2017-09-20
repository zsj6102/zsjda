package com.colpencil.propertycloud.Tools;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.colpencil.propertycloud.R;

/**
 * <p/>
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p/>
 * 作者：ZengFeng
 * <p/>
 * 描述：适配器中Item动画
 */
public class ItemViewAnimation {

    /**
     * @param view    需设置动画的布局
     */

    public static void setViewAnimation(final View view) {

        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_bottom_in);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setAlpha(1);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(animation);
    }
}
