package com.colpencil.tenement.View.Adpaters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import com.colpencil.tenement.Bean.UtilitiesRecord;
import com.colpencil.tenement.R;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @Description: 抄水电表记录适配器
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/14
 */
public class RecordDetailsAdapter extends SuperAdapter<UtilitiesRecord> {


    public RecordDetailsAdapter(Context context, List<UtilitiesRecord> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, UtilitiesRecord item) {
        holder.setText(R.id.ownername, item.getRoomNo());
        holder.setText(R.id.utilities_time, item.getDate());
        holder.setText(R.id.write_person, item.getEmpName());
        int user_id = SharedPreferencesUtil.getInstance(mContext).getInt("user_id", 0);
        if (item.getEmpId()!=user_id){
            holder.setBackgroundResource(R.id.tv_change,R.drawable.rect_gre);
            holder.setTextColor(R.id.tv_change, mContext.getResources().getColor(R.color.white));
        }else {
            if (item.getDate().substring(0,7).equals(TimeUtil.getTimeStrFromMillisHour(System.currentTimeMillis()).substring(0,7))){
                holder.setBackgroundResource(R.id.tv_change,R.drawable.rect_line_blue);
                holder.setTextColor(R.id.tv_change, mContext.getResources().getColor(R.color.mian_blue));
            }else {
                holder.setBackgroundResource(R.id.tv_change,R.drawable.rect_gre);
                holder.setTextColor(R.id.tv_change, mContext.getResources().getColor(R.color.white));
            }
        }
        if (item.getType()==0){
            holder.setText(R.id.room_id, "水表");
            if (item.getDegrees()==-1.0){
                holder.setText(R.id.write_num, "暂无本月见抄");
            }else {
                holder.setText(R.id.write_num, item.getDegrees() + "吨");
            }
            if (item.getLastRecord()==-1.0){
                holder.setText(R.id.write_last,"暂无上月见抄");
            }else {
                holder.setText(R.id.write_last, item.getLastRecord()+ "吨");
            }
        }else {
            holder.setText(R.id.room_id, "电表");
            if (item.getDegrees()==-1.0){
                holder.setText(R.id.write_num, "暂无本月见抄");
            }else {
                holder.setText(R.id.write_num, item.getDegrees() + "度");
            }
            if (item.getLastRecord()==-1.0){
                holder.setText(R.id.write_last,"暂无上月见抄");
            }else {
                holder.setText(R.id.write_last, item.getLastRecord()+ "度");
            }
        }
    }
}
