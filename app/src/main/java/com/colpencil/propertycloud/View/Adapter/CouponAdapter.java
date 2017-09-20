package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.colpencil.propertycloud.Bean.CouponInfo;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.DecimalUtil;
import com.colpencil.propertycloud.View.Activitys.CloseManager.TranUserActivity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Created by xiaohuihui on 2017/1/9.
 */
public class CouponAdapter extends SuperAdapter<CouponInfo> {

    private String url;

    public CouponAdapter(Context context, List<CouponInfo> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final CouponInfo item) {
        holder.setText(R.id.tv_name, item.cash_name);
        holder.setText(R.id.tv_money1, "¥" + DecimalUtil.caculateFees(item.account_amount));
        holder.setText(R.id.tv_money2, "本月剩余额度：¥" + DecimalUtil.caculateFees(item.month_amount));
        holder.setOnClickListener(R.id.tv_trans, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TranUserActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("id", item.id);
                mContext.startActivity(intent);
            }
        });
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
