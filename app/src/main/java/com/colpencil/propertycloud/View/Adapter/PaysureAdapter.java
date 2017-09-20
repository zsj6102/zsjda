package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;

import com.colpencil.propertycloud.Bean.CorfimPayFees;
import com.colpencil.propertycloud.Bean.PaySure;
import com.colpencil.propertycloud.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @Description: 确认支付
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/6
 */
public class PaysureAdapter extends SuperAdapter<CorfimPayFees> {

    public PaysureAdapter(Context context, List<CorfimPayFees> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CorfimPayFees item) {
        holder.setText(R.id.tv_type,item.payitemsnm);
        holder.setText(R.id.tv_id,item.payment);
        holder.setText(R.id.tv_paymoney,"￥"+item.original_bill_amount);
    }
}
