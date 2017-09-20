package com.property.colpencil.colpencilandroidlibrary.Function.Http.inf;

/**
 * @Description:数据响应接口
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 16/6/23
 */
public interface IResponse {
    /**
     * 响应的数据回调
     */
    public void onResponse(String data);

    /**
     * 错误返回回掉
     */
    public void onError(Object error);
}