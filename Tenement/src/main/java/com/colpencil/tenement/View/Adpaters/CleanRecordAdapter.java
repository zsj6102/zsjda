package com.colpencil.tenement.View.Adpaters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.colpencil.tenement.Bean.CleanRecord;
import com.colpencil.tenement.Bean.ImagePreview;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Tools.PhotoPreview.PhotoActivity;
import com.colpencil.tenement.Tools.SpanUtils;
import com.colpencil.tenement.Ui.AdaptableTextView;
import com.colpencil.tenement.View.Activitys.Workbeach.ReviseRecordActivity;
import com.google.gson.Gson;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilGridView;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 汪 亮
 * @Description: 打扫/巡逻记录
 * @Email DramaScript@outlook.com
 * @date 2016/9/22
 */
public class CleanRecordAdapter extends SuperAdapter<CleanRecord> {

    private Activity context;
    private ImagePreview imagePreview;
    private Gson gson;
    private String json;
    private Intent intent;
    private int type;


    public CleanRecordAdapter(Activity context, List<CleanRecord> mDatas, int itemLayoutId, int type) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        this.type = type;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CleanRecord item) {
        SpannableString ss1;
        AdaptableTextView tv_address = holder.getView(R.id.item_greenclean_address);
        AdaptableTextView tv_desc = holder.getView(R.id.item_greenclean_task);
        TextView item_greenclean_village =holder.getView(R.id.item_greenclean_village);
        Button btn_change = holder.getView(R.id.btn_change);

        int userId = SharedPreferencesUtil.getInstance(context).getInt(StringConfig.USERID);
        if(userId == item.getEmpId()){
            btn_change.setBackgroundResource(R.drawable.rect_blue); //自己
        } else {
            btn_change.setBackgroundResource(R.drawable.rect_gre); //他人
        }
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userId == item.getEmpId()){
                    intent = new Intent(context, ReviseRecordActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("cleanRecord", item);
                    intent.putExtras(bundle);
                    intent.putExtra(StringConfig.TYPE, type);
                    context.startActivity(intent);
                } else {
                    ToastTools.showShort(context, false, "只能修改自己的记录");
                }
            }
        });

        ss1 = new SpannableString("工作地区："+item.getCommunityName());
        ss1.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_drak3)),0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        item_greenclean_village.setText(ss1);

        ss1 = new SpannableString("打点时间：" + item.getDate());
        ss1.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_drak3)),0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.setText(R.id.tv_time, ss1);

//        ss1 = new SpannableString("详细地址："+item.getPlace());
//        ss1.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_drak3)),0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_address.setText(item.getPlace());

//        ss1 = new SpannableString("工作任务："+ item.getWorkTask());
//        ss1.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_drak3)),0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_desc.setText(item.getWorkTask());

        ColpencilGridView gridView = holder.getView(R.id.item_greenclean_gridview);
        List<String> imageList = new ArrayList<String>();
        for (int i = 0; i < item.getImageList().size(); i++) {
            imageList.add(item.getImageList().get(i));
        }
        CleanImgAdapter adapter = new CleanImgAdapter(context,imageList, R.layout.item_green_image);
        gridView.setAdapter(adapter);
        imagePreview = new ImagePreview();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (item.getImageList().size()!=0){
                    List<String> imageList = new ArrayList<String>();
                    for (int i=0;i<item.getImageList().size();i++){
                        imageList.add(item.getImageList().get(i));
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
                    for(int i = 0;i<imageArray.length;i++){
                        imageArray[i] = imageList.get(i);
                    }
                    intent.putExtra("imgUrls", imageArray);
                    intent.putExtra("index", position);
                    intent.putExtra("bounds", rects);
                    context.startActivity(intent);
                    ColpencilLogger.e("size="+item.getImageList().size());
                    context.overridePendingTransition(0, 0);
                }
            }
        });
    }

}
