package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @Description:
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/19
 */
public class SingleToastTools {

    public static Toast mMiuiToast;
    public static TextView tv_msg;

    /**
     * @Title: showToast
     * @Description: TODO  Single row of Toast
     * @param @param mContext
     * @param @param msg    set content
     * @return void
     * @throws
     */
    public static void showToast(Context context, String msg) {
        if (mMiuiToast == null) {
           mMiuiToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        mMiuiToast.show();

    }

    /**
     * Can be directly used in the child thread toast
     *
     * @param context
     * @param msg
     */
    public static void showSuperToast(final Activity context, final String msg) {
        if ("main".equals(Thread.currentThread().getName())) {
            Toast.makeText(context, msg,Toast.LENGTH_SHORT ).show();
        } else {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
