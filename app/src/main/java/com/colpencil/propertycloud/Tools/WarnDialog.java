package com.colpencil.propertycloud.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Present.Home.OrderPayActivity;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.ApiCloudActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;

/**
 * @author 汪 亮
 * @Description: 提示不是业主的对话框
 * @Email DramaScript@outlook.com
 * @date 2016/11/25
 */
public class WarnDialog {


    private static RelativeLayout rl_pro_tel;
    private static RelativeLayout rl_ke_tel;
    private static TextView tv_pro_tel;
    private static TextView tv_ke_tel;
    private static TextView tv_know;

    public static void showInfo(final Context mContext, final String info) {
        View view = View.inflate(mContext, R.layout.dialog_info, null);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(view);
        TextView tv_info = (TextView) view.findViewById(R.id.tv_info);
        TextView tv_know = (TextView) view.findViewById(R.id.tv_know);
        tv_info.setText(info);
        tv_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static void showInfoAdd(final Activity mContext, final String info) {
        View view = View.inflate(mContext, R.layout.dialog_info, null);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(view);
        TextView tv_info = (TextView) view.findViewById(R.id.tv_info);
        TextView tv_know = (TextView) view.findViewById(R.id.tv_know);
        tv_info.setText(info);
        tv_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.finish();
            }
        });
    }

    public static void showTel(final Context mContext, final String info, String tel) {
        View view = View.inflate(mContext, R.layout.dialog_door, null);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(view);
        TextView tv_info = (TextView) view.findViewById(R.id.tv_info);
        TextView tv_know = (TextView) view.findViewById(R.id.tv_know);
        TextView tv_tel = (TextView) view.findViewById(R.id.tv_tel);
        tv_info.setText(info);
        tv_tel.setText(tel);
        tv_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static void show(final Context mContext, final String proTel, final String keTel) {
        View view = View.inflate(mContext, R.layout.dialog_warn, null);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(view);
       /* final DialogPlus dialogPlus = DialogPlus.newDialog(mContext)
                .setContentHolder(new ViewHolder(view))
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .create();
        dialogPlus.show();*/
        rl_pro_tel = (RelativeLayout) view.findViewById(R.id.rl_pro_tel);
        rl_ke_tel = (RelativeLayout) view.findViewById(R.id.rl_ke_tel);
        tv_pro_tel = (TextView) view.findViewById(R.id.tv_pro_tel);
        tv_ke_tel = (TextView) view.findViewById(R.id.tv_ke_tel);
        tv_know = (TextView) view.findViewById(R.id.tv_know);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_pro_tel.setText(proTel);
        rl_pro_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColpencilLogger.e("---------1");
                if (!TextUtils.isEmpty(proTel)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + proTel);
                    intent.setData(data);
                    mContext.startActivity(intent);
                }

            }
        });

        tv_ke_tel.setText(keTel);
        rl_ke_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(proTel)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + keTel);
                    intent.setData(data);
                    mContext.startActivity(intent);
                }
                ColpencilLogger.e("---------2");

            }
        });
        tv_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static void show(final Context mContext, final String proTel, final String keTel, String content) {
        View view = View.inflate(mContext, R.layout.dialog_warn, null);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(view);
       /* final DialogPlus dialogPlus = DialogPlus.newDialog(mContext)
                .setContentHolder(new ViewHolder(view))
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .create();
        dialogPlus.show();*/
        rl_pro_tel = (RelativeLayout) view.findViewById(R.id.rl_pro_tel);
        rl_ke_tel = (RelativeLayout) view.findViewById(R.id.rl_ke_tel);
        tv_pro_tel = (TextView) view.findViewById(R.id.tv_pro_tel);
        tv_ke_tel = (TextView) view.findViewById(R.id.tv_ke_tel);
        tv_know = (TextView) view.findViewById(R.id.tv_know);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_content.setText(content);
        tv_pro_tel.setText(proTel);
        rl_pro_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColpencilLogger.e("---------1");
                if (!TextUtils.isEmpty(proTel)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + proTel);
                    intent.setData(data);
                    mContext.startActivity(intent);
                }

            }
        });

        tv_ke_tel.setText(keTel);
        rl_ke_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(proTel)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + keTel);
                    intent.setData(data);
                    mContext.startActivity(intent);
                }
                ColpencilLogger.e("---------2");

            }
        });
        tv_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static void showInfoAndIntent(final Activity mContext, final String info, final Class<?> clazz) {
        View view = View.inflate(mContext, R.layout.dialog_info, null);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(view);
        TextView tv_info = (TextView) view.findViewById(R.id.tv_info);
        TextView tv_know = (TextView) view.findViewById(R.id.tv_know);
        tv_info.setText(info);
        tv_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, clazz));
                mContext.finish();
            }
        });
    }

    public static void showNotif(Activity mContext, String title, String content) {
        View view = View.inflate(mContext, R.layout.natifa_gong, null);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(view);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        TextView tv_know = (TextView) view.findViewById(R.id.tv_know);
        tv_title.setText(title);
        tv_content.setText(content);
        tv_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static void showUpdateDialog(final Activity mContext, final String url, String content, boolean forceUpdate) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_check_version, null);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        if (forceUpdate) {
            dialog.setCancelable(false);
        } else {
            dialog.setCancelable(true);
        }
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(view);
        ((TextView) view.findViewById(R.id.tv_message)).setText(content);
        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                mContext.startActivity(intent);
                System.exit(0);
                dialog.dismiss();
            }
        });
    }

    public static void showDetail(final Activity mContext, final String info, final String url) {
        View view = View.inflate(mContext, R.layout.dialog_info, null);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(view);
        TextView tv_info = (TextView) view.findViewById(R.id.tv_info);
        TextView tv_know = (TextView) view.findViewById(R.id.tv_know);
        tv_info.setText(info);
        tv_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(mContext, ApiCloudActivity.class);
                intent.putExtra("startUrl", url);
                mContext.startActivity(intent);
                mContext.finish();
            }
        });
    }

}
