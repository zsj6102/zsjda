package com.colpencil.propertycloud.View.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.FitProcess;
import com.colpencil.propertycloud.R;

import java.util.List;

public class NewFitmentPrgressAdapter extends BaseAdapter {

    private List<FitProcess> fitProcessList;

    private Activity activity;
    private Intent intent;

    public NewFitmentPrgressAdapter(List<FitProcess> fitProcessList, Activity activity) {
        this.fitProcessList = fitProcessList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return fitProcessList.size();
    }

    @Override
    public Object getItem(int position) {
        return fitProcessList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = View.inflate(activity, R.layout.item_fitment_process,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_position = (TextView) convertView.findViewById(R.id.tv_position);
            viewHolder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            viewHolder.iv_rigth = (ImageView) convertView.findViewById(R.id.iv_rigth);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FitProcess fitProcess = fitProcessList.get(position);
        viewHolder.tv_name.setText(fitProcess.name);
        viewHolder.tv_state.setText(fitProcess.group);
        viewHolder.tv_position.setText((position+1)+"");
        if (position==0){
            viewHolder.tv_name.setTextColor(activity.getResources().getColor(R.color.main_blue));
            viewHolder.tv_state.setTextColor(activity.getResources().getColor(R.color.main_blue));
            viewHolder.tv_position.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.circle_green));
        }else {
            if (fitProcess.status==0){
                viewHolder.tv_name.setTextColor(activity.getResources().getColor(R.color.text_dark99));
                viewHolder.tv_state.setTextColor(activity.getResources().getColor(R.color.text_dark99));
                viewHolder.tv_position.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.circle_gre));
            }else {
                viewHolder.tv_name.setTextColor(activity.getResources().getColor(R.color.main_blue));
                viewHolder.tv_state.setTextColor(activity.getResources().getColor(R.color.main_blue));
                viewHolder.tv_position.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.circle_green));
            }
        }
        if (position==1){
            switch (fitProcessList.get(1).commonStatus){
                case -1: // 待提交

                    break;
                case 0: // 待审核
                    viewHolder.iv_rigth.setVisibility(View.INVISIBLE);
                    break;
                case 1: // 已审核
                    viewHolder.iv_rigth.setVisibility(View.INVISIBLE);
                    break;
                case 2: // 已拒绝

                    break;
            }
        }else if (position==2){
            if (fitProcessList.get(position).wholeprogress<=2){
                viewHolder.iv_rigth.setVisibility(View.VISIBLE);
            }else {
                viewHolder.iv_rigth.setVisibility(View.INVISIBLE);
            }
        }
        return convertView;
    }

    class ViewHolder{

        public TextView tv_name;
        public TextView tv_position;
        public TextView tv_state;
        public ImageView iv_rigth;

    }

}
