package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @author 陈 宝
 * @Description:省份
 * @Email 1041121352@qq.com
 * @date 2016/11/21
 */
public class Province implements Serializable {

    private int region_id;
    private int p_region_id;
    private String local_name;
    private boolean isSelect;

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public int getP_region_id() {
        return p_region_id;
    }

    public void setP_region_id(int p_region_id) {
        this.p_region_id = p_region_id;
    }

    public String getLocal_name() {
        return local_name;
    }

    public void setLocal_name(String local_name) {
        this.local_name = local_name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
