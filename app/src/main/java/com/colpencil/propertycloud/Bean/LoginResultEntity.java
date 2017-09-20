package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * Created by 陈宝 on 2016/6/22.
 */
public class LoginResultEntity implements Serializable{
    private String msg;
    private String code;
    private String result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
