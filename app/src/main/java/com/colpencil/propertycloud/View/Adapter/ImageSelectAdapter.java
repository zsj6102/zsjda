package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.CommonAdapter;
import com.colpencil.propertycloud.Tools.CommonViewHolder;

import java.util.List;

public class ImageSelectAdapter extends CommonAdapter<String> {

    private int width;
    private int height;

    public ImageSelectAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, String item, int position) {
        final ImageView iv = helper.getView(R.id.item_grida_image);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        helper.setImageByUrl(mContext, R.id.item_grida_image, item);
        if (width == 0 || height == 0) {
            iv.post(new Runnable() {
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
    }
}
