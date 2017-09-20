package com.colpencil.propertycloud.Bean;

/**
 * @Description: rxbug的实体
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/12
 */
public class RxBusMsg {

    private int type;
    private String msg;
    private String logo;
    private String organizer;
    private String url;
    private EstateInfo info;

    private String sType;

    private String content;
    private String title;

    private int id;
    private int isOwner;

    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public EstateInfo getInfo() {
        return info;
    }

    public void setInfo(EstateInfo info) {
        this.info = info;
    }

    public int getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(int isOwner) {
        this.isOwner = isOwner;
    }
}
