package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @Description: 上次维护信息
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/20
 */
public class LastRecord implements Serializable {

    public int lastEmp;
    public String lastTime;
    public String eqName;
    public String eqCode;
    public String eqLocation;
    public int currEmp;
    public String currEmpName;
    public String currContent;
    public int maintainId;

    @Override
    public String toString() {
        return "LastRecord{" +
                "lastEmp=" + lastEmp +
                ", lastTime='" + lastTime + '\'' +
                ", eqName='" + eqName + '\'' +
                ", eqCode='" + eqCode + '\'' +
                '}';
    }
}
