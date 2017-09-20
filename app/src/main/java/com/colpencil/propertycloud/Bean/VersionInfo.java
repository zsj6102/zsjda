package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @author 陈 宝
 * @Description:检查更新信息
 * @Email 1041121352@qq.com
 * @date 2016/12/12
 */
public class VersionInfo implements Serializable {
    private String size;
    private String date;
    private String ver;
    private Integer verCode;
    private String md5;
    private String detail;
    private String url;
    private String appname;
    private Boolean forceUpdate;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public Integer getVerCode() {
        return verCode;
    }

    public void setVerCode(Integer verCode) {
        this.verCode = verCode;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public Boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }
}
