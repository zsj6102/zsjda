package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description: 物业缴费
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/6
 */
public class PayFees implements Serializable {

    public String billid;
    public String payitemsid;
    public String payitemsnm;
    public String billsn;
    public double billamout;

    @Override
    public String toString() {
        return "PayFees{" +
                "billid=" + billid +
                ", payitemsid=" + payitemsid +
                ", payitemsnm='" + payitemsnm + '\'' +
                ", billsn='" + billsn + '\'' +
                ", billamout=" + billamout +
                '}';
    }
}
