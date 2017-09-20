package com.colpencil.tenement.View.Adpaters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.colpencil.tenement.Bean.Visitor;
import com.colpencil.tenement.R;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @Description: 访客管理
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/25
 */
public class VisitorAdapter extends SuperAdapter<Visitor> {

    private Context context;

    public VisitorAdapter(Context context, List<Visitor> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Visitor item) {
        holder.setText(R.id.tv_date,"到访时间："+item.date);
        holder.setText(R.id.tv_name,item.name);
        holder.setText(R.id.tv_phone,item.phone);
        holder.setText(R.id.tv_reason,item.describe);
        holder.setText(R.id.tv_id,item.identity);
        holder.setText(R.id.tv_location,item.location);
        holder.setOnClickListener(R.id.ll_call, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(item.phone)){
                    ToastTools.showShort(context,"拨打的电话有误！");
                    return;
                }
                MaterialDialog show = new MaterialDialog.Builder(context)
                        .title("温馨提示")
                        .content("是否拨打该电话？")
                        .positiveText(R.string.online_yes)
                        .negativeText(R.string.online_no)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                Uri data = Uri.parse("tel:" + item.phone);
                                intent.setData(data);
                                context.startActivity(intent);
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            }
                        })
                        .show();
            }
        });
    }
}
