package com.colpencil.tenement.View.Adpaters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.tenement.Bean.SignList;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Tools.PhotoPreview.PhotoActivity;
import com.lzy.imagepicker.loader.ImageLoader;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ImgTool;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 签到/签退中的当天签到列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/17
 */
public class SignListDetailAdpater extends SuperAdapter<SignList.SignHistory> {

    private Activity context;

    public SignListDetailAdpater(Activity context, List<SignList.SignHistory> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, SignList.SignHistory item) {
        if (item.signType==0){
            holder.setText(R.id.tv_statue,"已签退");
            holder.setTextColor(R.id.tv_statue,context.getResources().getColor(R.color.color_ffb008));
        }else {
            holder.setText(R.id.tv_statue,"已签到");
            holder.setTextColor(R.id.tv_statue,context.getResources().getColor(R.color.color_5dc890));
        }
        holder.setText(R.id.tv_loaction,item.location);
        holder.setText(R.id.tv_time,item.signTime);
        ImageView tv_see_pic = holder.getView(R.id.tv_see_pic);
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add(item.signPhoto);
        tv_see_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColpencilLogger.e("imageUrls="+imageUrls.toString());
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
                intent.putExtra("index", 0);
                intent.putExtra("bounds", rects);
                context.startActivity(intent);
                context.overridePendingTransition(0, 0);
            }
        });
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(item.signPhoto, tv_see_pic);
    }
}
