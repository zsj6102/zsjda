package com.colpencil.propertycloud.Bean;

import java.io.Serializable;
import java.util.List;

public class GoodsList implements Serializable {

    public String image;
    public int cat_id;
    public int level;
    public String name;
    public int parent_id;
    public List<Goods> children;

    @Override
    public String toString() {
        return "GoodsList{" +
                "image='" + image + '\'' +
                ", cat_id=" + cat_id +
                ", level=" + level +
                ", name='" + name + '\'' +
                ", parent_id=" + parent_id +
                ", children=" + children +
                '}';
    }
}
