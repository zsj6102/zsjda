package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.Province;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Holders.AddressHolder;

import java.util.List;

public class ProvinceAdapter extends BaseAdapter {

    private Context mContext;
    private List<Province> mlist;

    public ProvinceAdapter(Context mContext, List<Province> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddressHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_select_city, null);
            holder = new AddressHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.item_name);
            holder.ll_content = (LinearLayout) convertView.findViewById(R.id.item_select);
            convertView.setTag(holder);
        } else {
            holder = (AddressHolder) convertView.getTag();
        }
        holder.tv_name.setText(mlist.get(position).getLocal_name());
        if (mlist.get(position).isSelect()) {
            holder.tv_name.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.ll_content.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.select_city_pink_shape));
        } else {
            holder.tv_name.setTextColor(mContext.getResources().getColor(R.color.text_dark33));
            holder.ll_content.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.select_city_shape));
        }
        return convertView;
    }

    class MyHolder {

        TextView tv_name;
        LinearLayout ll_content;

    }

}
