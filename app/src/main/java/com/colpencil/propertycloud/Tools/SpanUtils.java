package com.colpencil.propertycloud.Tools;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

/**
 * @author 陈 宝
 * @Description:TextView的工具类
 * @Email 1041121352@qq.com
 * @date 2016/12/26
 */
public class SpanUtils {

    /**
     * 改变TextView的部分文字的颜色
     *
     * @param context
     * @param text
     * @param start
     * @param end
     * @return
     */
    public static SpannableStringBuilder spanToTextView(Context context, String text, int start, int end, int colorId) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(colorId)),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return builder;
    }

}
