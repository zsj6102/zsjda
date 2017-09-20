package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @author 汪 亮
 * @Description: 投诉建议
 * @Email DramaScript@outlook.com
 * @date 2016/8/26
 */
public class Advice implements Serializable {

    public int orderId;
    public String date;
    public String describe;
    public String classify;
    public String appoint;
    public String phone;
    public String name;
    public String address;
    public String state;
    public String serviceDesc;
    public String sugCode;
    public String feedback;;
    public int hasFeedback;
    public int empId;
    public int communityId;

    @Override
    public String toString() {
        return "Advice{" +
                "orderId='" + orderId + '\'' +
                ", date='" + date + '\'' +
                ", describe='" + describe + '\'' +
                ", classify='" + classify + '\'' +
                ", appoint='" + appoint + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
