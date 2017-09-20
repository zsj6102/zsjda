package com.colpencil.propertycloud.Bean;

import java.io.Serializable;
import java.util.List;

public class FitProcess implements Serializable{

    public List<FitmentStatue> materList;
    public String group;
    public String name;
    public String remark;
    public String receiveInfo;
    public String url;
    public int status;
    public int decorateid;
    public int wholeprogress;
    public int commonStatus;

    public int comunityid;
    public int aprovid;
    public int houseid;

    @Override
    public String toString() {
        return "FitProcess{" +
                "materList=" + materList +
                ", group='" + group + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
