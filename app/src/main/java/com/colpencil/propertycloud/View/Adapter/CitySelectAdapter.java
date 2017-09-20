package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.widget.TextView;

import com.colpencil.propertycloud.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

public class CitySelectAdapter extends SuperAdapter<String> {

    public CitySelectAdapter(Context context, List<String> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, String item) {
        TextView textView = holder.getView(R.id.item_name);
        textView.setText(item);
    }
}
