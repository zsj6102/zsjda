package com.colpencil.tenement.Bean.TodayTask;

import java.io.Serializable;
import java.util.List;

/**
 * @author 陈宝
 * @Description:今日任务结果集
 * @Email DramaScript@outlook.com
 * @date 2016/8/23
 */
public class TodayTaskItemResult implements Serializable {

    private int code;
    private String message;
    private List<TodayTaskItem> data;

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

    public List<TodayTaskItem> getData() {
        return data;
    }

    public void setData(List<TodayTaskItem> data) {
        this.data = data;
    }
}
