package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @Description: 扫描二维码 维修的信息
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/20
 */
public class Poling implements Serializable {

    public int devId;
    public int eqType ;

    @Override
    public String toString() {
        return "Poling{" +
                "devId=" + devId +
                ", eqType=" + eqType +
                '}';
    }
}
