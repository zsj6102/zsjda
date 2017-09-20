package com.colpencil.tenement.Tools;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;


public class SpanUtils {

    /**
     * 格式化TextView的文本
     */
    public static SpannableStringBuilder formatText(String text, int start, int end) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return builder;
    }
}
