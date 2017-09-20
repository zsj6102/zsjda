package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;

import com.colpencil.propertycloud.Bean.LiveStreaming;
import com.colpencil.propertycloud.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @Description:  摄像头列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
public class LiveStreamListAdapter extends SuperAdapter<LiveStreaming> {

    private Context context;

    public LiveStreamListAdapter(Context context, List<LiveStreaming> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, LiveStreaming item) {
        holder.setText(R.id.tv_name,item.name);
    }
}
