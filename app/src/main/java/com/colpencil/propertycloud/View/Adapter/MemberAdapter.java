package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;

import com.colpencil.propertycloud.Bean.Member;
import com.colpencil.propertycloud.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @Description: 成员管理
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
public class MemberAdapter extends SuperAdapter<Member> {

    private Context context;

    public MemberAdapter(Context context, List<Member> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Member item) {
        holder.setText(R.id.tv_name,item.name);
    }
}
