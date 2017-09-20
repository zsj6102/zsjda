package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @Description:  客服列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/24
 */
public class Online implements Serializable {

    public String date;
    public String last;
    public String name;
    public String messgae;
    public String headImage;
    public String userId;

    @Override
    public String toString() {
        return "Online{" +
                "date='" + date + '\'' +
                ", last='" + last + '\'' +
                ", name='" + name + '\'' +
                ", messgae='" + messgae + '\'' +
                ", headImage='" + headImage + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
