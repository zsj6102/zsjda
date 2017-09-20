package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;

import com.colpencil.propertycloud.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

public class NationAdapter extends SuperAdapter<String> {

    private String content;
    private Context context;

    public NationAdapter(Context context, List<String> items, int layoutResId,String content) {
        super(context, items, layoutResId);
        this.content = content;
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, String item) {
        holder.setText(R.id.tv_nation,item);
        if (content.equals(item)){
            holder.setImageDrawable(R.id.iv_nation,context.getResources().getDrawable(R.mipmap.fit_check));
        }else {
            holder.setImageDrawable(R.id.iv_nation,context.getResources().getDrawable(R.mipmap.fit_uncheck));
        }
    }
}