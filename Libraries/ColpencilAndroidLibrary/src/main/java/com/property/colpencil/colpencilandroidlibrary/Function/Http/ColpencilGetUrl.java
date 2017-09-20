package com.property.colpencil.colpencilandroidlibrary.Function.Http;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxParams;

/**
 * @Description:组拼字符串的方法
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 16/6/23
 */
public class ColpencilGetUrl {

    /**
     * 组拼字符串的方法
     * @param baseUrl
     * @param params
     * @return
     */
    public static String getUrl(String baseUrl, AjaxParams params){
        FinalHttp fh = new FinalHttp();
        return fh.getUrlWithQueryString(baseUrl, params);
    }
}
