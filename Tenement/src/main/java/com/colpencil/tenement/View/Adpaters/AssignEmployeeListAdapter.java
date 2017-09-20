package com.colpencil.tenement.View.Adpaters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.tenement.Bean.Online;
import com.colpencil.tenement.Bean.OnlineListUser;
import com.colpencil.tenement.R;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13.
 */

public class AssignEmployeeListAdapter extends BaseExpandableListAdapter {

    private List<OnlineListUser> assignEmployeeList;

    private Context context;


    public AssignEmployeeListAdapter(List<OnlineListUser> list, Context context){
        this.assignEmployeeList = list;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return assignEmployeeList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return assignEmployeeList.get(i).members.size();
    }

    @Override
    public Object getGroup(int i) {
        return assignEmployeeList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return assignEmployeeList.get(i).members;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder = null;
        if(view == null){
            view = View.inflate(context, R.layout.item_assign, null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tv_assign = (TextView) view.findViewById(R.id.tv_assign);
            groupViewHolder.iv_assign = (ImageView) view.findViewById(R.id.iv_assign);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        if(b){
            groupViewHolder.iv_assign.setImageDrawable(context.getResources().getDrawable(R.mipmap.jian_up));
        } else {
            groupViewHolder.iv_assign.setImageDrawable(context.getResources().getDrawable(R.mipmap.jian_down));
        }
        groupViewHolder.tv_assign.setText(assignEmployeeList.get(i).departmentName);
        return view;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder = null;
        if(view == null){
            view = View.inflate(context, R.layout.item_assign_user, null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tv_assign = (TextView) view.findViewById(R.id.tv_assign_user);
            childViewHolder.btn_assign = (TextView) view.findViewById(R.id.btn_assign_user);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        childViewHolder.tv_assign.setText(assignEmployeeList.get(i).members.get(i1).membername+"("+assignEmployeeList.get(i).members.get(i1).position+")");
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    class GroupViewHolder{
        TextView tv_assign;
        ImageView iv_assign;
    }

    class ChildViewHolder{
        TextView tv_assign;
        TextView btn_assign;
    }
}
