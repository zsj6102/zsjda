package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @Description: 二维码扫描后的结果
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/18
 */
public class CodeResult implements Serializable {

    public String devId;
    public int type;

    @Override
    public String toString() {
        return "CodeResult{" +
                ", devId=" + devId +
                ", type='" + type + '\'' +
                '}';
    }
}
