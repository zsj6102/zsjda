package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.property.colpencil.colpencilandroidlibrary.R;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilToast.SVProgressHUD;
import com.wangjie.shadowviewhelper.ShadowProperty;
import com.wangjie.shadowviewhelper.ShadowViewHelper;

/**
 * @author 汪 亮
 * @Description:Toast统一管理类
 * @Email DramaScript@outlook.com
 * @date 16/6/23
 */
public class ToastTools {

    private ToastTools() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 是否显示Tost
     */
    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     */
    public static void showShort(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     */
    public static void showLong(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     */
    public static void show(Context context, int message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 展示警告的 提示
     *
     * @param context
     * @param message
     */
    public static void showShort(Activity context, String message) {
       /* Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        View view = View.inflate(context, R.layout.toast_warm, null);
        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
        tv_message.setText(message);
        toast.setView(view);
        toast.show();*/
        SVProgressHUD.showInfoWithStatus(context, message, SVProgressHUD.SVProgressHUDMaskType.None);
    }

    /**
     * 展示错误/成功的提示
     *
     * @param context
     * @param isSuccess
     * @param message
     */
    public static void showShort(Activity context, boolean isSuccess, String message) {
       /* Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        View view = View.inflate(context, R.layout.toast_custom, null);
        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
        ImageView iv_state = (ImageView) view.findViewById(R.id.iv_state);*/
//        ShadowViewHelper.bindShadowHelper(
//                new ShadowProperty()
//                        .setShadowColor(0x77000000)
//                        .setShadowDy(UITools.dp2px(context, 0.5f))
//                        .setShadowRadius(UITools.dp2px(context, 3))
//                , context.findViewById(R.id.rl_shacke));
//        tv_message.setText(message);
        if (isSuccess == true) {
            SVProgressHUD.showSuccessWithStatus(context, message);
        } else {
            SVProgressHUD.showErrorWithStatus(context, message, SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
        }
        /*toast.setView(view);
        toast.show();*/
    }

    /**
     * 展示错误/成功的提示
     *
     * @param context
     * @param message
     */
    public static void showForget(Activity context, boolean isSuccess, String message) {
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        View view = View.inflate(context, R.layout.toast_custom, null);
        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
        ImageView iv_state = (ImageView) view.findViewById(R.id.iv_state);
        if (isSuccess == true) {
            iv_state.setImageDrawable(context.getResources().getDrawable(R.mipmap.ok));
        } else {
            iv_state.setImageDrawable(context.getResources().getDrawable(R.mipmap.fail));
        }
        tv_message.setText(message);
        toast.setView(view);
        toast.show();
    }
}