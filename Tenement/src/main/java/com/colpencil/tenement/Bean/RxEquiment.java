package com.colpencil.tenement.Bean;

/**
 * @Description:  传输设备筛选的实体类
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/14
 */
public class RxEquiment {

    private String communityId;
    private String devCode;
    private String startDate;
    private String endDate;
    private String devName;
    private int self;
    private int flag;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getDevCode() {
        return devCode;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public int getSelf() {
        return self;
    }

    public void setSelf(int self) {
        this.self = self;
    }

    @Override
    public String toString() {
        return "RxEquiment{" +
                "communityId='" + communityId + '\'' +
                ", devCode='" + devCode + '\'' +
                ", devName='" + devName + '\'' +
                ", self=" + self +
                ", flag=" + flag +
                '}';
    }
}
