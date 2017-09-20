package com.colpencil.tenement.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.colpencil.tenement.R;

public class WarnDialog {

    public static void showNotif(Activity mContext, String title, String content){
        View view = View.inflate(mContext, R.layout.natifa_gong, null);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.show();
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        TextView tv_know = (TextView) view.findViewById(R.id.tv_know);
        tv_title.setText(title);
        tv_content.setText(content);
        Window window = dialog.getWindow();
        window.setContentView(view);
        tv_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static void showUpdateDialog(final Activity mContext, final String url, String content,boolean forceUpdate) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_check_version, null);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        if (forceUpdate){
            dialog.setCancelable(false);
        }else {
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

}
