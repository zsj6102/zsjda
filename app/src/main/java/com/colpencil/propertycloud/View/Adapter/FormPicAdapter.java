package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.widget.ImageView;

import com.colpencil.propertycloud.R;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ImgTool;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @Description: 装修人员照片
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/14
 */
public class FormPicAdapter extends SuperAdapter<String>{

    private Context context;

    public FormPicAdapter(Context context, List<String> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, String item) {
        ImageView iv_current = holder.getView(R.id.iv_current);
        ImgTool.getImgToolInstance(context).loadImgByString(item,iv_current);
    }
}
