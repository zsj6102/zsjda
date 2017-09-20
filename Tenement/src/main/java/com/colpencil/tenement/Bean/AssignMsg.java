package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/18.
 */

public class AssignMsg implements Serializable {

    private boolean isSuccess;
    private String message;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
