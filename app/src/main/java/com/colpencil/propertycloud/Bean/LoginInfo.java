package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @author 汪 亮
 * @Description: 登陆后返回的信息
 * @Email DramaScript@outlook.com
 * @date 2016/9/13
 */
public class LoginInfo implements Serializable {

    public String memberId;
    public String shortNm;
    public String comuId;
    public String usrNm;
    public boolean isProprietor;
    public String mobile;
    public String addr;
    public int userType;
    public String propertytel;
    public String servicetel;
    public boolean hasShen;

    @Override
    public String toString() {
        return "LoginInfo{" +
                "memberId='" + memberId + '\'' +
                ", shortNm='" + shortNm + '\'' +
                ", comuId='" + comuId + '\'' +
                ", usrNm='" + usrNm + '\'' +
                ", isProprietor=" + isProprietor +
                ", mobile='" + mobile + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }
}
