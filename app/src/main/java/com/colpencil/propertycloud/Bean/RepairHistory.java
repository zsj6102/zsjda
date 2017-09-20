package com.colpencil.propertycloud.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 汪 亮
 * @Description: 报修历史
 * @Email DramaScript@outlook.com
 * @date 2016/10/11
 */
public class RepairHistory implements Serializable {

    private String booktime;
    private String completm;
    private int orderid;
    private String repitems;
    private String description;
    private String empnm;
    private String mobile;
    private int state;
    private String statu;
    private int isfedbck;
    private String url_audio;
    private int audio_duration;
    private String repair_code;
    private String detail_description;
    private String property_opinion;
    private int repair_type;
    private int score;
    private String username;
    private String book_site;
    private String addtime;
    private List<String> pics;

    public String getBooktime() {
        return booktime;
    }

    public void setBooktime(String booktime) {
        this.booktime = booktime;
    }

    public String getCompletm() {
        return completm;
    }

    public void setCompletm(String completm) {
        this.completm = completm;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getRepitems() {
        return repitems;
    }

    public void setRepitems(String repitems) {
        this.repitems = repitems;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmpnm() {
        return empnm;
    }

    public void setEmpnm(String empnm) {
        this.empnm = empnm;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public int getIsfedbck() {
        return isfedbck;
    }

    public void setIsfedbck(int isfedbck) {
        this.isfedbck = isfedbck;
    }

    public String getUrl_audio() {
        return url_audio;
    }

    public void setUrl_audio(String url_audio) {
        this.url_audio = url_audio;
    }

    public int getAudio_duration() {
        return audio_duration;
    }

    public void setAudio_duration(int audio_duration) {
        this.audio_duration = audio_duration;
    }

    public String getRepair_code() {
        return repair_code;
    }

    public void setRepair_code(String repair_code) {
        this.repair_code = repair_code;
    }

    public String getDetail_description() {
        return detail_description;
    }

    public void setDetail_description(String detail_description) {
        this.detail_description = detail_description;
    }

    public String getProperty_opinion() {
        return property_opinion;
    }

    public void setProperty_opinion(String property_opinion) {
        this.property_opinion = property_opinion;
    }

    public int getRepair_type() {
        return repair_type;
    }

    public void setRepair_type(int repair_type) {
        this.repair_type = repair_type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBook_site() {
        return book_site;
    }

    public void setBook_site(String book_site) {
        this.book_site = book_site;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }
}
