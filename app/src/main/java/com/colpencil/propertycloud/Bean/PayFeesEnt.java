package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

public class PayFeesEnt implements Serializable {

    public int billid;
    public int payitemsid;
    public int house_id;
    public double billamout;
    public String payitemsnm;
    public String house_info;
    public String payment;

    @Override
    public String toString() {
        return "PayFeesEnt{" +
                "billid=" + billid +
                ", payitemsid=" + payitemsid +
                ", house_id=" + house_id +
                ", billamout=" + billamout +
                ", payitemsnm='" + payitemsnm + '\'' +
                ", house_info='" + house_info + '\'' +
                '}';
    }
}
