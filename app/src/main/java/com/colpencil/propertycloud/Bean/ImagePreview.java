package com.colpencil.propertycloud.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 图片预览的界面
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/23
 */
public class ImagePreview implements Serializable{

    public List<String> imageList = new ArrayList<>();

    @Override
    public String toString() {
        return "ImagePreview{" +
                "imageList=" + imageList +
                '}';
    }
}
