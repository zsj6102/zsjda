package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.colpencil.propertycloud.Bean.HouseInfo;
import com.colpencil.propertycloud.Bean.Room;
import com.colpencil.propertycloud.Tools.SingleView;

import java.util.List;

/**
 * @Description: 单选哪套房间
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/13
 */
public class SingleAdapter extends BaseAdapter {

    private List<HouseInfo> list;

    private Context context;

    public SingleAdapter(List<HouseInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SingleView singleView = new SingleView(context);
        HouseInfo houseInfo = list.get(position);
        singleView.setTitle(houseInfo.getCommunity_name()+"  "+houseInfo.getBuilding_name()+"栋"+houseInfo.getUnit_name()+"单元"+houseInfo.getHouse_name());
        return singleView;
    }
}
