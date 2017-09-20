package com.colpencil.tenement.View.Adpaters;

import android.content.Context;

import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @author 汪 亮
 * @Description: 小区选择
 * @Email DramaScript@outlook.com
 * @date 2016/8/29
 */
public class VillageAdapter extends SuperAdapter<Village> {

    private Context context;

    public VillageAdapter(Context context, List<Village> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Village item) {
        holder.setText(R.id.tv_village, item.plot);
    }
}
