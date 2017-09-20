package com.colpencil.propertycloud.Tools;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.R;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * @Description: 单选的装修订单view
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/13
 */
public class SingleFitmentView extends LinearLayout implements Checkable {

    private TextView tv_order;
    private TextView tv_time;
    private TextView tv_lai;
    private TextView tv_applay;
    private ImageView iv_pic;
    private CheckBox mCheckBox;


    public SingleFitmentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public SingleFitmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SingleFitmentView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context){
        // 填充布局
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_fitment_order , this, true);
        tv_order = (TextView) v.findViewById(R.id.tv_order);
        tv_time = (TextView) v.findViewById(R.id.tv_time);
        tv_lai = (TextView) v.findViewById(R.id.tv_lai);
        tv_applay = (TextView) v.findViewById(R.id.tv_applay);
        iv_pic = (ImageView) v.findViewById(R.id.iv_pic);
        mCheckBox = (CheckBox) v.findViewById(R.id.checkbox);
    }

    @Override
    public void setChecked( boolean checked) {
        mCheckBox.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return mCheckBox.isChecked();
    }

    @Override
    public void toggle() {
        mCheckBox.toggle();
    }

    public void setTitle(String text){
        tv_order.setText(text);
    }

    public void setTime(String text){
        tv_time.setText(text);
    }

    public void setLai(String text){
        tv_lai.setText(text);
    }

    public void setApp(String text){
        tv_applay.setText(text);
    }

    public void setUrl(String text){
        ImageLoader.getInstance().displayImage(text,iv_pic);
    }
}