package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

public class FindGoodsId implements Serializable {

    public int cat_id;
    public String cat_name;
    public int type_id;

    @Override
    public String toString() {
        return "FindGoodsId{" +
                "cat_id=" + cat_id +
                ", cat_name='" + cat_name + '\'' +
                ", type_id=" + type_id +
                '}';
    }
}
