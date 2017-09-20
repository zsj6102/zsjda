package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description: 缴费列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/6
 */
public class PayList implements Serializable {


    /**
     * billid : 1063
     * paystatus : 0
     * payitemsid : 6
     * payitemsnm : 车位租赁费
     * billamount : 505.0
     * lastrecord : 0.0
     * currecord : 0.0
     * payment : 2016.12.03-2017.01.02
     * create_month : 12
     * original_bill_amount : 505.0
     * payamount :
     * tel : 0592-2651961
     * ownernm :
     * paytype : 线上缴费
     * paytime :
     * billsn : SK161203103651765716640
     */

    private String billid;
    private int paystatus;
    private int payitemsid;
    private String payitemsnm;
    private double billamout;
    private double lastrecord;
    private double currecord;
    private String payment;
    private String create_month;
    private double original_bill_amount;
    private String payamount;
    private String tel;
    private String ownernm;
    private String paytype;
    private String paytime;
    private String billsn;
    private String advDescribe;
    private String payer;
    private String payernm;
    private String advUrl;
    private String remark;
    private double deposit;
    private double related_costs;
    private String return_time;

    public String getBillid() {
        return billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
    }

    public double getBillamout() {
        return billamout;
    }

    public void setBillamout(double billamout) {
        this.billamout = billamout;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getRelated_costs() {
        return related_costs;
    }

    public void setRelated_costs(double related_costs) {
        this.related_costs = related_costs;
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
    }

    public String getPayernm() {
        return payernm;
    }

    public void setPayernm(String payernm) {
        this.payernm = payernm;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getAdvUrl() {
        return advUrl;
    }

    public void setAdvUrl(String advUrl) {
        this.advUrl = advUrl;
    }

    public String getAdvDescribe() {
        return advDescribe;
    }

    public void setAdvDescribe(String advDescribe) {
        this.advDescribe = advDescribe;
    }


    public int getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(int paystatus) {
        this.paystatus = paystatus;
    }

    public int getPayitemsid() {
        return payitemsid;
    }

    public void setPayitemsid(int payitemsid) {
        this.payitemsid = payitemsid;
    }

    public String getPayitemsnm() {
        return payitemsnm;
    }

    public void setPayitemsnm(String payitemsnm) {
        this.payitemsnm = payitemsnm;
    }


    public double getLastrecord() {
        return lastrecord;
    }

    public void setLastrecord(double lastrecord) {
        this.lastrecord = lastrecord;
    }

    public double getCurrecord() {
        return currecord;
    }

    public void setCurrecord(double currecord) {
        this.currecord = currecord;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getCreate_month() {
        return create_month;
    }

    public void setCreate_month(String create_month) {
        this.create_month = create_month;
    }

    public double getOriginal_bill_amount() {
        return original_bill_amount;
    }

    public void setOriginal_bill_amount(double original_bill_amount) {
        this.original_bill_amount = original_bill_amount;
    }

    public String getPayamount() {
        return payamount;
    }

    public void setPayamount(String payamount) {
        this.payamount = payamount;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getOwnernm() {
        return ownernm;
    }

    public void setOwnernm(String ownernm) {
        this.ownernm = ownernm;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getBillsn() {
        return billsn;
    }

    public void setBillsn(String billsn) {
        this.billsn = billsn;
    }
}
