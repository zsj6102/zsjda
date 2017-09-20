package com.colpencil.tenement.View.Adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.colpencil.tenement.R;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ImgTool;

import java.util.List;

public class UpLoadImgAdapter extends BaseAdapter {

    private List<String> imageList;
    private Context context;
    private int width;
    private int height;

    public UpLoadImgAdapter(Context context, List<String> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size() == 3 ? 3 : imageList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView iv;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.upload_img_item, null);
            iv = (ImageView) convertView.findViewById(R.id.item_grida_image);
            convertView.setTag(iv);
        } else {
            iv = (ImageView) convertView.getTag();
        }
        if (position == imageList.size()) {
            iv.setImageResource(R.mipmap.imgselect);
            if (position == 3) {
                iv.setVisibility(View.GONE);
            } else {
                iv.setVisibility(View.VISIBLE);
            }
        } else {
            ImgTool.getImgToolInstance(context).loadImgByString(imageList.get(position), iv);
        }
        if (width == 0 || height == 0) {
            iv.post(new Runnable() {//动态更换ImageView的高度
                @Override
                public void run() {
                    width = iv.getWidth();
                    height = width;
                    iv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                }
            });
        } else {
            iv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        }
        return convertView;
    }
}
