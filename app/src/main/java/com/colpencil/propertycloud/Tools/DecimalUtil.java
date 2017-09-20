package com.colpencil.propertycloud.Tools;

import java.text.DecimalFormat;

public class DecimalUtil {

    /**
     * 获取某项的总金额
     *
     * @param fees
     * @return
     */
    public static String caculateFees(double fees) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(fees);
    }

    /**
     * 替换部分字符串
     *
     * @param string
     * @param start
     * @param end
     * @return
     */
    public static String strReplacestar(String string, int start, int end) {
        String star = "";
        for (int i = 0; i < end - start; i++) {
            star += "*";
        }
        StringBuilder builder = new StringBuilder(string);
        return builder.replace(start, end, star).toString();
    }

}
