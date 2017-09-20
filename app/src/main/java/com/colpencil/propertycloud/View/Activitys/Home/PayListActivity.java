package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.ListBean;
import com.colpencil.propertycloud.Bean.PayList;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Present.Home.PayListPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.PayListAdapter;
import com.colpencil.propertycloud.View.Imples.PayListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 缴费列表
 * @Email DramaScript@outlook.com
 * @date 2016/9/6
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_common1
)
public class PayListActivity extends ColpencilActivity implements PayListView, SwipeRefreshLayout.OnRefreshListener, AutoLoadRecylerView.loadMoreListener {

    @Bind(R.id.common_listview)
    AutoLoadRecylerView listView;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;

    @Bind(R.id.rlEmpty)
    RelativeLayout rlEmpty;

    @Bind(R.id.rlError)
    RelativeLayout rlError;

    @Bind(R.id.btnReloadEmpty)
    Button btnReloadEmpty;

    @Bind(R.id.btnReload)
    Button btnReload;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    private int flag;

    private PayListAdapter adapter;

    private PayListPresent present;

    private int page = 1;

    private int pageSize = 10;

    private LinearLayoutManager layoutManager;

    private List<PayList> lists = new ArrayList<>();
    private String payItemsId;
    private String billIds;
    private Observable<RxBusMsg> observable;

    @Override
    protected void initViews(View view) {
        refreshLayout.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        setStatecolor(getResources().getColor(R.color.white));
        flag = getIntent().getIntExtra("flag", 0);
        payItemsId = getIntent().getStringExtra("payItemsId");
        billIds = getIntent().getStringExtra("billIds");
        switch (flag) {
            case 1:
                tv_title.setText("电费");
                break;
            case 2:
                tv_title.setText("水费");
                break;
            case 3:
                tv_title.setText("物业费");
                break;
            case 4:
                tv_title.setText("垃圾处理费");
                break;
            case 5:
                tv_title.setText("房屋出租费");
                break;
            case 6:
                tv_title.setText("车位出租费");
                break;
            case 7:
                tv_title.setText("充电位租赁费");
                break;
            case 8:
                tv_title.setText("押金管理");
                break;
        }

        //初始化下拉刷新控件
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        listView.setLoadMoreListener(this);

        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (flag == 8) {
            adapter = new PayListAdapter(this, lists, R.layout.item_pay, 15, flag);
        } else {
            adapter = new PayListAdapter(this, lists, R.layout.item_pay, Integer.valueOf(payItemsId), flag);
        }

        listView.setAdapter(adapter);
        loadData();
        observable = RxBus.get().register("refreshPay", RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg msg) {
                if (msg.getType() == 4) {
                    onRefresh();
                }
            }
        };
        observable.subscribe(subscriber);
    }

    private void loadData() {
        present.getList(page, pageSize, payItemsId, billIds);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new PayListPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onRefresh() {
        page = 1;
        loadData();
    }

    @Override
    public void onLoadMore() {
        page++;
        loadData();
    }

    @Override
    public void refresh(ListBean<PayList> list) {
        int code = list.code;
        if (code == 2) {
            // 数据为空
            refreshLayout.setVisibility(View.GONE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.VISIBLE);
            btnReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    page = 1;
                    loadData();
                }
            });
        } else if (code == 3) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            refreshLayout.setVisibility(View.VISIBLE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.GONE);
        }
        lists.clear();
        lists.addAll(list.data);
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void loadMore(ListBean<PayList> list) {
        if (list.data.size() == 0) {
//            ToastTools.showShort(this,"没有更多数据了");
        } else {
            lists.addAll(list.data);
        }
        adapter.notifyDataSetChanged();
        listView.setLoading(false);
    }

    @Override
    public void loadError(String msg) {
        refreshLayout.setVisibility(View.GONE);
        rlProgress.setVisibility(View.GONE);
        rlError.setVisibility(View.VISIBLE);
        rlEmpty.setVisibility(View.GONE);
        btnReloadEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                loadData();
            }
        });
        ToastTools.showShort(this, msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("refreshPay", observable);
    }
}
