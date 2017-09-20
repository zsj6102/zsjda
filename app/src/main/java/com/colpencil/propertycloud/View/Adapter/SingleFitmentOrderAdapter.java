package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.colpencil.propertycloud.Bean.SingleFitOrder;
import com.colpencil.propertycloud.Tools.SingleFitmentView;

import java.util.List;

/**
 * @Description: 装修订单选择
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/23
 */
public class SingleFitmentOrderAdapter extends BaseAdapter {

    private Context context;

    private List<SingleFitOrder> orderList;

    public SingleFitmentOrderAdapter(Context context, List<SingleFitOrder> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SingleFitmentView singleFitmentView = new SingleFitmentView(context);

        return singleFitmentView;
    }
}
