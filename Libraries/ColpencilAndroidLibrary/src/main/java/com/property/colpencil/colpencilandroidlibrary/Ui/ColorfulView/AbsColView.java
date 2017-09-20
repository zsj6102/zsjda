package com.property.colpencil.colpencilandroidlibrary.Ui.ColorfulView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.StringUtil;

import butterknife.ButterKnife;

/**
 * @Description:抽象的填充类
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 16/6/23
 */
public abstract class AbsColView extends LinearLayout implements IColView {
    private OnColViewClickListener mBtListener;
    private static String TAG;
    protected int mType = ERROR;

    public AbsColView(Context context) {
        this(context, null);
    }

    public AbsColView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        ColpencilLogger.init();
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(setLayoutId(), this);
        ButterKnife.bind(this, view);
        TAG = StringUtil.getClassName(this);
        init();
    }

    protected abstract void init();

    /**
     * 如果界面有按钮，则需要对着按钮的点击事件进行监听
     *
     * @param listener
     */
    public void setBtListener(@NonNull OnColViewClickListener listener) {
        mBtListener = listener;
    }

    protected abstract int setLayoutId();

    /**
     * 将按钮点击事件传递给TempView调用类
     *
     * @param type {@link IColView}
     */
    protected void onTempBtClick(View view, int type) {
        if (mBtListener != null) {
            mBtListener.onBtTempClick(view, type);
        }
    }

    @Override
    public void setType(int type) {
        mType = type;
        if (type == LOADING) {
            onLoading();
            return;
        }
        if (type == ERROR) {
            onError();
        } else if (type == DATA_NULL) {
            onNull();
        } else {
            ColpencilLogger.e(TAG, "类型错误");
        }
    }
}
