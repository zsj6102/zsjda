package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @author 陈 宝
 * @Description:房间
 * @Email 1041121352@qq.com
 * @date 2016/11/20
 */
public class RoomInfo implements Serializable {

    private int houseId;
    private String houseCode;
    private boolean isSelect;

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
