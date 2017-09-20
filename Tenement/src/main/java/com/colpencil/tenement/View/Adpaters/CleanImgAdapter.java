package com.colpencil.tenement.View.Adpaters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Tools.PhotoPreview.PhotoActivity;
import com.colpencil.tenement.View.Adpaters.Base.CommonAdapter;
import com.colpencil.tenement.View.Adpaters.Base.CommonViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ImgTool;

import java.util.ArrayList;
import java.util.List;

public class CleanImgAdapter extends CommonAdapter<String> {

    private int width;
    private int height;
    private Activity context;
    private boolean preventDoubleClick = false; //预防双击
    private List<String> imageUrls = new ArrayList<>();

    public CleanImgAdapter(Activity context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        imageUrls.addAll(mDatas);
        ColpencilLogger.e("size="+imageUrls.size());
    }

    @Override
    public void convert(CommonViewHolder holder, String item, int position) {
        final ImageView iv = holder.getView(R.id.item_green_iv);
        ImageLoader.getInstance().displayImage(item, iv);
//        iv.setOnClickListener(photoListener);
//        iv.setTag(position);
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
    }

    private View.OnClickListener photoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = (int) v.getTag();
            ViewGroup parent = (ViewGroup) v.getParent();

            int childCount = parent.getChildCount();
            ArrayList<Rect> rects = new ArrayList<>();
            for (int i = 0; i < childCount; i++) {
                Rect rect = new Rect();
                View child = parent.getChildAt(i);
                child.getGlobalVisibleRect(rect);
                rects.add(rect);
            }
            Intent intent = new Intent(context, PhotoActivity.class);
            String imageArray[] = new String[imageUrls.size()];
            for(int i = 0;i<imageArray.length;i++){
                imageArray[i] = imageUrls.get(i);
            }
            intent.putExtra("imgUrls", imageArray);
            intent.putExtra("index", index);
            intent.putExtra("bounds", rects);
            context.startActivity(intent);
            context.overridePendingTransition(0, 0);
        }
    };
}
