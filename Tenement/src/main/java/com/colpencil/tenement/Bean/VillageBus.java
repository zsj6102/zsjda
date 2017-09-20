package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @Description: 事件小区
 * @author 汪 亮  
 * @Email  DramaScript@outlook.com
 * @date 2016/10/17
 */ 
public class VillageBus implements Serializable {

    private String plotId;
    private String plot;

    public int getTpye() {
        return tpye;
    }

    public void setTpye(int tpye) {
        this.tpye = tpye;
    }

    private int flag;
    private int tpye;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getPlotId() {
        return plotId;
    }

    public void setPlotId(String plotId) {
        this.plotId = plotId;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}
