package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;

import com.colpencil.propertycloud.Bean.FilterInfo;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.CommonAdapter;
import com.colpencil.propertycloud.Tools.CommonViewHolder;

import java.util.List;

public class FilterAdapter extends CommonAdapter<FilterInfo> {

    public FilterAdapter(Context context, List<FilterInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, FilterInfo item, int position) {
        helper.setText(R.id.tv, item.status_value);
        if (item.isSelect) {
            helper.setTextColor(R.id.tv, mContext.getResources().getColor(R.color.white));
            helper.setBackgroundDrawable(R.id.tv, mContext.getResources().getDrawable(R.drawable.filter_check));
        } else {
            helper.setTextColor(R.id.tv, mContext.getResources().getColor(R.color.text_dark33));
            helper.setBackgroundDrawable(R.id.tv, mContext.getResources().getDrawable(R.drawable.filter_uncheck));
        }
    }
}
