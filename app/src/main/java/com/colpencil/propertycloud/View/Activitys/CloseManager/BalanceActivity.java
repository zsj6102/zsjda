package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.BalanceInfo;
import com.colpencil.propertycloud.Bean.FilterInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Adapter.BalanceAdapter;
import com.colpencil.propertycloud.View.Adapter.FilterAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ScreenUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilGridView;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_common
)
public class BalanceActivity extends ColpencilActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, AutoLoadRecylerView.loadMoreListener {

    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_rigth)
    TextView tv_right;
    @Bind(R.id.ll_rigth)
    RelativeLayout ll_right;
    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.common_listview)
    AutoLoadRecylerView listview;
    @Bind(R.id.divide)
    View divide;

    private int mType;
    private BalanceAdapter adapter;
    private int page = 1;
    private int pageSize = 15;
    private List<FilterInfo> mlist = new ArrayList<>();
    private int str = -1;
    private List<BalanceInfo> balances = new ArrayList<>();
    private PopupWindow window;

    @Override
    protected void initViews(View view) {
        mType = getIntent().getIntExtra("type", 1);
        if (mType == 1) {
            tv_title.setText("可用余额");
        } else if (mType == 2) {
            tv_title.setText("不可用余额");
        } else {
            tv_title.setText("收支明细");
        }
        tv_right.setText("筛选");
        tv_right.setVisibility(View.VISIBLE);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(this);
        listview.setLoadMoreListener(this);
        ll_left.setOnClickListener(this);
        ll_right.setOnClickListener(this);
        adapter = new BalanceAdapter(this, balances, R.layout.item_balance);
        listview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listview.setAdapter(adapter);
        loadFilters();
        loadData();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_left) {
            finish();
        } else if (id == R.id.ll_rigth) {       //筛选
            showPopWindow(divide);
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        balances.clear();
        loadData();
    }

    @Override
    public void onLoadMore() {
        page++;
        loadData();
    }

    private void loadData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("pageNo", page + "");
        params.put("pageSize", pageSize + "");
        if (mType == 1) {
            params.put("listType", "2");
        } else if (mType == 2) {
            params.put("listType", "3");
        } else {
            params.put("listType", "1");
        }
        if (str != -1) {
            params.put("transactionTypes", str + "");
        }
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadBalances(params)
                .map(new Func1<ResultListInfo<BalanceInfo>, ResultListInfo<BalanceInfo>>() {
                    @Override
                    public ResultListInfo<BalanceInfo> call(ResultListInfo<BalanceInfo> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<BalanceInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultListInfo<BalanceInfo> result) {
                        refreshLayout.setRefreshing(false);
                        if (result.code == 0) {
                            balances.addAll(result.data);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    public void loadFilters() {
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadFilter(new HashMap<String, String>())
                .map(new Func1<ResultListInfo<FilterInfo>, ResultListInfo<FilterInfo>>() {
                    @Override
                    public ResultListInfo<FilterInfo> call(ResultListInfo<FilterInfo> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<FilterInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultListInfo<FilterInfo> result) {
                        if (result.code == 0) {
                            mlist.addAll(result.data);
                        }
                    }
                });
    }

    private void showPopWindow(View parent) {
        if (window == null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.popup_filter, null);
            view.findViewById(R.id.viewnull).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            ColpencilGridView listView = (ColpencilGridView) view.findViewById(R.id.gridview);
            final FilterAdapter madapter = new FilterAdapter(this, mlist, R.layout.item_filter_tv);
            listView.setAdapter(madapter);
            window = new PopupWindow(view, ScreenUtil.getInstance().getScreenWidth(this), ScreenUtil.getInstance().getScreenHeight(this));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int i = 0; i < mlist.size(); i++) {
                        if (i == position) {
                            mlist.get(i).isSelect = true;
                            str = mlist.get(i).status_code;
                        } else {
                            mlist.get(i).isSelect = false;
                        }
                    }
                    madapter.notifyDataSetChanged();
                    dismiss();
                    onRefresh();
                }
            });
        }
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAsDropDown(parent, 12, 5);
    }

    public void dismiss() {
        if (window != null) {
            window.dismiss();
        }
    }
}
