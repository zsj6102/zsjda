package com.colpencil.propertycloud.Bean;

import java.util.List;

/**
 * @Description:
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/19
 */
public class FitmentInfo {

    public String name;
    public String tel;
    public String decortCoNm;
    public String decortHead;
    public String qualifiNo;
    public String decortCoTel;
    public String decortbeginTm;
    public String decortEndTm;
    public String decortDesc;
    public String peopleNum;
    public int isrentcam;
    public int camnum;
    public List<HseLst> hselst;

    @Override
    public String toString() {
        return "FitmentInfo{" +
                "name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", decortCoNm='" + decortCoNm + '\'' +
                ", decortHead='" + decortHead + '\'' +
                ", qualifiNo='" + qualifiNo + '\'' +
                ", decortCoTel='" + decortCoTel + '\'' +
                ", decortbeginTm='" + decortbeginTm + '\'' +
                ", decortEndTm='" + decortEndTm + '\'' +
                ", decortDesc='" + decortDesc + '\'' +
                ", peopleNum=" + peopleNum +
                ", isrentcam=" + isrentcam +
                '}';
    }
}
