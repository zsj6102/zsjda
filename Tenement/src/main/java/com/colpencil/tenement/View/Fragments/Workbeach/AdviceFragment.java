package com.colpencil.tenement.View.Fragments.Workbeach;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.colpencil.tenement.Bean.Advice;
import com.colpencil.tenement.Bean.AssignMsg;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Bean.VillageBus;
import com.colpencil.tenement.Present.Home.AdivcePresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Adpaters.AdviceAdapter;
import com.colpencil.tenement.View.Imples.AdviceView;
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

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description:
 * @Email DramaScript@outlook.com
 * @date 2016/8/26
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_advice
)
public class AdviceFragment extends ColpencilFragment implements AdviceView, SwipeRefreshLayout.OnRefreshListener, AutoLoadRecylerView.loadMoreListener {

    @Bind(R.id.bg_advice)
    SwipeRefreshLayout bg_advice;

    @Bind(R.id.lv_advice)
    AutoLoadRecylerView lv_advice;

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

    private LinearLayoutManager layoutManager;

    private int mType;
    private int mSelf;
    private int page = 1;
    private int pageSize = 10;

    private AdviceAdapter adapter;
    private AdivcePresent present;
    private List<Advice> advices = new ArrayList<>();
    private String communityId="";
    private Observable<RxBusMsg> register;
    private Observable<VillageBus> advice;
    private Observable<AssignMsg> assignSuccess;

    public static AdviceFragment newInstance(int type) {
        Bundle args = new Bundle();
        AdviceFragment fragment = new AdviceFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        bg_advice.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        mType = getArguments().getInt("type");
//        communityId = SharedPreferencesUtil.getInstance(getActivity()).getString("plotId");
        //初始化下拉刷新控件
        bg_advice.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        bg_advice.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(getActivity());
        lv_advice.setLayoutManager(layoutManager);
        lv_advice.setLoadMoreListener(this);

        adapter = new AdviceAdapter(getActivity(), advices, R.layout.item_advice, mType,1);
        lv_advice.setAdapter(adapter);
        loadData();

        assignSuccess = RxBus.get().register("assignSuccess", AssignMsg.class);
        Subscriber<AssignMsg> booleanSubscriber = new Subscriber<AssignMsg>() {
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
                    ToastTools.showShort(getActivity(), msg.getMessage());
                }
            }
        };
        assignSuccess.subscribe(booleanSubscriber);

        register = RxBus.get().register("stateChange", RxBusMsg.class);
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
                    case 1:
                        onRefresh();
                        break;
                }
            }
        };
        register.subscribe(subscriber);

        advice = RxBus.get().register("repair", VillageBus.class);
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
        advice.subscribe(busSubscriber);

    }

    private void loadData(){
        present.loadAdviceList(communityId,mType,page,pageSize,mSelf);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new AdivcePresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void refresh(ListCommonResult<Advice> list) {
        ColpencilLogger.e("投诉建议的列表："+list.toString());
        if (list.getCode()==2){
            // 数据为空
            bg_advice.setVisibility(View.GONE);
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
        }else {
            bg_advice.setVisibility(View.VISIBLE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.GONE);
        }
        advices.clear();
        advices.addAll(list.getData());
        adapter.notifyDataSetChanged();
        bg_advice.setRefreshing(false);
    }

    @Override
    public void loadMore(ListCommonResult<Advice> list) {
        if (list.getData().size()==0){
        }else {
            advices.addAll(list.getData());
            adapter.notifyDataSetChanged();
        }
        lv_advice.setLoading(false);
    }

    @Override
    public void loadError(String msg) {
        // 加载出错
        bg_advice.setVisibility(View.GONE);
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
        RxBus.get().unregister("stateChange",register);
        RxBus.get().unregister("advice",advice);
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
