package com.colpencil.propertycloud.Bean;

import java.util.List;

/**
 * @author 汪 亮
 * @Description: 物业建议列表
 * @Email DramaScript@outlook.com
 * @date 2016/9/7
 */
public class AdviceList {

    public String title;
    public int approve_id;
    public String sendtm;
    public String detaildesc;
    public String company ;
    public List<String> pic;

    @Override
    public String toString() {
        return "AdviceList{" +
                "title='" + title + '\'' +
                ", approve_id='" + approve_id + '\'' +
                ", sendtm='" + sendtm + '\'' +
                ", detaildesc='" + detaildesc + '\'' +
                ", company='" + company + '\'' +
                ", pic=" + pic +
                '}';
    }
}
