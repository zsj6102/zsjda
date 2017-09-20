package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @author 陈宝
 * @Description:抄表数
 * @Email DramaScript@outlook.com
 * @date 2016/9/2
 */
public class ReadingNum implements Serializable {

    private int recorded;
    private int notRecorded;

    public int getRecorded() {
        return recorded;
    }

    public void setRecorded(int recorded) {
        this.recorded = recorded;
    }

    public int getNotRecorded() {
        return notRecorded;
    }

    public void setNotRecorded(int notRecorded) {
        this.notRecorded = notRecorded;
    }
}
