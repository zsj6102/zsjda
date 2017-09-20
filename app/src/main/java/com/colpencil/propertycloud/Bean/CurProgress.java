package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description: 装修进度
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/19
 */
public class CurProgress implements Serializable {

    public int aprovid;
    public String aprovtm;
    public String dcname;
    public int auditstatus;
    public int wholeprogress;
    public String depositsn;
    public String relcostsn;
    public String refund_reason;
    public double deposit;
    public double relcosts;
    public int depositpaystatus;
    public int relcostspaystatus;
    public String othermaterial;

    public int regulation;
    public int design;
    public int application;
    public int aptitude;
    public int promise;
    public int personreg;
    public int communityid;
    public int houseid;
    public int decorateid;
    public int is_apply_leave;

    @Override
    public String toString() {
        return "CurProgress{" +
                "aprovid=" + aprovid +
                ", aprovtm='" + aprovtm + '\'' +
                ", dcname='" + dcname + '\'' +
                ", auditstatus=" + auditstatus +
                ", wholeprogress=" + wholeprogress +
                ", depositsn='" + depositsn + '\'' +
                ", relcostsn='" + relcostsn + '\'' +
                ", deposit=" + deposit +
                ", relcosts=" + relcosts +
                ", depositpaystatus=" + depositpaystatus +
                ", relcostspaystatus=" + relcostspaystatus +
                ", othermaterial='" + othermaterial + '\'' +
                '}';
    }
}
