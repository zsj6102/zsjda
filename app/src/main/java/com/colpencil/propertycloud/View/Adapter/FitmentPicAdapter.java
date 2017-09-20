package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.propertycloud.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

public class FitmentPicAdapter extends SuperAdapter<String>{

    private int wholeprogress;

    public FitmentPicAdapter(Context context, List<String> items, int layoutResId,int wholeprogress) {
        super(context, items, layoutResId);
        this.wholeprogress =wholeprogress;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, String item) {
        ImageView iv_pic = holder.getView(R.id.iv_pic);
        ImageLoader.getInstance().displayImage(item,iv_pic);
        if (wholeprogress<=1){
            holder.setVisibility(R.id.iv_del, View.VISIBLE);
        }else {
            holder.setVisibility(R.id.iv_del, View.INVISIBLE);
        }
    }
}
