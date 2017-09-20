package com.colpencil.propertycloud.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 汪 亮
 * @Description: 最外层的实体类
 * @Email DramaScript@outlook.com
 * @date 2016/9/13
 */
public class ResultListInfo<T> implements Serializable {

    public int code;

    public String message;

    public List<T> data;

    public String ordersn;
    public String url;
    public String house_name;
    public String search_url;
    public String shopping_cart_url;
    public String use_rule;

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
