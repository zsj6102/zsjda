package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description: 广告
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/12
 */
public class Ad implements Serializable {

    public int aid;
    public String target;
    public String files;
    public String advnm;
    public String begintm;
    public String endtm;

    @Override
    public String toString() {
        return "Ad{" +
                "aid=" + aid +
                ", target='" + target + '\'' +
                ", files='" + files + '\'' +
                ", advnm='" + advnm + '\'' +
                ", begintm='" + begintm + '\'' +
                ", endtm='" + endtm + '\'' +
                '}';
    }
}
