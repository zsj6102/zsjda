package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description: 反馈
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/12
 */
public class Feed implements Serializable {

    public long  feedscore;
    public String detaildesc;
    public String completm;
    public String empnm;

    @Override
    public String toString() {
        return "Feed{" +
                "feedscore=" + feedscore +
                ", detaildesc='" + detaildesc + '\'' +
                ", completm='" + completm + '\'' +
                ", empnm='" + empnm + '\'' +
                '}';
    }
}
