package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @Description: 水电表的信息
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/30
 */
public class WaterInfo implements Serializable{

    public String ownerName;
    public String roomNo;
    public double currRecord;
    public int currEmp;
    public String currEmpName;
    public double lastRecord;
    public int roomId;
    public int communityId;
    public int buildingId;
    public int recordId;

    @Override
    public String toString() {
        return "WaterInfo{" +
                "ownerName='" + ownerName + '\'' +
                ", roomNo='" + roomNo + '\'' +
                ", lastRecord='" + lastRecord + '\'' +
                '}';
    }
}
