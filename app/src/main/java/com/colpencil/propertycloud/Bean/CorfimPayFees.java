package com.colpencil.propertycloud.Bean;

public class CorfimPayFees {

    public int billid;
    public float billamout;
    public String payitemsnm;
    public String payment;
    public String lastrecord;
    public String currecord;
    public String tel;
    public String ownernm;
    public String payer;
    public String billsn;
    public String create_month;
    public double original_bill_amount;

    @Override
    public String toString() {
        return "CorfimPayFees{" +
                "billid=" + billid +
                ", billamout=" + billamout +
                ", payitemsnm='" + payitemsnm + '\'' +
                ", payment='" + payment + '\'' +
                ", lastrecord='" + lastrecord + '\'' +
                ", currecord='" + currecord + '\'' +
                ", tel='" + tel + '\'' +
                ", ownernm='" + ownernm + '\'' +
                ", payer='" + payer + '\'' +
                ", billsn='" + billsn + '\'' +
                ", create_month='" + create_month + '\'' +
                ", original_bill_amount='" + original_bill_amount + '\'' +
                '}';
    }
}
