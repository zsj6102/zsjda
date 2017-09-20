package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description: 补丁
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/9
 */
public class AnFixBean implements Serializable {

    public String md5;
    public String path;

    @Override
    public String toString() {
        return "AnFixBean{" +
                "md5='" + md5 + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
