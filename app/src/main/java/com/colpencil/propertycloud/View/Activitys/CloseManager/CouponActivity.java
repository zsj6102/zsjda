package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.CouponInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.ApiCloudActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.CouponAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView.loadMoreListener;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xiaohuihui on 2017/1/9.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_commo
)
public class CouponActivity extends ColpencilActivity implements OnClickListener, OnRefreshListener, loadMoreListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_rigth)
    TextView tv_rigth;
    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;
    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.common_listview)
    AutoLoadRecylerView recycler;
    @Bind(R.id.rlEmpty)
    RelativeLayout rlEmpty;
    @Bind(R.id.rlError)
    RelativeLayout rlError;
    @Bind(R.id.imageview)
    ImageView imageview;
    @Bind(R.id.tvEmpty)
    TextView tvEmpty;
    @Bind(R.id.btnReload)
    Button btnReload;

    private int page = 1;
    private int pageSize = 10;
    private List<CouponInfo> mlist = new ArrayList<>();
    private CouponAdapter mAdapter;
    private String url;

    @Override
    protected void initViews(View view) {
        tv_title.setText("我的现金券");
        tv_rigth.setText("使用规则");
        tv_rigth.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);
        btnReload.setVisibility(View.GONE);
        imageview.setImageResource(R.mipmap.no_coupon);
        ll_left.setOnClickListener(this);
        ll_rigth.setOnClickListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(this);
        recycler.setLoadMoreListener(this);
        mAdapter = new CouponAdapter(this, mlist, R.layout.item_coupons);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(mAdapter);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                if (!TextUtils.isEmpty(mlist.get(position).detail_url)) {
                    Intent intent = new Intent(CouponActivity.this, ApiCloudActivity.class);
                    intent.putExtra("startUrl", mlist.get(position).detail_url);
                    startActivity(intent);
                }
            }
        });
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
        } else if (id == R.id.ll_rigth) {
            if (!TextUtils.isEmpty(url)) {
                Intent intent = new Intent(this, ApiCloudActivity.class);
                intent.putExtra("startUrl", url);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onRefresh() {
        mlist.clear();
        page = 1;
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
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadCoupon(params)
                .map(new Func1<ResultListInfo<CouponInfo>, ResultListInfo<CouponInfo>>() {
                    @Override
                    public ResultListInfo<CouponInfo> call(ResultListInfo<CouponInfo> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<CouponInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(ResultListInfo<CouponInfo> result) {
                        url = result.use_rule;
                        mAdapter.setUrl(url);
                        refreshLayout.setRefreshing(false);
                        if (result.code == 0 || result.code == 2) {
                            mlist.addAll(result.data);
                            mAdapter.notifyDataSetChanged();
                            if (mlist.size() > 0) {
                                rlEmpty.setVisibility(View.GONE);
                                rlError.setVisibility(View.GONE);
                                refreshLayout.setVisibility(View.VISIBLE);
                            } else {
                                rlEmpty.setVisibility(View.VISIBLE);
                                rlError.setVisibility(View.GONE);
                                refreshLayout.setVisibility(View.GONE);
                            }
                        } else if (result.code == 3) {
                            startActivity(new Intent(CouponActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            if (mlist.size() > 0) {
                                rlEmpty.setVisibility(View.GONE);
                                rlError.setVisibility(View.GONE);
                                refreshLayout.setVisibility(View.VISIBLE);
                            } else {
                                rlEmpty.setVisibility(View.GONE);
                                rlError.setVisibility(View.VISIBLE);
                                refreshLayout.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }
}
