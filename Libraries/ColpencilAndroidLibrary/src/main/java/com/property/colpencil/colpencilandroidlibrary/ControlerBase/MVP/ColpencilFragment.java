package com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import butterknife.ButterKnife;

/**
 * @Description:所有fragment的基类
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 16/6/23
 */
public abstract class ColpencilFragment<T extends ColpencilPresenter<ColpencilBaseView>> extends Fragment implements ColpencilBase,ColpencilBaseView {

    protected ColpencilPresenter mPresenter;
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mPresenter = getPresenter();
        if (mPresenter != null && this instanceof ColpencilBaseView) {
            //绑定view的操作
            mPresenter.attach((ColpencilBaseView) this);
        }
        super.onCreate(savedInstanceState);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    protected View mRootView;

    /**
     * 布局的id
     */
    protected int mContentViewId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);//避免重复加载view
            }
        } else {
            mRootView = createView(inflater, container, savedInstanceState);
        }
        mContext = mRootView.getContext();
        return mRootView;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getClass().isAnnotationPresent(ActivityFragmentInject.class)) {
            ActivityFragmentInject annotation = getClass()
                    .getAnnotation(ActivityFragmentInject.class);
            mContentViewId = annotation.contentViewId();
        }else {
            throw new RuntimeException(
                    "Class must add annotations of ActivityFragmentInitParams.class");
        }
        View view = inflater.inflate(mContentViewId, container, false);
        ButterKnife.bind(this, view);
        initViews(view);
        return view;
    }

    /**
     * 初始view的操作
     * @param view
     */
    protected abstract void initViews(View view);

    @Nullable
    @Override
    public View getView() {
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindView(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        if (mPresenter != null && this instanceof ColpencilBaseView) {
            //防止present在activity之后还持有view做一些耗时操作，从而造成内存溢出的后果
            mPresenter.detachView();
            mPresenter = null;
        }
        mContext = null;
        super.onDestroy();
    }

    /**
     * 重写，用于显示加载信息的
     * @param msg
     */
    @Override
    public void showLoading(String msg) {

    }

    /**
     * 重写隐藏加载信心
     */
    @Override
    public void hideLoading() {

    }

    /**
     * 重写，显示错误信息，点击事件用于重新加载操作
     * @param msg
     * @param onClickListener
     */
    @Override
    public void showError(String msg, View.OnClickListener onClickListener) {

    }

    /**
     * 重写，当数据为空的界面，点击重新加载
     * @param msg
     * @param onClickListener
     */
    @Override
    public void showEmpty(String msg, View.OnClickListener onClickListener) {

    }

    @Override
    public void showEmpty(String msg, View.OnClickListener onClickListener, int imageId) {

    }

    /**
     * 网络错误显示的界面
     * @param onClickListener
     */
    @Override
    public void showNetError(View.OnClickListener onClickListener) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelp.getInstance().handlePermissionCallback(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PermissionHelp.getInstance().handleSpecialPermissionCallback(getContext(), requestCode, resultCode, data);
    }
}
