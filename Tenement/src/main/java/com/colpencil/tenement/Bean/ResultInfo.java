package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @Description: 最外层的实体类
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/13
 */
public class ResultInfo<T> implements Serializable {

    public int code;

    public String message;

    public T data;

    @Override
    public String toString() {
        return "ResultInfo{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
