package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @author 陈 宝
 * @Description:建筑
 * @Email 1041121352@qq.com
 * @date 2016/11/20
 */
public class Building implements Serializable {

    private int buildId;
    private String buildName;
    private boolean isSelect;

    public int getBuildId() {
        return buildId;
    }

    public void setBuildId(int buildId) {
        this.buildId = buildId;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
