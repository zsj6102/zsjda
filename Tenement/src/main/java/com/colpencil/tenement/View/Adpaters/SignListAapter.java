package com.colpencil.tenement.View.Adpaters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import com.colpencil.tenement.Bean.SignList;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.ToayTask.SignInActivity;
import com.colpencil.tenement.View.Activitys.ToayTask.SignListActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilListView;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @Description: 签到/签退记录
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/26
 */
public class SignListAapter extends SuperAdapter<SignList> {

    private Activity context;

    public SignListAapter(Activity context, List<SignList> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, SignList item) {
        holder.setText(R.id.tv_time,item.date+"");
        int type = 1;
        for (int i=0;i<item.signHistory.size();i++){
            type = item.signHistory.get(i).signType;
        }
        if (type==0){
            holder.setVisibility(R.id.tv_isSign, View.GONE);
        }else { // 这个时候是需要签退
            if (TimeUtil.getTimeStrFromMillisHour(System.currentTimeMillis()).equals(item.date)){
                holder.setVisibility(R.id.tv_isSign, View.GONE);
                holder.setOnClickListener(R.id.tv_isSign, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SignInActivity.class);
                        intent.putExtra("signStatus","1");
                        context.startActivity(intent);
                    }
                });
            }else {
                holder.setVisibility(R.id.tv_isSign, View.GONE);
            }

        }
        ColpencilListView ll_sign = holder.getView(R.id.ll_sign);
        SignListDetailAdpater adpater = new SignListDetailAdpater(context,item.signHistory,R.layout.item_sign_detail);
        ll_sign.setAdapter(adpater);
    }
}
