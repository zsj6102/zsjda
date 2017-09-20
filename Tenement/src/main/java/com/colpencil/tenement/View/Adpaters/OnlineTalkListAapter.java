package com.colpencil.tenement.View.Adpaters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.tenement.Bean.OnlineListUser;
import com.colpencil.tenement.R;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.List;

/**
 * @Description: 在线对讲的列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/18
 */
public class OnlineTalkListAapter extends BaseExpandableListAdapter{

    private List<OnlineListUser> onlineListUsers;

    private Context context;

    public OnlineTalkListAapter(List<OnlineListUser> onlineListUsers, Context context) {
        this.onlineListUsers = onlineListUsers;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return onlineListUsers.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return onlineListUsers.get(groupPosition).members.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return onlineListUsers.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return onlineListUsers.get(groupPosition).members;
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (convertView==null){
            convertView = View.inflate(context, R.layout.item_online,null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.iv_online = (ImageView) convertView.findViewById(R.id.iv_online);
            groupViewHolder.tv_online = (TextView) convertView.findViewById(R.id.tv_online);
            convertView.setTag(groupViewHolder);
        }else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        if (isExpanded){
            groupViewHolder.iv_online.setImageDrawable(context.getResources().getDrawable(R.mipmap.jian_up));
        }else {
            groupViewHolder.iv_online.setImageDrawable(context.getResources().getDrawable(R.mipmap.jian_down));
        }
        groupViewHolder.tv_online.setText(onlineListUsers.get(groupPosition).departmentName);
        return convertView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView==null){
            convertView = View.inflate(context,R.layout.item_online_user,null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.iv_online_user = (TextView) convertView.findViewById(R.id.iv_online_user);
            childViewHolder.tv_online_user = (TextView) convertView.findViewById(R.id.tv_online_user);
            convertView.setTag(childViewHolder);
        }else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        String talkname = SharedPreferencesUtil.getInstance(context).getString("talkname");
        if (onlineListUsers.get(groupPosition).members.get(childPosition).username.equals(talkname)){
            childViewHolder.iv_online_user.setBackground(context.getResources().getDrawable(R.mipmap.un_talk));
        }else {
            if (onlineListUsers.get(groupPosition).members.get(childPosition).onlineStatus==0){
                childViewHolder.iv_online_user.setBackground(context.getResources().getDrawable(R.mipmap.un_talk));
            }else {
                childViewHolder.iv_online_user.setBackground(context.getResources().getDrawable(R.mipmap.talk));
            }
        }

        childViewHolder.tv_online_user.setText(onlineListUsers.get(groupPosition).members.get(childPosition).membername+"("+onlineListUsers.get(groupPosition).members.get(childPosition).position+")");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder{
        public TextView tv_online;
        public ImageView iv_online;
    }

    class ChildViewHolder{
        public TextView tv_online_user;
        public TextView iv_online_user;
    }

}