package com.colpencil.propertycloud.Ui.indicator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2015/10/19.
 */
public class LineScalePartyIndicator extends BaseIndicatorController {

    public static final float SCALE = 1.0f;

    float[] scaleFloats = new float[]{SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE};

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.parseColor("#fd780c"));
        float translateX = getWidth() / 15;
        float translateY = getHeight() / 2;
        for (int i = 0; i < 6; i++) {
            canvas.save();
            canvas.translate((2 + i * 2) * translateX - translateX / 2, translateY);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            RectF rectF = new RectF(-translateX / 2, -getHeight() / 2.5f, translateX / 2, getHeight() / 2.5f);
            canvas.drawRoundRect(rectF, 3, 3, paint);
            canvas.restore();
        }
    }

    @Override
    public void createAnimation() {
        long[] durations = new long[]{1260, 430, 1010, 730, 950, 600};
        long[] delays = new long[]{770, 290, 280, 270, 260, 740};
        for (int i = 0; i < 6; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.4f, 1);
            scaleAnim.setDuration(durations[i]);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            scaleAnim.start();
        }
    }

}
