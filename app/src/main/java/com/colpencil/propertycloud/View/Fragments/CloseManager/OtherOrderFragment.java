package com.colpencil.propertycloud.View.Fragments.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.colpencil.propertycloud.Bean.ListBean;
import com.colpencil.propertycloud.Bean.OtherOrder;
import com.colpencil.propertycloud.Present.CloseManager.OtherOrderPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.OtherOrderAdapter;
import com.colpencil.propertycloud.View.Imples.OtherOrderView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @Description: 其他订单
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/8
 */
@ActivityFragmentInject(
        contentViewId = R.layout.refrash_layout
)
public class OtherOrderFragment extends ColpencilFragment implements OtherOrderView, SwipeRefreshLayout.OnRefreshListener, AutoLoadRecylerView.loadMoreListener {

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

    private OtherOrderPresent present;

    private int page = 1;

    private int pageSize = 10;

    private LinearLayoutManager layoutManager;

    private List<OtherOrder> lists = new ArrayList<>();
    private OtherOrderAdapter adapter;

    @Override
    protected void initViews(View view) {
        refreshLayout.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        //初始化下拉刷新控件
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(layoutManager);
        listView.setLoadMoreListener(this);

        adapter = new OtherOrderAdapter(getActivity(),lists, R.layout.item_other_order);
        listView.setAdapter(adapter);
        loadData();
    }

    private void loadData(){
        present.getOrderList(page,pageSize);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new OtherOrderPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void refresh(ListBean<OtherOrder> list) {
        int code = list.code;
        if (list.data.size()==0){
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
        }else if (code==3){
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }else {
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
    public void loadMore(ListBean<OtherOrder> list) {
        if (list.data.size()==0){
            ToastTools.showShort(getActivity(),"没有更多数据了");
        }else {
            lists.addAll(list.data);
        }
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
        ToastTools.showShort(getActivity(), msg);
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

}
