package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @Description: 打扫轨迹
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/31
 */
public class Record implements Serializable {

    public double longitude;
    public double latitude;

    @Override
    public String toString() {
        return "Record{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
