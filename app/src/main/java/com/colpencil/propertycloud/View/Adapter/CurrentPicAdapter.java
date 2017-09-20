package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.widget.ImageView;

import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ImgTool;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @author 汪 亮
 * @Description: 装修当前进度中的图片
 * @Email DramaScript@outlook.com
 * @date 2016/9/7
 */
public class CurrentPicAdapter extends SuperAdapter<String> {

    private Context context;

    public CurrentPicAdapter(Context context, List<String> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, String item) {
        ImageView iv_current = holder.getView(R.id.iv_current);
        ImageLoader.getInstance().displayImage(item, iv_current);
    }
}
