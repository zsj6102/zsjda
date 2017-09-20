package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import android.app.Activity;
import android.content.Context;

import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilNotification.ColpencilNotification;

/**
 * @Description: 通知栏的工具类
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/19
 */
public class NotificationTools {

    private static ColpencilNotification notification;

    private NotificationTools() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     *
     * @param context 上下文
     * @param image  图片
     * @param title  标题
     * @param color  背景颜色
     * @param content 需要显示的内容
     */
    public static void show(Context context,int image,String title,int color,String content){
        notification = new ColpencilNotification.Builder().setContext(context)
               .setTime(System.currentTimeMillis())
               .setImgRes(image)
               .setTitle(title)
               .setColor(color)
               .setContent(content)
               .build()
               .show();
    }

    public static void show(Context context,String title,int color,String content){
        notification = new ColpencilNotification.Builder().setContext(context)
                .setTime(System.currentTimeMillis())
                .setTitle(title)
                .setColor(color)
                .setContent(content)
                .build()
                .show();
    }

    public static void show(Context context,int color,String content){
        notification = new ColpencilNotification.Builder().setContext(context)
                .setTime(System.currentTimeMillis())
                .setColor(color)
                .setContent(content)
                .build()
                .show();
    }

    public static void show(Context context,String content){
        notification = new ColpencilNotification.Builder().setContext(context)
                .setTime(System.currentTimeMillis())
                .setContent(content)
                .build()
                .show();
    }

    public static void show(Context context,String title,String content){
        notification = new ColpencilNotification.Builder().setContext(context)
                .setTime(System.currentTimeMillis())
                .setTitle(title)
                .setContent(content)
                .build()
                .show();
    }

    /**
     * 可以在子线程中显示的
     * @param activity
     * @param image
     * @param title
     * @param color
     * @param content
     */
    public static void showSuper(final Activity activity, final int image, final String title, final int color, final String content){
        if ("main".equals(Thread.currentThread().getName())) {
           show(activity,image,title,color,content);
        }else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    show(activity,image,title,color,content);
                }
            });
        }
    }

    public static void dismiss(){
        if (notification==null){
            return;
        }else {
            notification.dismiss();
        }
    }

}
