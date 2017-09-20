package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @Description: 单元
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/13
 */
public class Unit implements Serializable{

    public String unitName;
    public String unitId;

    @Override
    public String toString() {
        return "Unit{" +
                "unitName='" + unitName + '\'' +
                ", unitId='" + unitId + '\'' +
                '}';
    }
}
