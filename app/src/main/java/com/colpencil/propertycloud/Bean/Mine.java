package com.colpencil.propertycloud.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 我的个人资料
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/11
 */
public class Mine implements Serializable {

    public String face;
    public String name;
    public String nickname;
    public int age;
    public String birthday;
    public int sex;
    public boolean isApproved;
    public String hobby;
    public String workunit;
    public String nation;
    public String urgent_tel;
    public String address;
    public String email;
    public String live_address;
    public String mobile;
    public String my_house;
    public String keyManagerUrl;
    public String point;
    public List<String> urls;
    public double user_redaccount;
    public double userAccount;

    @Override
    public String toString() {
        return "Mine{" +
                "face='" + face + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                ", birthday='" + birthday + '\'' +
                ", sex=" + sex +
                ", isApproved=" + isApproved +
                ", hobby='" + hobby + '\'' +
                ", workunit='" + workunit + '\'' +
                ", nation='" + nation + '\'' +
                ", urgent_tel='" + urgent_tel + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", live_address='" + live_address + '\'' +
                '}';
    }
}
