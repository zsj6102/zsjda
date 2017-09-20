package com.colpencil.tenement.Bean;

/**
 * @Description: 在线对讲人员
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/18
 */
public class OnlineUser {

    public String membername;
    public String position;
    public String callnumber;
    public String username;
    public int onlineStatus;
    public int emp_id;


    @Override
    public String toString() {
        return "OnlineUser{" +
                "membername='" + membername + '\'' +
                ", position='" + position + '\'' +
                ", callnumber='" + callnumber + '\'' +
                '}';
    }
}
