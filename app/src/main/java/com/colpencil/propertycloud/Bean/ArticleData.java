package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description:
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/18
 */
public class ArticleData implements Serializable {

    public int id;
    public String title;
    public String news_time;
    public int cat_id;
    public String adapter;
    public String url;
    @Override
    public String toString() {
        return "ArticleData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cat_id=" + cat_id +
                '}';
    }
}
