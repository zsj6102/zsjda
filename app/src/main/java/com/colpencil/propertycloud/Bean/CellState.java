package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

public class CellState implements Serializable {

    private String propertytel;
    private String servicetel;
    private boolean hasShen;
    private boolean isProprietor;
    private boolean isClick;

    public String getPropertytel() {
        return propertytel;
    }

    public void setPropertytel(String propertytel) {
        this.propertytel = propertytel;
    }

    public String getServicetel() {
        return servicetel;
    }

    public void setServicetel(String servicetel) {
        this.servicetel = servicetel;
    }

    public boolean isHasShen() {
        return hasShen;
    }

    public void setHasShen(boolean hasShen) {
        this.hasShen = hasShen;
    }

    public boolean isIsProprietor() {
        return isProprietor;
    }

    public void setIsProprietor(boolean isProprietor) {
        this.isProprietor = isProprietor;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
