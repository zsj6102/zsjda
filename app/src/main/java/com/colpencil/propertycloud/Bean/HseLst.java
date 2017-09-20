package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description:
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/19
 */
public class HseLst implements Serializable {

    public int hseid;
    public String comnm;
    public String budnm;
    public String unitnm;
    public String hsecd;
    public String hsearea;
    public String communityid;

    @Override
    public String toString() {
        return "HseLst{" +
                "hseid=" + hseid +
                ", comnm='" + comnm + '\'' +
                ", budnm='" + budnm + '\'' +
                ", unitnm='" + unitnm + '\'' +
                ", hsecd='" + hsecd + '\'' +
                ", hsearea='" + hsearea + '\'' +
                '}';
    }
}
