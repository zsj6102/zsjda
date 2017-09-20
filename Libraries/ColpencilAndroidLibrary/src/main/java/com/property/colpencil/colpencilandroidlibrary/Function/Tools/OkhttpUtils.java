package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @Description: Okhttp3的一些工具类
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/23
 */
public class OkhttpUtils {

    public static RequestBody toRequestBody(String value){
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }

}
