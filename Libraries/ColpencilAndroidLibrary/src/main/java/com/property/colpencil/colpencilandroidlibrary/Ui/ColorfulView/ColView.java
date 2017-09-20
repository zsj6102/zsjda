package com.property.colpencil.colpencilandroidlibrary.Ui.ColorfulView;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.property.colpencil.colpencilandroidlibrary.Function.Tools.UITools;
import com.property.colpencil.colpencilandroidlibrary.R;


/**
 * @Description:错误填充View
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 16/6/23
 */
public class ColView extends AbsColView {
    private ImageView mImg;
    private Button mBt;
    private TextView mText;
    private int mErrorDrawable;
    private int mTempDrawable;
    private CharSequence mErrorStr = "重新加载";
    private CharSequence mEmptyStr = "别处看看";
    private CharSequence mErrorHintStr = "网络错误";
    private CharSequence mEmptyHintStr = "什么都没找到";
    private LinearLayout mTemp;
    private LinearLayout mErrorTemp;
    private ImageView mLoadingTemp;

    public ColView(Context context) {
        super(context);
    }

    @Override
    protected void init() {
        mErrorDrawable = R.mipmap.load_error;
        mTempDrawable = R.mipmap.load_empty;
        mImg = (ImageView) findViewById(R.id.img);
        mBt = (Button) findViewById(R.id.bt);
        mText = (TextView) findViewById(R.id.text);
        mTemp = (LinearLayout) findViewById(R.id.temp);
        mErrorTemp = (LinearLayout) findViewById(R.id.error_temp);
        mLoadingTemp = (ImageView) findViewById(R.id.loading);
        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTempBtClick(v, mType);
            }
        });
        setType(mType);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.layout_error_temp;
    }

    /**
     * 设置距离顶部的高度
     */
    public void setMarginTop(int dp) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, UITools.convertDpToPixel(dp), 0, 0);
        mTemp.setLayoutParams(lp);
        requestLayout();
    }

    /**
     * 关闭加载填充
     */
    public void closeLoading() {
        if (mLoadingTemp != null) {
            mLoadingTemp.setVisibility(GONE);
        }
    }

    /**
     * 设置填充类型
     *
     * @param type {@link #ERROR}, {@link #DATA_NULL}, {@link #LOADING}
     */
    @Override
    public void setType(int type) {
        super.setType(type);
        mLoadingTemp.setVisibility(type == IColView.LOADING ? VISIBLE : GONE);
        mErrorTemp.setVisibility(type == IColView.LOADING ? GONE : VISIBLE);
        requestLayout();
    }

    @Override
    public void onError() {
        mImg.setImageResource(mErrorDrawable);
        mText.setText(mErrorHintStr);
        mBt.setText(mErrorStr);
    }

    @Override
    public void onNull() {
        mImg.setImageResource(mTempDrawable);
        mText.setText(mEmptyHintStr);
        mBt.setText(mEmptyStr);
    }

    @Override
    public void onLoading() {
        AnimationDrawable ad = createLoadingAnim(getContext());
        mLoadingTemp.setImageDrawable(ad);
        ad.start();
        requestLayout();
    }

    public AnimationDrawable createLoadingAnim(Context context) {
        AnimationDrawable ad = new AnimationDrawable();
        ad.addFrame(context.getResources().getDrawable(R.mipmap.icon_refresh_left), 200);
        ad.addFrame(context.getResources().getDrawable(R.mipmap.icon_refresh_center), 200);
        ad.addFrame(context.getResources().getDrawable(R.mipmap.icon_refresh_right), 200);
        ad.setOneShot(false);
        return ad;
    }

}
