package com.colpencil.tenement.View.Fragments.TodayTask;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Bean.SignInfo;
import com.colpencil.tenement.Bean.TodayTask.TodayTaskItem;
import com.colpencil.tenement.Bean.TodayTask.TodayTaskItemResult;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Present.TodayTask.TodayTaskPresenter;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.ToayTask.SignInActivity;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Adpaters.TodayTask.TodayTaskItemAdapter;
import com.colpencil.tenement.View.Imples.TodayTaskItemView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 陈宝
 * @Description: 今日任务的子界面
 * @Email DramaScript@outlook.com
 * @date 2016/8/23
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_common)
public class TodayTaskItemFragment extends ColpencilFragment
        implements TodayTaskItemView, SwipeRefreshLayout.OnRefreshListener, AutoLoadRecylerView.loadMoreListener {

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

    @Bind(R.id.tv_qian)
    TextView tv_qian;

    private String communityId;

    private int type;
    private TodayTaskPresenter presenter;
    private TodayTaskItemAdapter adapter;
    private List<TodayTaskItem> taskItemList = new ArrayList<>();
    private int page = 1;
    private int pageSize = 10;
    private LinearLayoutManager layoutManager;
    private Observable<RxBusMsg> msgObservable;

    public static TodayTaskItemFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(StringConfig.TYPE, type);
        TodayTaskItemFragment fragment = new TodayTaskItemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        tv_qian.setVisibility(View.GONE);
        type = getArguments().getInt(StringConfig.TYPE);
        refreshLayout.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        communityId = SharedPreferencesUtil.getInstance(getActivity()).getString(StringConfig.PLOTID);

        //初始化下拉刷新控件
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(layoutManager);
        listView.setLoadMoreListener(this);

        adapter = new TodayTaskItemAdapter(getActivity(), taskItemList, R.layout.item_today_task, type);
        listView.setAdapter(adapter);
        loadData();


        msgObservable = RxBus.get().register("ata", RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                switch (rxBusMsg.getType()) {
                    case 0:
                        onRefresh();
                        break;
                }
            }
        };
        msgObservable.subscribe(subscriber);

    }

    private void loadData() {
        presenter.loadTaskList(communityId, type, page, pageSize);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new TodayTaskPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void refresh(TodayTaskItemResult list) {
        int code = list.getCode();
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
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        } else {
            refreshLayout.setVisibility(View.VISIBLE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.GONE);
        }
        taskItemList.clear();
        taskItemList.addAll(list.getData());
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void loadMore(TodayTaskItemResult list) {
        if (list.getCode()==2){

        }else {
            taskItemList.addAll(list.getData());
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
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void getSignState(EntityResult<SignInfo> result) {
        ColpencilLogger.e("签到信息：" + result.toString());
        int code = result.getCode();
        String message = result.getMessage();
        String status = result.getData().status;
        switch (code) {
            case 0:
                if ("0".equals(status)) {  // 未签订
                    tv_qian.setBackground(getResources().getDrawable(R.mipmap.qiandao));
                    tv_qian.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), SignInActivity.class);
                            intent.putExtra("signStatus", status);
                            startActivity(intent);
                        }
                    });
                } else if ("1".equals(status)) {  // 已签到
                    tv_qian.setBackground(getResources().getDrawable(R.mipmap.qiantui));
                    tv_qian.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), SignInActivity.class);
                            intent.putExtra("signStatus", status);
                            startActivity(intent);
                        }
                    });
                } else {
                    tv_qian.setBackground(getResources().getDrawable(R.mipmap.has_qiantui));
                    tv_qian.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), SignInActivity.class);
                            intent.putExtra("signStatus", status);
                            startActivity(intent);
                        }
                    });
                }

                SharedPreferencesUtil.getInstance(getActivity()).setString("signStatus", status);

                break;
            case 1:
                ToastTools.showShort(getActivity(), false, message);
                break;
            case 3:

                break;
        }
    }

    @Override
    public void taskFinsh(EntityResult entityResult) {
        DialogTools.dissmiss();
        int code = entityResult.getCode();
        String message = entityResult.getMessage();
        switch (code) {
            case 0:
                onRefresh();
                ToastTools.showShort(getActivity(), true, "任务已完成");
                break;
            case 1:
                ToastTools.showShort(getActivity(), false, message);
                break;
            case 3:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
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
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("ata", msgObservable);
    }
}
