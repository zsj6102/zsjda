package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.widget.ImageView;

import com.colpencil.propertycloud.Bean.Goods;
import com.colpencil.propertycloud.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

public class GoodsAdapter extends SuperAdapter<Goods> {

    private DisplayImageOptions options;

    public GoodsAdapter(Context context, List<Goods> items, int layoutResId) {
        super(context, items, layoutResId);
        options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false) // default
                .cacheOnDisk(true) // default
                .showImageForEmptyUri(R.mipmap.no) // resource or
                // drawable
                .showImageOnFail(R.mipmap.no).build();
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Goods item) {
        ImageView iv_pic = holder.getView(R.id.iv_pic);
        ImageLoader.getInstance().displayImage(item.image,iv_pic,options);
        holder.setText(R.id.tv_name,item.name);
    }
}
