package com.colpencil.propertycloud.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 汪 亮
 * @Description: 首页数据
 * @Email DramaScript@outlook.com
 * @date 2016/11/18
 */
public class HomeData implements Serializable {

    public int code;
    public String message;
    public String articleMore;
    public String noticeMore;
    public String serUrl;
    public String jUrl;
    public String qUrl;
    public String gUrl;
    public String cUrl;
    public String workReportUrl;
    public String luckDrawUrl;
    /**
     * 装修服务url
     */
    public String zxfwUrl;
    public List<AdData> advData = new ArrayList<>();
    public List<NoticeData> noticeData = new ArrayList<>();
    public List<ArticleData> articleData = new ArrayList<>();

    @Override
    public String toString() {
        return "HomeData{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", advData=" + advData +
                ", noticeData=" + noticeData +
                ", articleData=" + articleData +
                '}';
    }
}
