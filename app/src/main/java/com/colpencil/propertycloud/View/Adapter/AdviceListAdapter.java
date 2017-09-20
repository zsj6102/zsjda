package com.colpencil.propertycloud.View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.AdapterView;

import com.colpencil.propertycloud.Bean.AdviceList;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.PhotoPreview.PhotoActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilGridView;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 汪 亮
 * @Description: 物业意见列表
 * @Email DramaScript@outlook.com
 * @date 2016/9/7
 */
public class AdviceListAdapter extends SuperAdapter<AdviceList> {

    private Activity context;

    public AdviceListAdapter(Activity context, List<AdviceList> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final AdviceList item) {
        holder.setText(R.id.tv_reason, item.title);
        holder.setText(R.id.tv_content, item.detaildesc);
        holder.setText(R.id.tv_place, item.company);
        holder.setText(R.id.tv_time, item.sendtm);
        ColpencilGridView gl_pic = holder.getView(R.id.gl_pic);
        CurrentPicAdapter adapter = new CurrentPicAdapter(context, item.pic, R.layout.item_pic);
        gl_pic.setAdapter(adapter);
        gl_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (item.pic.size() != 0) {
                    List<String> imageList = new ArrayList<String>();
                    for (int i = 0; i < item.pic.size(); i++) {
                        imageList.add(item.pic.get(i));
                    }
                    int childCount = parent.getChildCount();
                    ArrayList<Rect> rects = new ArrayList<>();
                    for (int i = 0; i < childCount; i++) {
                        Rect rect = new Rect();
                        View child = parent.getChildAt(i);
                        child.getGlobalVisibleRect(rect);
                        rects.add(rect);
                    }
                    Intent intent = new Intent(context, PhotoActivity.class);
                    String imageArray[] = new String[imageList.size()];
                    for (int i = 0; i < imageArray.length; i++) {
                        imageArray[i] = imageList.get(i);
                    }
                    intent.putExtra("imgUrls", imageArray);
                    intent.putExtra("index", position);
                    intent.putExtra("bounds", rects);
                    context.startActivity(intent);
                    ColpencilLogger.e("size=" + item.pic.size());
                    context.overridePendingTransition(0, 0);
                }
            }
        });
    }
}
