package com.colpencil.tenement.Bean;

/**
 * @Description:  RXbus的时间实体类
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/24
 */
public class Event {

    private int flag;

    private double progress;

    private String devCode;

    public String getDevCode() {
        return devCode;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
