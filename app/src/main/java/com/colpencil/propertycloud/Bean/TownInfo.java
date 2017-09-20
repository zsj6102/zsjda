package com.colpencil.propertycloud.Bean;

/**
 * author zaaach on 2016/1/26.
 */
public class TownInfo {

    private int region_id;
    private int p_region_id;
    private String local_name;
    private String first_name;

    public TownInfo(String first_name, String local_name) {
        this.first_name = first_name;
        this.local_name = local_name;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public int getP_region_id() {
        return p_region_id;
    }

    public void setP_region_id(int p_region_id) {
        this.p_region_id = p_region_id;
    }

    public String getLocal_name() {
        return local_name;
    }

    public void setLocal_name(String local_name) {
        this.local_name = local_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
}
