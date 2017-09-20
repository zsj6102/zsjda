package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * @Description: 对话框的工具
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/16
 */
public class DialogTools {


    private static MaterialDialog materialDialog;

    public DialogTools() {

    }

    public static void showLoding(Context context, String title, String content){
        materialDialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .progress(true, 0)
                .show();
    }

    public static void showNoCancelLoding(Context context, String title, String content){
        materialDialog = new MaterialDialog.Builder(context)
                .cancelable(false)
                .title(title)
                .content(content)
                .progress(true, 0)
                .show();
    }

    public static void dissmiss(){
        if (materialDialog!=null){
            materialDialog.dismiss();
        }
    }

}
