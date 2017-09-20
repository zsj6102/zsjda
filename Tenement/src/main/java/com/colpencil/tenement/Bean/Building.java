package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @author 陈宝
 * @Description:楼宇bean
 * @Email DramaScript@outlook.com
 * @date 2016/9/2
 */
public class Building implements Serializable {

    private String buildName;
    private String buildId;

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }
}
