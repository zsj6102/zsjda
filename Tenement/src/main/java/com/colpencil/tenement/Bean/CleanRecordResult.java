package com.colpencil.tenement.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 陈宝
 * @Description:保洁记录的结果集
 * @Email DramaScript@outlook.com
 * @date 2016/8/24
 */
public class CleanRecordResult implements Serializable {

    private int code;
    private String message;
    private int count;
    private List<CleanRecord> data;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CleanRecord> getData() {
        return data;
    }

    public void setData(List<CleanRecord> data) {
        this.data = data;
    }
}
