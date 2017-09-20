package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @author 陈 宝
 * @Description:小区选择
 * @Email 1041121352@qq.com
 * @date 2016/11/17
 */
public class CellInfo implements Serializable {

    private int community_id;
    private String shortname;
    private String community_name;
    private String propertytel;
    private String servicetel;
    private String shortnm;
    private String comuid;
    private boolean isSelect;
    private String shortName;

    public int getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(int community_id) {
        this.community_id = community_id;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getPropertytel() {
        return propertytel;
    }

    public void setPropertytel(String propertytel) {
        this.propertytel = propertytel;
    }

    public String getServicetel() {
        return servicetel;
    }

    public void setServicetel(String servicetel) {
        this.servicetel = servicetel;
    }

    public String getShortnm() {
        return shortnm;
    }

    public void setShortnm(String shortnm) {
        this.shortnm = shortnm;
    }

    public String getComuid() {
        return comuid;
    }

    public void setComuid(String comuid) {
        this.comuid = comuid;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
