package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description: 居住情况
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
public class LiveInfoList implements Serializable {

    public int usrtpcd;
    public int hseid;
    public String usrtpnm;
    public String commnm;
    public String buldnm;
    public String unitnm;
    public String romnum;
    public String shortname ;

    @Override
    public String toString() {
        return "LiveInfoList{" +
                "usrtpcd=" + usrtpcd +
                ", hseid=" + hseid +
                ", usrtpnm='" + usrtpnm + '\'' +
                ", commnm='" + commnm + '\'' +
                ", buldnm='" + buldnm + '\'' +
                ", unitnm='" + unitnm + '\'' +
                ", romnum='" + romnum + '\'' +
                '}';
    }
}
