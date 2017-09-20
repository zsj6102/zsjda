package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.text.TextUtils;

import com.colpencil.propertycloud.Bean.Vilage;
import com.colpencil.propertycloud.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @Description: 小区选择
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/9
 */
public class VilageSelectAdapter extends SuperAdapter<Vilage> {

    private Context context;
    private String comuid;

    public VilageSelectAdapter(Context context, List<Vilage> items, int layoutResId,String comuid) {
        super(context, items, layoutResId);
        this.context = context;
        this.comuid = comuid;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Vilage item) {
        if (!TextUtils.isEmpty(item.shortnm)){
            holder.setText(R.id.tv_jian,item.buldnm+"   "+item.commnm);
        }else {
            holder.setText(R.id.tv_jian,item.shortnm);
        }
        holder.setText(R.id.tv_vilage_name,item.commnm);
        if (TextUtils.isEmpty(comuid)){
            holder.setImageDrawable(R.id.iv_check,context.getResources().getDrawable(R.mipmap.un_check));
        }else {
            if (item.comuid.equals(comuid)){
                holder.setImageDrawable(R.id.iv_check,context.getResources().getDrawable(R.mipmap.check));
            }else {
                holder.setImageDrawable(R.id.iv_check,context.getResources().getDrawable(R.mipmap.un_check));
            }
        }
    }
}