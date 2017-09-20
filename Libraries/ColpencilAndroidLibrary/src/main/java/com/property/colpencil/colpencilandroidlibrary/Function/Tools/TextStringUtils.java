package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 汪 亮
 * @Description:内容描述：判断文本的类型
 * @Email DramaScript@outlook.com
 * @date 2016/9/29
 */
public class TextStringUtils {

    public static boolean isEditText(EditText editText) {

        return isEmpty(editText.getText().toString().trim());
    }

    public static boolean firstStr(String str) {
        Pattern pattern = Pattern.compile("[A-Za-z]");
        Matcher mc = pattern.matcher(str);
        return mc.matches();
    }

    /**
     * 判断输入的内容为金额
     */
    public static boolean isMoney(String msg) {
        if (isEmpty(msg)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
        Matcher isNum = pattern.matcher(msg);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    /**
     * 判断字符串是否为�?
     */
    public static boolean isEmpty(String str) {

        return str == null || "".equals(str) || "null".equals(str);
    }

    /**
     * 判断字符串是否为邮箱
     */
    public static boolean isEmail(String str) {
        Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher mc = pattern.matcher(str);
        return mc.matches();
    }

    // 验证电话号码
    public static boolean isMobileNO(String phoneNumber) {
        Pattern p = Pattern.compile("^1[0-9]{10}$");
        Matcher m = p.matcher(phoneNumber);
        if (m.matches() == true) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断用户输入的内容是否是汉字
     */
    public static boolean isHanzi(String text) {
        int len = 0;
        char[] charText = text.toCharArray();
        for (int i = 0; i < charText.length; i++) {
            if (isChinese(charText[i])) {
                len += 2;
            } else {

                return false;
            }
        }
        return true;
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 验证身份证号是否符合规则
     *
     * @param text 身份证号
     * @return
     */
    public static boolean ispersonIdValidation(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }

    /**
     * 判断List是否为空
     */
    public static boolean listIsNullOrEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 判断map是否为空
     */
    public static boolean mapIsNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty() || map.size() == 0;
    }

    /**
     * 判断object是否为空
     */
    public static boolean objectIsNull(Object object) {
        return object == null;
    }

    /**
     * 将字符串的编码转换成utf�?8
     *
     * @param str
     * @return
     */
    public static String encodeString(String str) {
        if (str == null) {
            str = "";
        } else if (str.equals("") || str.length() == 0) {
            str = "";
        } else {
            try {
                str = URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return str;
    }

    public static String decodeUtf8(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (Exception e) {
            return str;
        }
    }

    public static boolean isLower(int ch) {
        return ((ch - 'a') | ('z' - ch)) >= 0;
    }

    public static boolean isUpper(int ch) {
        return ((ch - 'A') | ('Z' - ch)) >= 0;
    }

}
