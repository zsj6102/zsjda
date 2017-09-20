package com.colpencil.propertycloud.Bean;

import java.io.Serializable;

/**
 * @Description: 物业公共列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/18
 */
public class NoticeData implements Serializable {

    public int id;
    public String notice_code;
    public int property_id;
    public String title;
    public String content;
    public int type;
    public int level;
    public int clickcount;
    public String name;
    public String url;
    public int is_show;
    public int push_id;
    public int notice_id;
    public int push_category;
    public int push_targ;
    public int house_id;
    public int push_type;
    public int push_mode;
    public int push_status;
    public String push_time;
    public String create_time;
    public String notice_time;
}
