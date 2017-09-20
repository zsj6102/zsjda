package com.colpencil.tenement.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 签到/签退记录
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/26
 */
public class SignList {

    public String date;
    public List<SignHistory> signHistory = new ArrayList<>();

    public class SignHistory{

        public String location;
        public String signTime;
        public String signPhoto;
        public int signType;

        @Override
        public String toString() {
            return "SignHistory{" +
                    ", location='" + location + '\'' +
                    ", signPhoto='" + signPhoto + '\'' +
                    ", signType='" + signType + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SignList{" +
                "date=" + date +
                ", signHistory=" + signHistory +
                '}';
    }
}
