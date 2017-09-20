package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.ContentlistEntity;
import com.colpencil.propertycloud.Bean.ListEntity;
import com.colpencil.propertycloud.Bean.TestEntity;
import com.colpencil.propertycloud.R;

import java.util.List;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * <p/>
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p/>
 * 作者：LigthWang
 * <p/>
 * 描述：
 */
public class TestAdapter extends BaseAdapter {

    private List<ListEntity> testBeanList;

    private Context context;

    public TestAdapter(Context context, List<ListEntity> testBeanList) {
        this.context = context;
        this.testBeanList = testBeanList;
    }

    @Override
    public int getCount() {
        return testBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return testBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup view) {
        if(convertView==null){
            convertView=view.inflate(context, R.layout.item_test, null);
        }
        TextView testView= (TextView) convertView.findViewById(R.id.tv_name);
        testView.setText(testBeanList.get(position).getNickName());
        return convertView;
    }
}
