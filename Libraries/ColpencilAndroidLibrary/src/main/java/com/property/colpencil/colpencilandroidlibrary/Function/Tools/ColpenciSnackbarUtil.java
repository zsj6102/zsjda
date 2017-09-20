package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.property.colpencil.colpencilandroidlibrary.R;


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
 * <p/>
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p/>
 * 作者：ZenFeng
 * <p/>
 * 描述：Snackbar 提示框
 */
public class ColpenciSnackbarUtil {

    /**
     * @param view    //上下文布局
     * @param message //提示消息
     * @deprecated //低部弹出提示框
     */
    public static void downShowing(View view, String message) {
        Snackbar downSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        downSnackbar.show();
    }

    /**
     * @param view//低部弹出提示框
     * @param message//上下文布局
     * @param snackbartype//提示消息时长 1:标识时间显示较短 2：时间显示较长
     * @deprecated //低部弹出提示框
     */
    public static void downShowing(View view, String message, int snackbartype) {
        Snackbar downSnackbar = null;
        if (snackbartype == 1) {
            downSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        } else {
            downSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        }
        downSnackbar.show();
    }

    /**
     * @param view                   //上下文布局
     * @param message                //提示消息
     * @param actionMessage//动作操作提示语
     * @param downAction//单击事件
     * @deprecated //低部弹出提示框
     */
    public static void downShowing(View view, String message, String actionMessage, View.OnClickListener downAction) {
        Snackbar downSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        downSnackbar.setAction(actionMessage, downAction);
        downSnackbar.show();
    }

    /**
     * @param view                      //上下文布局
     * @param message                   //提示消息
     * @param messageColor//提示文字颜色
     * @param backgroundColor//提示框的背景颜色
     * @deprecated //低部弹出提示框
     */
    public static void downShowing(View view, String message, int messageColor, int backgroundColor) {
        Snackbar downSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        View downView = downSnackbar.getView();
        if (downView != null) {//设置背景与文本字体颜色
            TextView downText = ((TextView) downView.findViewById(R.id.snackbar_text));
            downView.setBackgroundColor(backgroundColor);
            downText.setTextColor(messageColor);
        }
        downSnackbar.show();
    }

    /**
     * @param view                      //上下文布局
     * @param message                   //提示消息
     * @param actionMessage//动作操作提示语
     * @param downAction//单击事件
     * @param messageColor//提示文字颜色
     * @param backgroundColor//提示框的背景颜色
     * @deprecated //低部弹出提示框
     */
    public static void downShowing(View view, String message, String actionMessage, View.OnClickListener downAction, int messageColor, int backgroundColor) {
        Snackbar downSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        View downView = downSnackbar.getView();
        if (downView != null) {//设置背景与文本字体颜色
            TextView downText = ((TextView) downView.findViewById(R.id.snackbar_text));
            downView.setBackgroundColor(backgroundColor);
            downText.setTextColor(messageColor);
        }
        downSnackbar.setAction(actionMessage, downAction);
        downSnackbar.show();
    }

    /**
     * @param view          //上下文布局
     * @param message//提示消息
     * @deprecated //顶部弹出提示框
     */
    public static void topShowing(View view, String message) {
        TSnackbar topSnackbar = TSnackbar.make(view, message, TSnackbar.LENGTH_SHORT);
        topSnackbar.show();
    }

    /**
     * @param view                  //上下文布局
     * @param message//提示消息
     * @param tsnackbartype//提示消息时长 1:标识时间显示较短 2：时间显示较长
     * @deprecated //顶部弹出提示框
     */
    public static void topShowing(View view, String message, int tsnackbartype) {
        TSnackbar topSnackbar = null;
        if (tsnackbartype == 1) {
            topSnackbar = TSnackbar.make(view, message, TSnackbar.LENGTH_SHORT);
        } else {
            topSnackbar = TSnackbar.make(view, message, TSnackbar.LENGTH_LONG);
        }
        topSnackbar.show();
    }

    /**
     * @param view                   //上下文布局
     * @param message//提示消息
     * @param actionMessage//动作操作提示语
     * @param topAction//单击事件
     * @deprecated //顶部弹出提示框
     */
    public static void topShowing(View view, String message, String actionMessage, View.OnClickListener topAction) {
        TSnackbar topSnackbar = TSnackbar.make(view, message, TSnackbar.LENGTH_INDEFINITE);
        topSnackbar.setAction(actionMessage, topAction);
        topSnackbar.show();
    }

    /**
     * @param view                      //上下文布局
     * @param message//提示消息
     * @param messageColor//提示文字颜色
     * @param backgroundColor//提示框的背景颜色
     * @deprecated //顶部弹出提示框
     */
    public static void topShowing(View view, String message, int messageColor, int backgroundColor) {
        TSnackbar topSnackbar = TSnackbar.make(view, message, TSnackbar.LENGTH_SHORT);
        View topView = topSnackbar.getView();
        if (topView != null) {//设置背景与文本字体颜色
            TextView topText = (TextView) topView.findViewById(R.id.snackbar_text);
            topView.setBackgroundColor(backgroundColor);
            topText.setTextColor(messageColor);
        }
        topSnackbar.show();
    }

    /**
     * @param view                      //上下文布局
     * @param message//提示消息
     * @param actionMessage//动作操作提示语
     * @param topAction//单击事件
     * @param messageColor//提示文字颜色
     * @param backgroundColor//提示框的背景颜色
     * @deprecated //顶部弹出提示框
     */
    public static void topShowing(View view, String message, String actionMessage, View.OnClickListener topAction, int messageColor, int backgroundColor) {
        TSnackbar topSnackbar = TSnackbar.make(view, message, TSnackbar.LENGTH_INDEFINITE);
        View topView = topSnackbar.getView();
        if (topView != null) {//设置背景与文本字体颜色
            TextView topText = (TextView) topView.findViewById(R.id.snackbar_text);
            topView.setBackgroundColor(backgroundColor);
            topText.setTextColor(messageColor);
        }
        topSnackbar.setAction(actionMessage, topAction);
        topSnackbar.show();
    }
}
