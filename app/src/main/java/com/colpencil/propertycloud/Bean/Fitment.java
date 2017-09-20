package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description: 装修进度子条目
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/17
 */
public class Fitment implements Serializable {

    public String name;
    public boolean process;

    public double deposit;
    public double relcosts;

    public int depositpaystatus;
    public int relcostspaystatus;

    public int regulation;
    public int design;
    public int application;
    public int aptitude;
    public int promise;
    public int personreg;

    @Override
    public String toString() {
        return "Fitment{" +
                "name='" + name + '\'' +
                ", process=" + process +
                '}';
    }
}
