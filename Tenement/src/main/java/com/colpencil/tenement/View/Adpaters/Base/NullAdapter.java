package com.colpencil.tenement.View.Adpaters.Base;

import android.content.Context;

import java.util.List;

public class NullAdapter extends CommonAdapter<String> {

    public NullAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, String item, int position) {

    }
}
