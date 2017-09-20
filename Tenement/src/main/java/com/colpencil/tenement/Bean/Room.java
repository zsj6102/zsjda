package com.colpencil.tenement.Bean;

import java.io.Serializable;

public class Room implements Serializable {

    private String houseId;
    private String houseCode;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }
}
