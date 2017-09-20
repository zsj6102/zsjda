package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description: 装修申请表信息
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/21
 */
public class ApplaInfo implements Serializable {

    public String decortCoNm;
    public String decortHead;
    public String peopleNum;
    public String qualifiNo;
    public String decortCoTel;
    public String decortbeginTm;
    public String decortEndTm;
    public String decortDesc;

    @Override
    public String toString() {
        return "ApplaInfo{" +
                "decortCoNm='" + decortCoNm + '\'' +
                ", decortHead='" + decortHead + '\'' +
                ", peopleNum='" + peopleNum + '\'' +
                ", qualifiNo='" + qualifiNo + '\'' +
                ", decortCoTel='" + decortCoTel + '\'' +
                ", decortbeginTm='" + decortbeginTm + '\'' +
                ", decortEndTm='" + decortEndTm + '\'' +
                ", decortDesc='" + decortDesc + '\'' +
                '}';
    }
}
