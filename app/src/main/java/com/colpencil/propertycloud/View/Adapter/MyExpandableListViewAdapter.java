package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.SelectFees;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Ui.CheckableLinearLayout;
import com.colpencil.propertycloud.View.Activitys.CloseManager.PayFeesSelectActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

    public static class ItemInfo {
        public ItemInfo(int groupPosition, int childPosition, String itemContent) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
            this.itemContent = itemContent;
        }

        public int groupPosition;
        public int childPosition;
        public String itemContent;
    }

    private List<SelectFees> selectFees;

    private HashSet<String> checkSet = new HashSet<>();

    private Context context;

    public HashSet<String> getCheckSet() {
        return checkSet;
    }

    public MyExpandableListViewAdapter(Context context, List<SelectFees> selectFees) {
        this.context = context;
        this.selectFees = selectFees;
    }



    @Override
    public int getGroupCount() {
        return selectFees.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return selectFees.get(groupPosition).billList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return selectFees.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return selectFees.get(groupPosition).billList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupViewHoder groupViewHoder;
        if (convertView==null){
            groupViewHoder = new GroupViewHoder();
            convertView = View.inflate(context,R.layout.item_payfees_group,null);
            groupViewHoder.tv_village = (TextView) convertView.findViewById(R.id.tv_village);
            convertView.setTag(groupViewHoder);
        }else {
            groupViewHoder = (GroupViewHoder) convertView.getTag();
        }
        if(groupPosition != 0){
            convertView.setPadding(0,30,0,0);
        }
        groupViewHoder.tv_village.setText(selectFees.get(groupPosition).houseInfo);
        return convertView;
    }



    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        View itemView = convertView;
        final ViewHolder vh;
        if (itemView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.item_payfees_select, null);

            vh = new ViewHolder();
            vh.layout = (CheckableLinearLayout) itemView
                    .findViewById(R.id.layout);
            vh.item = (TextView) itemView.findViewById(R.id.item);
            vh.tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            vh.tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }
        vh.item.setText(selectFees.get(groupPosition).billList.get(childPosition).payitemsnm);
        vh.tv_time.setText(selectFees.get(groupPosition).billList.get(childPosition).payment);
        vh.tv_price.setText("￥"+selectFees.get(groupPosition).billList.get(childPosition).billamout);
        final ExpandableListView listView = ((ExpandableListView) ((PayFeesSelectActivity) context)
                .findViewById(R.id.lv_select));
        final int position = listView.getFlatListPosition(ExpandableListView
                .getPackedPositionForChild(groupPosition, childPosition));
        vh.layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((CheckableLinearLayout) v).toggle();
                listView.setItemChecked(position,
                        ((CheckableLinearLayout) v).isChecked());
                if (((CheckableLinearLayout) v).isChecked()){
                    checkSet.add(selectFees.get(groupPosition).billList.get(childPosition).billid+"");
                    ColpencilLogger.e("------------选中:Size="+checkSet.size());
                }else {
                    checkSet.remove(selectFees.get(groupPosition).billList.get(childPosition).billid+"");
                    ColpencilLogger.e("------------未选中:Size="+checkSet.size());
                }
            }
        });
        return itemView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        Log.d("ExpandableListView",
                "onGroupCollapsed is called, groupPositoin: " + groupPosition);
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        Log.d("ExpandableListView",
                "onGroupExpanded is called, groupPositoin: " + groupPosition);
        super.onGroupExpanded(groupPosition);
    }

    class GroupViewHoder{
        public TextView tv_village;
    }

    private static class ViewHolder {
        TextView item;
        TextView tv_time;
        TextView tv_price;
        CheckableLinearLayout layout;
    }
}
