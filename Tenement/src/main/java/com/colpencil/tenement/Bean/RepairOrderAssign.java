package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/10.
 */

public class RepairOrderAssign implements Serializable {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
