package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @Description: 工作台
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/17
 */
public class WorkBeach implements Serializable{

    public int imageId;
    public String name;

    @Override
    public String toString() {
        return "WorkBeach{" +
                "imageId=" + imageId +
                ", name='" + name + '\'' +
                '}';
    }
}
