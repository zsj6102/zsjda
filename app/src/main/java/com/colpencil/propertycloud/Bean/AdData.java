package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description: 首页广告
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/18
 */
public class AdData implements Serializable {

    public int aid;
    public int type;
    public String target;
    public String advnm;
    public String begintm;
    public String endtm;
    public String files;
    public String advcode;
    public String adv_describe;

    @Override
    public String toString() {
        return "AdData{" +
                "aid=" + aid +
                ", target='" + target + '\'' +
                ", advnm='" + advnm + '\'' +
                ", begintm='" + begintm + '\'' +
                ", endtm='" + endtm + '\'' +
                ", files='" + files + '\'' +
                ", advcode='" + advcode + '\'' +
                ", adv_describe='" + adv_describe + '\'' +
                '}';
    }
}
