package com.colpencil.tenement.Bean;

import java.io.Serializable;

public class UtilitiesRecord implements Serializable {

    private String ownerName;
    private String date;
    private double degrees;
    private String roomNo;
    private String waterId;
    private String empName;
    private double lastRecord;
    private int roomId;
    private int communityId;
    private int buildingId;
    private int type;
    private int empId;

    public double getLastRecord() {
        return lastRecord;
    }

    public void setLastRecord(double lastRecord) {
        this.lastRecord = lastRecord;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDegrees() {
        return degrees;
    }

    public void setDegrees(double degrees) {
        this.degrees = degrees;
    }

    public String getWaterId() {
        return waterId;
    }

    public void setWaterId(String waterId) {
        this.waterId = waterId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UtilitiesRecord{" +
                "ownerName='" + ownerName + '\'' +
                ", date='" + date + '\'' +
                ", degrees=" + degrees +
                ", roomNo='" + roomNo + '\'' +
                ", waterId='" + waterId + '\'' +
                ", roomId=" + roomId +
                ", communityId=" + communityId +
                ", buildingId=" + buildingId +
                ", type=" + type +
                '}';
    }
}
