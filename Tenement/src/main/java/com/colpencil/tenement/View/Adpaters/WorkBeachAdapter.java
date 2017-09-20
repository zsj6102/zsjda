package com.colpencil.tenement.View.Adpaters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import com.colpencil.tenement.Bean.WorkBeach;
import com.colpencil.tenement.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @Description: 工作台
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/17
 */
public class WorkBeachAdapter extends SuperAdapter<WorkBeach> {

    private Context mcontext;

    public WorkBeachAdapter(Context context, List<WorkBeach> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mcontext = context;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, WorkBeach item) {
        holder.setText(R.id.tv_workbeach,item.name);
        holder.setImageDrawable(R.id.iv_workbeach,mcontext.getResources().getDrawable(item.imageId));
    }
}
