package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.colpencil.propertycloud.Bean.GoodsList;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.ApiCloudActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilGridView;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

public class GoodsListAdapter extends SuperAdapter<GoodsList> {

    private Context context;

    private String url;

    public GoodsListAdapter(Context context, List<GoodsList> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final GoodsList item) {
        holder.setText(R.id.tv_classify,item.name);
        ColpencilGridView cl_goods = holder.getView(R.id.cl_goods);
        GoodsAdapter adapter = new GoodsAdapter(context,item.children,R.layout.item_good);
        cl_goods.setAdapter(adapter);
        cl_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, ApiCloudActivity.class);
                intent.putExtra("startUrl", url);
                intent.putExtra("catid", item.children.get(position).cat_id);
                context.startActivity(intent);
                ColpencilLogger.e("--------好货url:"+url+item.children.get(position).cat_id);
            }
        });
    }
}