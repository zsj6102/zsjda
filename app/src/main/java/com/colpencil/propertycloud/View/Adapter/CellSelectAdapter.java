package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.widget.ImageView;

import com.colpencil.propertycloud.Bean.HouseInfo;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.CommonAdapter;
import com.colpencil.propertycloud.Tools.CommonViewHolder;

import java.util.List;

/**
 * @author 汪 亮
 * @Description: 单选哪套房间
 * @Email DramaScript@outlook.com
 * @date 2016/9/13
 */
public class CellSelectAdapter extends CommonAdapter<HouseInfo> {

    private int hourseId;

    public CellSelectAdapter(Context context, List<HouseInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, HouseInfo item, int position) {
        helper.setText(R.id.tv_vilage_name, item.getCommunity_name());
        helper.setText(R.id.tv_jian, item.getBuilding_name() + "栋" + item.getUnit_name() + "单元" + item.getHouse_name() + "号");
        ImageView imageView = helper.getView(R.id.iv_check);
        if (hourseId == item.getHouse_id()) {
            imageView.setImageResource(R.mipmap.fit_check);
        } else {
            imageView.setImageResource(R.mipmap.fit_uncheck);
        }
    }

    public void setComId(int hourseId) {
        this.hourseId = hourseId;
    }
}
