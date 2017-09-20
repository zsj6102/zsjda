package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.Building;
import com.colpencil.propertycloud.Bean.Unit;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.CommonAdapter;
import com.colpencil.propertycloud.Tools.CommonViewHolder;

import java.util.List;

public class EstateUnitAdapter extends CommonAdapter<Unit> {

    public EstateUnitAdapter(Context context, List<Unit> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, Unit item, int position) {
        helper.setText(R.id.item_name, item.getUnitName() + "单元");
        if (item.isSelect()) {
            ((TextView) helper.getView(R.id.item_name)).setTextColor(mContext.getResources().getColor(R.color.white));
            helper.getView(R.id.item_select).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.select_city_pink_shape));
        } else {
            ((TextView) helper.getView(R.id.item_name)).setTextColor(mContext.getResources().getColor(R.color.text_dark33));
            helper.getView(R.id.item_select).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.select_city_shape));
        }
    }
}
