package com.colpencil.propertycloud.Bean.orm;


public class FormBean {

    private int id;
    private String json;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public String toString() {
        return "FormBean{" +
                "id=" + id +
                ", json='" + json + '\'' +
                '}';
    }
}
