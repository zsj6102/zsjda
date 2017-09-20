package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

public class Goods implements Serializable {

    public int cat_id;
    public int level;
    public int parent_id;
    public String image;
    public String name;
    public String url;

    @Override
    public String toString() {
        return "Goods{" +
                "cat_id=" + cat_id +
                ", level=" + level +
                ", parent_id=" + parent_id +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
