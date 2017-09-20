package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @Description: 物业公司的列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/29
 */
public class TenementComp implements Serializable {

    public int propertyId;
    public String propertyName;

    @Override
    public String toString() {
        return "TenementComp{" +
                "propertyId=" + propertyId +
                ", propertyName='" + propertyName + '\'' +
                '}';
    }
}
