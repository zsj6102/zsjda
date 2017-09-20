package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @Description: 设备管理
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/23
 */
public class Equipment implements Serializable {

    public String eq_code;
    public String date;
    public String record;
    public String eq_name;
    public int maintain_id;
    public String emp_name;
    public String eq_location;
    public String curr_time;
    public int current;
    public int last_emp;
    public int equipment_id;
    public int eq_type;
    public int emp_id;

    @Override
    public String toString() {
        return "Equipment{" +
                "eq_code='" + eq_code + '\'' +
                ", date='" + date + '\'' +
                ", record='" + record + '\'' +
                ", eq_name='" + eq_name + '\'' +
                ", maintain_id=" + maintain_id +
                ", emp_name='" + emp_name + '\'' +
                '}';
    }
}
