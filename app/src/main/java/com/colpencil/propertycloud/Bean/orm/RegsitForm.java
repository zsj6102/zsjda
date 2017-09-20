package com.colpencil.propertycloud.Bean.orm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:装修人员登记表信息
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/14
 */
public class RegsitForm implements Serializable{

    public int approveid;
    public int wkId;
    public String name;
    public String tel;
    public String idNum;
    public String photo;

    public boolean type;

    public boolean isZhu = false;

}