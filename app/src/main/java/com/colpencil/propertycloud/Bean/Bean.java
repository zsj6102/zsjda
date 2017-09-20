package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description: 对象的实体
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/27
 */
public class Bean<T> implements Serializable {

    public int code;
    public String message;
    public T data;

    @Override
    public String toString() {
        return "Bean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
