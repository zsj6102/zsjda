package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Description:时间处理工具
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 16/6/23
 */
public class TimeUtil {

    public static String getDateBySplit(String str) {
        String[] strings = str.split(" ");
        return strings[0];
    }

    /**
     * 将以毫秒为单位的时间转换成字符串格式的时间
     * @param time	时间，以毫秒为单位
     */
    public static String getTimeStrFromMillis(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String result = sdf.format(new Date(time));
        return result;
    }

    public static String getTimeStrFromMillisHour(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String result = sdf.format(new Date(time));
        return result;
    }
}
