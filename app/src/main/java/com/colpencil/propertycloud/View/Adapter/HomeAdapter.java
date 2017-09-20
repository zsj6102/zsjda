package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.colpencil.propertycloud.Bean.HomeBean;
import com.colpencil.propertycloud.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yinghe.whiteboardlib.bean.Image;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @Description: 首页的适配器
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/16
 */
public class HomeAdapter extends BaseAdapter {

    private Context context;

    private List<String> lists;

    public HomeAdapter(Context context, List<String> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context,R.layout.item_home,null);
            viewHolder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(lists.get(position), viewHolder.iv_pic);
        return convertView;
    }

    class ViewHolder{
        public ImageView iv_pic;
    }

}
