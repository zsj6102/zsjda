package com.colpencil.propertycloud.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 汪 亮
 * @Description: 最外层的实体类
 * @Email DramaScript@outlook.com
 * @date 2016/9/13
 */
public class ResultInfo<T> implements Serializable {

    public int code;

    public String message;

    public T data;

    public int errorNum;

    public int data_sources;

    public String prompt_message;

    public String collectUrl;

    public String pointUrl;

    public String couponUrl;

    public String awardUrl;

    @Override
    public String toString() {
        return "ResultInfo{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static final Integer CODE_SUCCESS = 0;
    public static final Integer CODE_FAIL = 1;
    public static final Integer CODE_EMPTY = 2;
    public static final Integer CODE_NOT_LOGGED_IN = 3;
    public static final Integer CODE_VALIDATION_FAIL = 4;
    public static final Integer CODE_NOT_ALLOWED = 5;
    public static final Integer CODE_UNAUTHORIZED = 6;
    public static final String MSG_UNKNOWN = "未知错误";
    public static final String MSG_INTERNAL = "内部错误";
    public static final String MSG_UNSUPPORTED = "接口未支持";
    public static final String MSG_SUCCESS = "请求成功";
    public static final String MSG_FAIL = "请求不成功";
    public static final String MSG_POST_SUCCESS = "提交成功";
    public static final String MSG_POST_FAIL = "提交失败";
    public static final String MSG_EMPTY = "当前搜索条件下没有找到匹配的项目，建议您更换搜索条件后重新搜索";
    public static final String MSG_NOT_LOGGED_IN = "请登陆";
    public static final String MSG_NOT_REGISTED = "这个手机号没有注册过";
    public static final String MSG_IS_REGISTED = "这个手机号已经注册过";
    public static final String MSG_VALIDATION_FAIL = "验证码不一致，建议您确认短信内容后重新提交";
    public static final String MSG_NOT_ALLOWED = "您没有该条记录的修改权限，建议您联系原记录者进行修改";
    public static final String MSG_UNAUTHORIZED = "您的账号没有当前权限，如有疑问建议您联系物业公司";
}
