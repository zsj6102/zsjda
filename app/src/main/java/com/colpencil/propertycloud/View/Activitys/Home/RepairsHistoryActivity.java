package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.RepairHistory;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Present.CloseManager.RepairHistoryPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.PlayerUtil;
import com.colpencil.propertycloud.View.Activitys.CloseManager.TenementRepairsActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.RepairHistoryAdapter;
import com.colpencil.propertycloud.View.Imples.RepailHistoryView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
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

/**
 * @author 汪 亮
 * @Description: 历史报修
 * @Email DramaScript@outlook.com
 * @date 2016/10/11
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_commo
)
public class RepairsHistoryActivity extends ColpencilActivity implements RepailHistoryView, SwipeRefreshLayout.OnRefreshListener, AutoLoadRecylerView.loadMoreListener {

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout bg_history;

    @Bind(R.id.common_listview)
    AutoLoadRecylerView lv_histroy_complaint;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

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

    private int page = 1;
    private int pageSzie = 20;

    private LinearLayoutManager layoutManager;

    private List<RepairHistory> lists = new ArrayList<>();
    private RepairHistoryAdapter adapter;
    private RepairHistoryPresent present;
    private Observable<EventBean> register;
    private PlayerUtil util;

    private ImageView imageView;
    private ScaleAnimation animation;

    @Override
    protected void initViews(View view) {

        tv_title.setText("报修记录");

        bg_history.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);

        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getIntExtra("type", 0) != 1) {
                    Intent intent = new Intent(RepairsHistoryActivity.this, TenementRepairsActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });

        //初始化下拉刷新控件
        bg_history.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        bg_history.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this);
        lv_histroy_complaint.setLayoutManager(layoutManager);
        lv_histroy_complaint.setLoadMoreListener(this);

        adapter = new RepairHistoryAdapter(this, lists, R.layout.item_repair_history);
        lv_histroy_complaint.setAdapter(adapter);

        loadData();

        register = RxBus.get().register("feed", EventBean.class);
        Subscriber<EventBean> subscriber = new Subscriber<EventBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(EventBean eventBean) {
                if (eventBean.getFlag() == 0) {
                    onRefresh();
                }
            }
        };
        register.subscribe(subscriber);
        animation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f);
        animation.setDuration(2000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        util = new PlayerUtil();
        adapter.setListener(new RepairHistoryAdapter.PlayListener() {
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
        util.setOnCompleteListener(new PlayerUtil.OnCompleteListener() {
            @Override
            public void complete() {
                imageView.clearAnimation();
            }
        });
    }

    private void loadData() {
        present.getHistory(page, pageSzie);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new RepairHistoryPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void refresh(ResultListInfo<RepairHistory> list) {
        if (list.code == 2) {
            // 数据为空
            bg_history.setVisibility(View.GONE);
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
        } else if (list.code == 3) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            bg_history.setVisibility(View.VISIBLE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.GONE);
        }
        lists.clear();
        lists.addAll(list.data);
        adapter.notifyDataSetChanged();
        bg_history.setRefreshing(false);
    }

    @Override
    public void loadMore(ResultListInfo<RepairHistory> list) {
        lists.addAll(list.data);
        adapter.notifyDataSetChanged();
        lv_histroy_complaint.setLoading(false);
    }

    @Override
    public void loadError(String msg) {
        // 加载出错
        bg_history.setVisibility(View.GONE);
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
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("feed", register);
        if (util.player != null && util.player.isPlaying()) {
            util.stop();
        }
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
                                imageView.clearAnimation();
                            }
                            util.start(file1.getAbsolutePath());
                            imageView.startAnimation(animation);
                        }
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (getIntent().getIntExtra("type", 0) != 1) {
                Intent intent = new Intent(this, TenementRepairsActivity.class);
                startActivity(intent);
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
