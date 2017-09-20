package com.colpencil.tenement.View.Adpaters;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.colpencil.tenement.Bean.Equipment;
import com.colpencil.tenement.R;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @Description: 设备管理
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/23
 */
public class EquipmentAdapter extends SuperAdapter<Equipment> {

    private Context mContext;
    private int flag;



    public EquipmentAdapter(Context context, List<Equipment> items, int layoutResId,int flag) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.flag = flag;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Equipment item) {
        holder.setText(R.id.tv_id,item.eq_code);
        holder.setText(R.id.tv_name,item.eq_name);
        holder.setText(R.id.loction,item.eq_location);
        int user_id = SharedPreferencesUtil.getInstance(mContext).getInt("user_id", 0);
        ColpencilLogger.e("user_id="+user_id+",emp_id="+item.emp_id);
        TextView tv_change = holder.getView(R.id.tv_change);
        if (item.current==user_id){
//            tv_change.setBackground(mContext.getResources().getDrawable(R.drawable.rect_green));
            holder.setBackgroundResource(R.id.tv_change,R.drawable.rect_line_blue);
            holder.setTextColor(R.id.tv_change,mContext.getResources().getColor(R.color.mian_blue));
        }else {
            holder.setBackgroundResource(R.id.tv_change,R.drawable.rect_gre);
            holder.setTextColor(R.id.tv_change,mContext.getResources().getColor(R.color.white));
        }
        SpannableString ss;
        TextView tv_record = holder.getView(R.id.tv_record);
        switch (flag){
            case 1://巡检
                holder.setText(R.id.tv_member,item.emp_name);
                holder.setText(R.id.tv,"巡检人员：");
                holder.setText(R.id.tv_date_name,"巡检日期：");
                ss = new SpannableString("巡检记录："+item.record);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_drak3)),0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_record.setText(ss);
                holder.setText(R.id.tv_date,item.curr_time);
                break;
            case 2://维修
                holder.setText(R.id.tv,"维修人员：");
                holder.setText(R.id.tv_member,item.emp_name);
                holder.setText(R.id.tv_date_name,"维修日期：");
                ss = new SpannableString("维修记录："+item.record);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_drak3)),0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_record.setText(ss);
                holder.setText(R.id.tv_date,item.curr_time);
                break;
            case 3://保养
                holder.setText(R.id.tv,"保养人员：");
                holder.setText(R.id.tv_member,item.emp_name);
                holder.setText(R.id.tv_date_name,"保养日期：");
                ss = new SpannableString("保养记录："+item.record);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_drak3)),0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_record.setText(ss);
                holder.setText(R.id.tv_date,item.curr_time);
                break;
        }
    }


}