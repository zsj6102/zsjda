package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @Description:  小区
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/29
 */
public class Village implements Serializable {

    public String plotId;
    public String plot;
    public String shortName;

    @Override
    public String toString() {
        return "Village{" +
                "plotId='" + plotId + '\'' +
                ", plot='" + plot + '\'' +
                '}';
    }
}
