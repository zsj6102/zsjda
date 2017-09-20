package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @Description: 签到/签退
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/23
 */
public class Sign implements Serializable {

    public String date;
    public String signImage;

    @Override
    public String toString() {
        return "Sign{" +
                "date='" + date + '\'' +
                ", signImage='" + signImage + '\'' +
                '}';
    }
}
