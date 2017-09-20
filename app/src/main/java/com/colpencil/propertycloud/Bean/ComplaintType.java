package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description: 投诉类型
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/8
 */
public class ComplaintType implements Serializable {

    public int catid;
    public String tpnm;
    public int pid;

    @Override
    public String toString() {
        return "ComplaintType{" +
                "catid=" + catid +
                ", tpnm='" + tpnm + '\'' +
                ", pid=" + pid +
                '}';
    }
}
