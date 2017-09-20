package com.colpencil.tenement.View.Adpaters;

import android.content.Context;

import com.colpencil.tenement.Bean.Online;
import com.colpencil.tenement.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @author 汪 亮
 * @Description: 在线客服列表
 * @Email DramaScript@outlook.com
 * @date 2016/8/24
 */
public class OnlineListAdapter extends SuperAdapter<Online> {

    private Context mContext;

    public OnlineListAdapter(Context context, List<Online> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Online item) {
        holder.setText(R.id.tv_name, item.name);
        holder.setText(R.id.tv_msg, item.messgae);
        holder.setText(R.id.tv_last, "上次响应客服：" + item.last);
        holder.setText(R.id.tv_time, "响应时间：" + item.date);
    }
}
