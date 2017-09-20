package com.colpencil.tenement.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 最外层的实体类
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/13
 */
public class ResultListInfo<T> implements Serializable {

    public int code;

    public String message;

    public List<T> data;

    public String ordersn;

    public double totalAmount;

    @Override
    public String toString() {
        return "ResultInfo{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
