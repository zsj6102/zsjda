package com.colpencil.propertycloud.View.Activitys.Test;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.View.Adapter.TestMvpAdapter;
import com.colpencil.propertycloud.Bean.ContentlistEntity;
import com.colpencil.propertycloud.Present.TestMvpPresenter;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Imples.TestMvpView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NetUtils;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColorfulView.IColView;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;


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
 * <p>
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * 作者：LigthWang
 * <p>
 * 描述：测试Mvp框架的activity
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_testmvp
)
public class TestMvpActivity extends ColpencilActivity implements TestMvpView, SwipeRefreshLayout.OnRefreshListener, AutoLoadRecylerView.loadMoreListener {

    @Bind(R.id.record_recycleview)
    AutoLoadRecylerView recordRecycleview;

    @Bind(R.id.joke_refresh_layout)
    SwipeRefreshLayout jokeRefreshLayout;

    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;

    @Bind(R.id.rlError)
    RelativeLayout rlError;

    @Bind(R.id.btnReload)
    Button retryBtn;

    @Bind(R.id.tvError)
    TextView tvError;

    @Bind(R.id.rlEmpty)
    RelativeLayout rlEmpty;

    private LinearLayoutManager layoutManager;
    private int page = 1;
    private List<ContentlistEntity> jokeList;
    private TestMvpAdapter testMvpAdapter;
    private TestMvpPresenter presenter;

    @Override
    protected void initViews(View view) {
        jokeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this);
        recordRecycleview.setLayoutManager(layoutManager);
        recordRecycleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration
                .VERTICAL_LIST));
        recordRecycleview.setLoadMoreListener(this);
        rlProgress.setVisibility(View.VISIBLE);
        initData();
        loadData();
    }

    private void initData() {
        jokeList = new ArrayList<>();
        testMvpAdapter = new TestMvpAdapter(jokeList);
        recordRecycleview.setAdapter(testMvpAdapter);
    }

    private void loadData() {
        presenter.getContent(page);
        page++;
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new TestMvpPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        if (mPresenter == null) return;
        ((TestMvpPresenter) mPresenter).getContent(page);
    }


    @Override
    public void refresh(List<ContentlistEntity> data) {
        rlError.setVisibility(View.GONE);
        rlProgress.setVisibility(View.GONE);
        jokeList.clear();
        jokeList.addAll(data);
        testMvpAdapter.notifyDataSetChanged();
        jokeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadMore(List<ContentlistEntity> data) {
        rlError.setVisibility(View.GONE);
        rlProgress.setVisibility(View.GONE);
        jokeList.addAll(data);
        testMvpAdapter.notifyDataSetChanged();
        recordRecycleview.setLoading(false);
    }

    @Override
    public void showError(String msg, View.OnClickListener onClickListener) {
        super.showError(msg, onClickListener);
        if (NetUtils.isConnected(this) == false) {
            tvError.setText("网络未连接。");
        }
        if (jokeList != null && jokeList.size() > 0) {
            rlError.setVisibility(View.GONE);
        } else {
            rlError.setVisibility(View.VISIBLE);
        }
        rlError.setVisibility(View.VISIBLE);
        rlProgress.setVisibility(View.GONE);
        rlEmpty.setVisibility(View.GONE);
        recordRecycleview.setLoading(false);
        jokeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading(String msg) {
        super.showLoading(msg);
        rlProgress.setVisibility(View.VISIBLE);
        rlEmpty.setVisibility(View.GONE);
        rlError.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();

        rlProgress.setVisibility(View.GONE);
        rlError.setVisibility(View.GONE);
        rlEmpty.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnReload)
    void setRetryBtnonClick() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        page = 1;
        loadData();
    }

    @Override
    public void onLoadMore() {
        loadData();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            boolean is = onDoubleClickExit();
            if (is == true) {
                exitApp(true);
            } else {
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

}
