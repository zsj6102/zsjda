package com.colpencil.propertycloud.View.Fragments.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.colpencil.propertycloud.Bean.ComplaintHistroy;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Present.Home.HistroyPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.PlayerUtil;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.ComplaintHistoryAdapter;
import com.colpencil.propertycloud.View.Imples.HistroyView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.AnimationUtils;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;
import com.yinghe.whiteboardlib.bean.EventBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import rx.Observable;
import rx.Subscriber;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_complain_history
)
public class ComplainHistoryFragment extends ColpencilFragment implements HistroyView, SwipeRefreshLayout.OnRefreshListener, AutoLoadRecylerView.loadMoreListener {

    @Bind(R.id.bg_normal)
    SwipeRefreshLayout bg_normal;

    @Bind(R.id.lv_normal)
    AutoLoadRecylerView lv_normal;

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
    private HistroyPresent present;
    private int page = 1;
    private int pageSize = 10;
    private ComplaintHistoryAdapter adapter;
    private List<ComplaintHistroy> lists = new ArrayList<>();
    private Observable<EventBean> observable;
    private PlayerUtil util;

    private ImageView imageView;
    private ScaleAnimation animation;

    public static ComplainHistoryFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        ComplainHistoryFragment fragment = new ComplainHistoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        mType = getArguments().getInt("type");
        bg_normal.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        //初始化下拉刷新控件
        bg_normal.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        bg_normal.setOnRefreshListener(this);
        adapter = new ComplaintHistoryAdapter(getActivity(), lists, R.layout.item_complain_history, mType);
        lv_normal.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        lv_normal.setLayoutManager(layoutManager);
        lv_normal.setLoadMoreListener(this);

        observable = RxBus.get().register("feed", EventBean.class);
        Subscriber<EventBean> subscriber = new Subscriber<EventBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EventBean bean) {
                page = 1;
                loadData();
            }
        };
        observable.subscribe(subscriber);
        util = new PlayerUtil();
        animation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f);
        animation.setDuration(2000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        adapter.setListener(new ComplaintHistoryAdapter.PlayerListener() {
            @Override
            public void play(String path, ImageView iv) {
                if (imageView != null) {
                    imageView.clearAnimation();
                }
                imageView = iv;
                download(path);
            }

            @Override
            public void stop() {
                if (imageView != null) {
                    imageView.clearAnimation();
                }
                if (util.player != null && util.player.isPlaying()) {
                    util.stop();
                }
            }
        });
        loadData();
        util.setOnCompleteListener(new PlayerUtil.OnCompleteListener() {
            @Override
            public void complete() {
                imageView.clearAnimation();
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new HistroyPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    private void loadData() {
        present.getList(page, mType, pageSize);
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
    public void refresh(ResultListInfo<ComplaintHistroy> result) {
        int code = result.code;
        if (code == 2) { // TODO: 2016/9/19 数据为空  显示空的界面
            bg_normal.setVisibility(View.GONE);
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
        } else if (code == 0) {
            // TODO: 2016/9/19 显示成功的界面
            bg_normal.setVisibility(View.VISIBLE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.GONE);
        } else if (code == 3) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        lists.clear();
        lists.addAll(result.data);
        adapter.notifyDataSetChanged();
        bg_normal.setRefreshing(false);
    }

    @Override
    public void loadMore(ResultListInfo<ComplaintHistroy> result) {
        if (result.data.size() != 0) {
            lists.addAll(result.data);
        }
        lv_normal.setLoading(false);
    }

    @Override
    public void loadError(String msg) {
        bg_normal.setVisibility(View.GONE);
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

    private void download(String downloadUrl) {
        final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/calls.amr");
        ColpencilLogger.e("路径：" + file.exists());
        if (file.exists()) {
            file.delete();
            ColpencilLogger.e("路径：" + file.exists());
        }
        OkHttpUtils
                .get()
                .url(downloadUrl)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "calls.amr") {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        File file1 = new File(response.getAbsolutePath());
                        if (file1.exists()) {
                            if (util.player != null && util.player.isPlaying()) {
                                util.stop();
                            }
                            util.start(file1.getAbsolutePath());
                            imageView.startAnimation(animation);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (util.player != null && util.player.isPlaying()) {
            util.stop();
        }
    }
}
