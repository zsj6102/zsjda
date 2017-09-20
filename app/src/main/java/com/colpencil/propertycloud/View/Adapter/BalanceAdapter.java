package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;

import com.colpencil.propertycloud.Bean.BalanceInfo;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.DecimalUtil;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

public class BalanceAdapter extends SuperAdapter<BalanceInfo> {

    public BalanceAdapter(Context context, List<BalanceInfo> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, BalanceInfo item) {
        holder.setText(R.id.item_name, item.transaction_type);
        if (item.type_code == 0) {
            holder.setText(R.id.item_money1, "+￥" + DecimalUtil.caculateFees(item.amount));
            holder.setTextColor(R.id.item_money1, mContext.getResources().getColor(R.color.main_red));
        } else {
            holder.setText(R.id.item_money1, "-￥" + DecimalUtil.caculateFees(item.amount));
            holder.setTextColor(R.id.item_money1, mContext.getResources().getColor(R.color.main_blue));
        }
        holder.setText(R.id.item_time, item.create_time);
        holder.setText(R.id.item_money2, "余额：￥" + DecimalUtil.caculateFees(item.account_amount));
    }

}
