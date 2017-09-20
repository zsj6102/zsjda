package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.R;

import java.util.List;

/**
 * @author 汪 亮
 * @Description: 小区选择
 * @Email DramaScript@outlook.com
 * @date 2016/9/9
 */
public class VilageAdapter extends BaseAdapter {

    private Context context;
    private List<CellInfo> mlist;
    private String comuid;

    public VilageAdapter(Context context, List<CellInfo> mlist, String comuid) {
        this.context = context;
        this.mlist = mlist;
        this.comuid = comuid;
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
        MyHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_vilage_select, null);
            holder = new MyHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_vilage_name);
            holder.tv_jian = (TextView) convertView.findViewById(R.id.tv_jian);
            holder.iv_check = (ImageView) convertView.findViewById(R.id.iv_check);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
        holder.tv_name.setText(mlist.get(position).getCommunity_name());
        holder.tv_jian.setText(mlist.get(position).getShortname());
        if (TextUtils.isEmpty(comuid)) {
            holder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_uncheck));
        } else {
            if ((mlist.get(position).getCommunity_id() + "").equals(comuid)) {
                holder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_check));
            } else {
                holder.iv_check.setImageDrawable(context.getResources().getDrawable(R.mipmap.fit_uncheck));
            }
        }
        return convertView;
    }

    class MyHolder {

        TextView tv_name;
        TextView tv_jian;
        ImageView iv_check;

    }

    public void setComuid(String comuid) {
        this.comuid = comuid;
    }

    //    private String comuid;
//
//    public VilageAdapter(Context context, List<CellInfo> items, int layoutResId, String comuid) {
//        super(context, items, layoutResId);
//        this.comuid = comuid;
//    }
//
//    @Override
//    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CellInfo item) {
//        holder.setText(R.id.tv_vilage_name, item.getCommunity_name());
//        holder.setText(R.id.tv_jian, item.getShortName());
//        if (TextUtils.isEmpty(comuid)) {
//            holder.setImageDrawable(R.id.iv_check, mContext.getResources().getDrawable(R.mipmap.un_check));
//        } else {
//            if ((item.getCommunity_id() + "").equals(comuid)) {
//                holder.setImageDrawable(R.id.iv_check, mContext.getResources().getDrawable(R.mipmap.check));
//            } else {
//                holder.setImageDrawable(R.id.iv_check, mContext.getResources().getDrawable(R.mipmap.un_check));
//            }
//        }
//    }
}