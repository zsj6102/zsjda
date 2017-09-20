package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description: 生成装修申请
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/15
 */
public class Approve implements Serializable {

    public int approveid;

    @Override
    public String toString() {
        return "Approve{" +
                "approveid=" + approveid +
                '}';
    }
}
