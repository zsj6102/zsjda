package com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SettingPrefUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.StringUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.UITools;
import com.property.colpencil.colpencilandroidlibrary.R;
import com.property.colpencil.colpencilandroidlibrary.Ui.SwipeBack.SwipeBackActivityBase;
import com.property.colpencil.colpencilandroidlibrary.Ui.SwipeBack.SwipeBackActivityHelper;
import com.property.colpencil.colpencilandroidlibrary.Ui.SwipeBack.SwipeBackLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.SwipeBack.Utils;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * @Description:所有activity的基类
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 16/6/23
 */
public abstract class ColpencilActivity<T extends ColpencilPresenter<ColpencilBaseView>> extends AppCompatActivity
        implements ColpencilBase,ColpencilBaseView,SwipeBackActivityBase {


    /**
     * 主线程id
     */
    private long mUIThreadId;
    //日志的标识
    protected String TAG = "";

    // present基类
    protected ColpencilPresenter mPresenter;
    //基层view对象
    protected View mRootView;

    private int Statecolor;

    private boolean isStatusBaropen;

    public void setStatusBaropen(boolean statusBaropen) {
        isStatusBaropen = statusBaropen;
    }

    /**
     * 第一次点击返回的系统时间
     */
    private long mFirstClickTime = 0;
    protected ColpencilFrame mAm;

    private SwipeBackActivityHelper mHelper;

    /**
     * 布局的id
     */
    protected int mContentViewId;

    public void setStatecolor(int statecolor) {
        Statecolor = statecolor;
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("SplashScreen");
        MobclickAgent.onPause(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        mUIThreadId = android.os.Process.myTid();
        mAm = ColpencilFrame.getInstance();
        mAm.addActivity(this);
        TAG = StringUtil.getClassName(this);
        mPresenter = getPresenter();
        if (mPresenter != null && this instanceof ColpencilBaseView) {
            //绑定view的操作
            mPresenter.attach((ColpencilBaseView) this);
        }
        mRootView = createView(null, null, savedInstanceState);
        setContentView(mRootView);
        //驱动更新view的操作
        bindView(savedInstanceState);
        if (isStatusBaropen==true){
            StatusBarUtil.setTranslucent(this);
            StatusBarUtil.setColor(this,Statecolor);
        }
    }

    @Override protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    @Override public void finish() {
        super.finish();
    }

    @Override public void onResume() {
        super.onResume();
        int mode = SettingPrefUtils.getSwipeBackEdgeMode(this);
        SwipeBackLayout mSwipeBackLayout = mHelper.getSwipeBackLayout();
        switch (mode) {
            case 0:
                mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
                break;
            case 1:
                mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_RIGHT);
                break;
            case 2:
                mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_BOTTOM);
                break;
            case 3:
                mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);
                break;
        }
        MobclickAgent.onPageStart("SplashScreen");
        MobclickAgent.onResume(this);
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
        View view = UITools.inflate(this,mContentViewId);
        ButterKnife.bind(this, view);
        initViews(view);
        return view;
    }

    protected abstract void initViews(View view);

    @Override
    public View getView() {
        return mRootView;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        mUIThreadId = android.os.Process.myTid();
        super.onNewIntent(intent);
    }

    /**
     * 获取UI线程ID
     *
     * @return UI线程ID
     */
    public long getUIThreadId() {
        return mUIThreadId;
    }


    @Override
    protected void onDestroy() {
        ColpencilFrame.getInstance().finishActivity(this);
        if (mPresenter != null && this instanceof ColpencilBaseView) {
            //防止present在activity之后还持有view做一些耗时操作，从而造成内存溢出的后果
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg, View.OnClickListener onClickListener) {

    }

    @Override
    public void showEmpty(String msg, View.OnClickListener onClickListener) {

    }

    @Override
    public void showEmpty(String msg, View.OnClickListener onClickListener, int imageId) {

    }

    @Override
    public void showNetError(View.OnClickListener onClickListener) {

    }

    /**
     * 双击退出，间隔时间为2000ms
     *
     * @return
     */
    public boolean onDoubleClickExit() {
        return onDoubleClickExit(2000);
    }

    /**
     * 退出应用程序
     *
     * @param isBackground 是否开开启后台运行,如果为true则为后台运行
     */
    public void exitApp(Boolean isBackground) {
        mAm.exitApp(isBackground);
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        mAm.exitApp(false);
    }

    /**
     * 双击退出
     */
    private boolean onDoubleClickExit(long timeSpace) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - mFirstClickTime > timeSpace) {
            ToastTools.showShort(this, "再按一次退出");
            mFirstClickTime = currentTimeMillis;
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelp.getInstance().handlePermissionCallback(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PermissionHelp.getInstance().handleSpecialPermissionCallback(this, requestCode, resultCode, data);
    }


}
