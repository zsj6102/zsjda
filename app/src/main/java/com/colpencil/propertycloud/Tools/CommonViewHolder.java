package com.colpencil.propertycloud.Tools;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * @author 陈宝
 * @Description:万能适配器的viewHolder
 * @Email DramaScript@outlook.com
 * @date 2016/7/11
 */
public class CommonViewHolder {
    private final SparseArray<View> mViews;
    private View mConvertView;

    private CommonViewHolder(Context context, ViewGroup parent, int layoutId,
                             int position) {
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        //setTag
        mConvertView.setTag(this);


    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static CommonViewHolder get(Context context, View convertView,
                                       ViewGroup parent, int layoutId, int position) {

        if (convertView == null) {
            return new CommonViewHolder(context, parent, layoutId, position);
        }
        return (CommonViewHolder) convertView.getTag();
    }


    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public CommonViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView加载图片
     *
     * @param context
     * @param viewId
     * @param url
     * @return
     */
    public CommonViewHolder setImageByUrl(Context context, int viewId, String url) {
        ImageView iv = getView(viewId);
        Glide.with(context).load(url).into(iv);
        return this;
    }

    public CommonViewHolder setImageById(int viewId, int resourceId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resourceId);
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param viewId
     * @param color
     * @return
     */
    public CommonViewHolder setTextColor(int viewId, int color) {
        TextView tv = getView(viewId);
        tv.setTextColor(color);
        return this;
    }

    public CommonViewHolder setBackgroundDrawable(int viewId, Drawable drawable) {
        TextView tv = getView(viewId);
        tv.setBackgroundDrawable(drawable);
        return this;
    }
}
