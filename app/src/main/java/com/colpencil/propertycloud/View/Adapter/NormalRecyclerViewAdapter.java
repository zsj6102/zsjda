package com.colpencil.propertycloud.View.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import com.colpencil.propertycloud.Bean.ListEntity;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.ItemViewAnimation;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * 作者:ZengFeng
 * 创建时间:16/6/24 16:31
 * 描述:
 */
public class NormalRecyclerViewAdapter extends BGARecyclerViewAdapter<ListEntity> {

    private int mLastPosition = -1;//动画设置标识
    /**
     * 带有阴影背景的控件
     */
    private CardView listItem;

    public NormalRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_test);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ListEntity model) {
        viewHolderHelper.setText(R.id.tv_name, model.getNickName());
        /**
         *设置动画
         */
        if (position > mLastPosition) {
            ItemViewAnimation.setViewAnimation(viewHolderHelper.getConvertView());
            mLastPosition = position;
        }
    }

    /**
     * 如果要实现Item 项的单击事件，该方法必须填写
     *
     * @param viewHolderHelper
     */
    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.listItem);

    }

}