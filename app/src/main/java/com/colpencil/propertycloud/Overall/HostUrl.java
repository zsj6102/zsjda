package com.colpencil.propertycloud.Overall;

/**
 * @author 汪 亮
 * @Description: 主机地址
 * @Email DramaScript@outlook.com
 * @date 2016/9/13
 */
public class HostUrl {

    public static final boolean isTest = true;
    //TODO
//    public static final String BASE_URL = "http://112.74.197.63:80/wuye/api/property/";
//    public static final String BASE_PAY_URL = "http://112.74.197.63:80/wuye/property/api/";
//    public static final String BASE_URL_PIC = "http://112.74.197.63:80";
    //正式环境
    /** */
    public static String BASE_HOST = "http://www.daiqijia.com";
    public static String BASE_HOST_PATH = BASE_HOST + "/wymgr";
    /** */
    public static String BASE_URL = BASE_HOST_PATH + "/api/property/";
    public static String BASE_PAY_URL = BASE_HOST_PATH + "/property/api/";

    /**
     * 正式环境视频
     */
//    public static String BASE_VIDEO_URL = "http://wholeally.net:18081/";
    /**
     *视频测试
     */
    public static String BASE_VIDEO_URL = "http://117.28.255.16:18081/";
    /**
     * 静态资源地址
     */
    @Deprecated
    public static String BASE_URL_PIC = BASE_HOST + "/statics/";

    /** */
//    public static String BASE_HOST = "http://112.74.197.63:80";
//    public static String BASE_HOST_PATH = BASE_HOST + "/wymgr";
//    /** */
//    public static String BASE_URL = BASE_HOST_PATH + "/api/property/";
//    public static String BASE_PAY_URL = BASE_HOST_PATH + "/property/api/";
//    /**
//     * 静态资源地址
//     */
//    @Deprecated
//    public static String BASE_URL_PIC = BASE_HOST + "/statics/";

    static {
        if (isTest) {
            BASE_HOST = "http://112.74.133.239";
//            BASE_HOST = "http://192.168.0.93:8088";
            BASE_HOST_PATH = BASE_HOST + "/wymgr";

            BASE_URL = BASE_HOST_PATH + "/api/property/";
            BASE_PAY_URL = BASE_HOST_PATH + "/property/api/";
            BASE_URL_PIC = BASE_HOST + "/statics/";
        }
    }
}
