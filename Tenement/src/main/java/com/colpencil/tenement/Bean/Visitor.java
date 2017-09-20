package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @Description: 访客管理
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/25
 */
public class Visitor implements Serializable {

    public String date;
    public String describe;
    public String name;
    public String phone;
    public String ownPhone;
    public String identity;
    public String location;

    @Override
    public String toString() {
        return "Visitor{" +
                "date='" + date + '\'' +
                ", describe='" + describe + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", ownPhone='" + ownPhone + '\'' +
                ", identity='" + identity + '\'' +
                '}';
    }
}
