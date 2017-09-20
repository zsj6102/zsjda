package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @Description: 是否签到的信息
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/23
 */
public class SignInfo implements Serializable {

    public String status;
    public String location;

    @Override
    public String toString() {
        return "SignInfo{" +
                "status='" + status + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
