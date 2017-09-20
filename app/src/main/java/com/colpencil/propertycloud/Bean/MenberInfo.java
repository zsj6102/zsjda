package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description: 家庭成员信息
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/15
 */
public class MenberInfo implements Serializable {

    public int usrid;
    public int hseid;
    public String usrnm;
    public int canlogoff;
    public int isdefbenfy;
    public boolean type;
    public int user_type;

    @Override
    public String toString() {
        return "MenberInfo{" +
                "usrid=" + usrid +
                ", hseid=" + hseid +
                ", usrnm='" + usrnm + '\'' +
                ", canlogoff=" + canlogoff +
                ", isdefbenfy=" + isdefbenfy +
                '}';
    }
}
