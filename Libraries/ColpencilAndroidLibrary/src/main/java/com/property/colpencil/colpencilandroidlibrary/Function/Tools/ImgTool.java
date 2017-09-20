package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.property.colpencil.colpencilandroidlibrary.R;

import java.io.File;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * <p>
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * 作者：曾老二
 * <p>
 * 描述：图片加载工具
 */
public class ImgTool {

    private static ImgTool imgTool;
    /**
     * 图片加载器
     */
    private static RequestManager requestManager;


    private ImgTool(Context context) {

        requestManager = Glide.with(context);
    }

    public static ImgTool getImgToolInstance(Context context) {
        if (imgTool == null) {
            imgTool = new ImgTool(context);
        }
        return imgTool;
    }
    /**
     * @param imgString 本地文件路径或者网络图片请求地址
     * @param imageView 加载控件
     */
    public void loadImgByString(String imgString, ImageView imageView) {

        requestManager.load(imgString).placeholder(R.drawable.defauot).into(imageView);

    }

    /**
     * @param uri       图片Uri
     * @param imageView 加载控件
     */
    public void loadImgByUri(Uri uri, ImageView imageView) {

        requestManager.load(uri).into(imageView);
    }

    /**
     * @param file      文件图片
     * @param imageView 加载控件
     */
    public void loadImgByFile(File file, ImageView imageView) {

        requestManager.load(file).into(imageView);
    }

    /**
     * @param resId     图片id
     * @param imageView 加载控件
     */
    public void loadImgByRes(int resId, ImageView imageView) {

        requestManager.load(resId).into(imageView);
    }
}
