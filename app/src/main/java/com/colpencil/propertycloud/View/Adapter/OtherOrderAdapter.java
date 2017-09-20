package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;

import com.colpencil.propertycloud.Bean.OtherOrder;
import com.colpencil.propertycloud.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @Description: 其他订单
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/8
 */
public class OtherOrderAdapter extends SuperAdapter<OtherOrder> {

    private Context context;

    public OtherOrderAdapter(Context context, List<OtherOrder> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, OtherOrder item) {
        holder.setText(R.id.tv_id,item.billid+"");
        holder.setText(R.id.tv_project_name,item.payitmnm);
        holder.setText(R.id.tv_fees,item.amount+"");
    }
}
