package com.colpencil.propertycloud.Tools;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.colpencil.propertycloud.R;
import com.youth.banner.loader.ImageLoader;

public class GlideLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
//        Glide.with(context).load(path)
//                .placeholder(R.mipmap.holder)
//                .error(R.mipmap.holder).into(imageView);

        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage((String) path,imageView);
    }
}
