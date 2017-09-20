package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description:  其他订单
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/8
 */
public class OtherOrder implements Serializable {

    public String payitmnm;
    public int billid;
    public double amount;

    @Override
    public String toString() {
        return "OtherOrder{" +
                "payitmnm='" + payitmnm + '\'' +
                ", billid=" + billid +
                ", amount=" + amount +
                '}';
    }
}
