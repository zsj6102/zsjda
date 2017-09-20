package com.colpencil.tenement.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 在线对讲
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/18
 */
public class OnlineListUser implements Serializable {

    public String departmentName;
    public List<OnlineUser> members;

    @Override
    public String toString() {
        return "OnlineListUser{" +
                "departmentName='" + departmentName + '\'' +
                ", members=" + members +
                '}';
    }
}
