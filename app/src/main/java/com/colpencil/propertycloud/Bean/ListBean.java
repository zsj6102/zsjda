package com.colpencil.propertycloud.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 集合的实体
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/27
 */
public class ListBean<T> implements Serializable {

    public int code;
    public String message;
    public String paymentHistory;
    public String wUrl;
    public String jUrl;
    public List<T> data;

    @Override
    public String toString() {
        return "ListBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
