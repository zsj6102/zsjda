package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description: 小区选择
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/9
 */
public class Vilage implements Serializable {

    public String commnm;
    public String shortnm;
    public String hseid;
    public String romnum;
    public String comuid;
    public String buldnm;
    public String propertyid;
    public String propertytel;
    public String servicetel  ;

    @Override
    public String toString() {
        return "Vilage{" +
                "commnm='" + commnm + '\'' +
                ", shortnm='" + shortnm + '\'' +
                ", hseid='" + hseid + '\'' +
                ", romnum='" + romnum + '\'' +
                ", comuid='" + comuid + '\'' +
                ", buldnm='" + buldnm + '\'' +
                '}';
    }
}
