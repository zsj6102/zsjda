package com.colpencil.tenement.View.Fragments.Workbeach;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.colpencil.tenement.Bean.AssignMsg;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.OwnerRepair;
import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Bean.VillageBus;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Present.Home.OwnerRepairPresenter;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Adpaters.ItemRepairAdapter;
import com.colpencil.tenement.View.Imples.OwnerRepairView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 陈宝
 * @Description:业主报修
 * @Email DramaScript@outlook.com
 * @date 2016/8/25
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_common_rqpair)
public class OwnerRepairFragment extends ColpencilFragment implements OwnerRepairView, SwipeRefreshLayout.OnRefreshListener, AutoLoadRecylerView.loadMoreListener {

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

    private ItemRepairAdapter repairAdapter;
    private OwnerRepairPresenter presenter;
    private int page = 1;
    private int pageSize = 10;
    private List<OwnerRepair> dataBeanList = new ArrayList<>();

    private LinearLayoutManager layoutManager;

    private int mType = 0;
    private int mSelf;
    private String communityId="";
    private Observable<RxBusMsg> stateChange;
    private Observable<VillageBus> repair;
    private Observable<AssignMsg> assignSuccess;

    public static OwnerRepairFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(StringConfig.TYPE, type);
        OwnerRepairFragment fragment = new OwnerRepairFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        refreshLayout.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        mType = getArguments().getInt("type");
        //初始化下拉刷新控件
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(layoutManager);
        listView.setLoadMoreListener(this);
//        communityId = SharedPreferencesUtil.getInstance(getActivity()).getString("plotId");
        ColpencilLogger.e("plotId="+communityId);
        repairAdapter = new ItemRepairAdapter(getActivity(), dataBeanList, R.layout.item_owner_repair,mType,0);
        listView.setAdapter(repairAdapter);
        loadData();

        assignSuccess = RxBus.get().register("assignSuccess", AssignMsg.class);
        Subscriber<AssignMsg> subscriberAssign = new Subscriber<AssignMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AssignMsg msg) {
                if(msg.isSuccess()){
                    onRefresh();
                    ToastTools.showShort(getActivity(), msg.getMessage());
                } else {
                    ToastTools.showShort(getActivity(),msg.getMessage());
                }
            }
        };
        assignSuccess.subscribe(subscriberAssign);

        stateChange = RxBus.get().register("stateChange", RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                switch (rxBusMsg.getType()){
                    case 1: // 处理中
                        onRefresh();
                        break;
                    case 2: // 已完成
                        onRefresh();
                        break;
                }
            }
        };
        stateChange.subscribe(subscriber);
        repair = RxBus.get().register("repair", VillageBus.class);
        Subscriber<VillageBus> busSubscriber = new Subscriber<VillageBus>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(VillageBus villageBus) {
                switch (villageBus.getFlag()){
                    case 0:
                        communityId = villageBus.getPlotId();
                        mSelf = villageBus.getTpye();
                        page = 1;
                        loadData();
                        break;
                }
            }
        };
        repair.subscribe(busSubscriber);

    }

    private void loadData(){
        presenter.loadRepairList(communityId,mType, page, pageSize, mSelf);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new OwnerRepairPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }


    @Override
    public void refresh(ListCommonResult<OwnerRepair> list) {
        ColpencilLogger.e("报修订单数据"+mType+1+"="+list.getData().toString());
        if (list.getCode()==2){
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
        }else if (list.getCode()==3){
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        } else {
            refreshLayout.setVisibility(View.VISIBLE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.GONE);
        }
        dataBeanList.clear();
        dataBeanList.addAll(list.getData());
        repairAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void loadMore(ListCommonResult<OwnerRepair> list) {
        if (list.getCode()==2){

        }else {
            dataBeanList.addAll(list.getData());
            repairAdapter.notifyDataSetChanged();
        }
        listView.setLoading(false);
    }

    @Override
    public void loadError(String msg) {
        // 加载出错
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
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("stateChange",stateChange);
    }

    @Override
    public void onRefresh() {
        page=1;
        loadData();
    }

    @Override
    public void onLoadMore() {
        page++;
        loadData();
    }
}
