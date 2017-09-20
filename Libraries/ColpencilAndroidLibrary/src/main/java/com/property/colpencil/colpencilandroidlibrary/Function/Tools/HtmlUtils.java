package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by 陈宝 on 2016/6/23.
 */
public class HtmlUtils {

    /**
     * 从网络解析Html
     */
    public static void parseHtmlfromWeb(final String... params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(params[0]).timeout(5000).get();
                    selectTag(document);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 统一解析方法
     * getElementsByTag方法指的是对html标签进行解析，比如<p></p>,<div></div>
     * getElementsByClass方法指的是对class类进行解析，比如<p class="test"></p>中的test
     * getElementById方法指的是通过id进行解析，比如<p id="test"></p>中的test
     * attr方法指的是取标签的具体属性，比如<a href="www.baidu.com"></a>中的href
     *
     * @param document
     */
    private static void parseTag(Document document) {
        Element ele = document.getElementById("contents");  //这里拿到的是一个div模块的Element
        Elements links = ele.getElementsByTag("p");     //这里拿到的是上面div模块下的所有a标签的Element列表
        for (Element link : links) {
            Log.e("ptext", link.text());       //这里获取的是所有的a标签的链接
        }

    }

    /**
     * id的格式为#id
     * 标签的格式为直接输入标签名
     * 类的格式为.class
     * 属性名查找为   标签[class="class名"]或者    标签[id="id名"]
     * 属性前缀查找    标签[^class的前面两个字母]
     * 文本内容查找    标签:contains("内容")
     *
     * @param document
     */
    private static void selectTag(Document document) {
        Elements links = document.select("span:matchesOwn(^3)");
        for (Element link : links) {
            Log.e("href", link.attr("href"));
        }
    }

}
