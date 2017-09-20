package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.colpencil.propertycloud.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

public class FitmentPicAdapter2 extends BaseAdapter {

    private Context context;
    private List<String> list;

    public FitmentPicAdapter2(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context,R.layout.fit_pic,null);
        ImageView iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
        Glide.with(context)
                .load(list.get(position))
                .into(iv_pic);
        return convertView;
    }
}
