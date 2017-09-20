package com.colpencil.tenement.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 报修订单
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/20
 */
public class OwnerRepair implements Serializable {

    private String title;
    private String desc;
    private String orderCode;
    private String voiceUrl;
    private String staff;
    private String repairDesc;
    private String ownerName;
    private String ownerPhone;
    private String address;
    private String time;
    private String communityName;
    private String feedback;
    private int ownerRecordId;
    private List<String> imgUrls;
    private double voiceDuration;
    private int hasFeedback;
    private int state;
    private int devId;
    private int eqType;
    private int empId;
    private int communityId;

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public double getVoiceDuration() {
        return voiceDuration;
    }

    public void setVoiceDuration(double voiceDuration) {
        this.voiceDuration = voiceDuration;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getRepairDesc() {
        return repairDesc;
    }

    public void setRepairDesc(String repairDesc) {
        this.repairDesc = repairDesc;
    }

    public int getEqType() {
        return eqType;
    }

    public void setEqType(int eqType) {
        this.eqType = eqType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDevId() {
        return devId;
    }

    public void setDevId(int devId) {
        this.devId = devId;
    }

    public int getHasFeedback() {
        return hasFeedback;
    }

    public void setHasFeedback(int hasFeedback) {
        this.hasFeedback = hasFeedback;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getOwnerRecordId() {
        return ownerRecordId;
    }

    public void setOwnerRecordId(int ownerRecordId) {
        this.ownerRecordId = ownerRecordId;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    @Override
    public String toString() {
        return "OwnerRepair{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", voiceUrl='" + voiceUrl + '\'' +
                ", staff='" + staff + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", ownerPhone='" + ownerPhone + '\'' +
                ", address='" + address + '\'' +
                ", time='" + time + '\'' +
                ", ownerRecordId=" + ownerRecordId +
                ", imgUrls=" + imgUrls +
                ", voiceDuration=" + voiceDuration +
                '}';
    }
}
