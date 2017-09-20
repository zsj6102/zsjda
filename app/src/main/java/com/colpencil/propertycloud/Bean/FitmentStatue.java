package com.colpencil.propertycloud.Bean;

import java.io.Serializable;
import java.util.List;

public class FitmentStatue implements Serializable {

    public List<String> listPic;
    public String name;
    public int  sn;
    public int status;

    @Override
    public String toString() {
        return "FitmentStatue{" +
                "listPic=" + listPic +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
