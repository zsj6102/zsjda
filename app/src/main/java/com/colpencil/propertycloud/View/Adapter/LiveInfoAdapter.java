package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.colpencil.propertycloud.Bean.LiveInfoList;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.CloseManager.MenberManagerActivity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @author 汪 亮
 * @Description: 居住情况
 * @Email DramaScript@outlook.com
 * @date 2016/9/12
 */
public class LiveInfoAdapter extends SuperAdapter<LiveInfoList> {

    private Context context;

    public LiveInfoAdapter(Context context, List<LiveInfoList> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final LiveInfoList item) {
        holder.setText(R.id.tv_vilage_name, item.commnm);
        holder.setText(R.id.tv_jian, item.buldnm+"栋"+item.unitnm+"单元"+item.romnum);
//        holder.setText(R.id.tv_room_id, item.romnum);
//        holder.setText(R.id.tv_time, item.romnum);
//        holder.setText(R.id.tv_guan, item.usrtpnm);
        holder.setOnClickListener(R.id.iv_check, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenberManagerActivity.class);
                intent.putExtra("hseid",item.hseid);
                intent.putExtra("info", item.buldnm+"栋"+item.unitnm+"单元"+item.romnum);
                context.startActivity(intent);
            }
        });
    }
}
