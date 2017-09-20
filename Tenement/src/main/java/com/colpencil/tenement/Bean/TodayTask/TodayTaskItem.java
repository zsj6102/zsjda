package com.colpencil.tenement.Bean.TodayTask;

import java.io.Serializable;

public class TodayTaskItem implements Serializable{

    private String date;
    private String task;
    private String pubName;
    private String finishDesc;
    private String pubTime;
    private int type;
    private int taskId;

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public String getFinishDesc() {
        return finishDesc;
    }

    public void setFinishDesc(String finishDesc) {
        this.finishDesc = finishDesc;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
