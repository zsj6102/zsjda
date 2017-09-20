package com.colpencil.propertycloud.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 汪 亮
 * @Description: 投诉历史
 * @Email DramaScript@outlook.com
 * @date 2016/9/5
 */
public class ComplaintHistroy implements Serializable {

    private int orderid;
    private String sugcode;
    private String sugtm;
    private String completm;
    private String classname;
    private String detaildesc;
    private int state;
    private String statu;
    private String empnm;
    private String tel;
    private int isfedbck;
    private String url_audio;
    private int audio_duration;
    private String detail_description;
    private String property_opinion;
    private List<String> pics;
    private int score;

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getSugcode() {
        return sugcode;
    }

    public void setSugcode(String sugcode) {
        this.sugcode = sugcode;
    }

    public String getSugtm() {
        return sugtm;
    }

    public void setSugtm(String sugtm) {
        this.sugtm = sugtm;
    }

    public String getCompletm() {
        return completm;
    }

    public void setCompletm(String completm) {
        this.completm = completm;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getDetaildesc() {
        return detaildesc;
    }

    public void setDetaildesc(String detaildesc) {
        this.detaildesc = detaildesc;
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

    public String getEmpnm() {
        return empnm;
    }

    public void setEmpnm(String empnm) {
        this.empnm = empnm;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
