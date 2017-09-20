package com.colpencil.tenement.View.Activitys.ToayTask;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Bean.SignInfo;
import com.colpencil.tenement.Bean.SignList;
import com.colpencil.tenement.Present.TodayTask.SignListPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Adpaters.SignListAapter;
import com.colpencil.tenement.View.Imples.SignListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
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
 * @Description: 签到/签退列表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/26
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_common
)
public class SignListActivity extends ColpencilActivity implements SignListView,SwipeRefreshLayout.OnRefreshListener, AutoLoadRecylerView.loadMoreListener {

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

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    private int page = 1;

    private int pageSize = 10;

    private LinearLayoutManager layoutManager;
    private SignListPresent present;

    private List<SignList> lists = new ArrayList<>();
    private SignListAapter aapter;

    @Bind(R.id.tv_qian)
    TextView tv_qian;


    @Override
    protected void initViews(View view) {
        tv_qian.setVisibility(View.VISIBLE);
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        refreshLayout.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        tv_title.setText("考勤记录");
        tv_rigth.setText("签到");
        tv_rigth.setVisibility(View.VISIBLE);
        tv_qian.setVisibility(View.GONE);
        present.getSignState();
        //初始化下拉刷新控件
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        listView.setLoadMoreListener(this);
        aapter = new SignListAapter(this,lists, R.layout.item_sign_list);
        listView.setAdapter(aapter);
        loadData();
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Observable<RxBusMsg> sign = RxBus.get().register("sign", RxBusMsg.class);
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
                        present.getSignState();
                        break;
                    case 2:
                        onRefresh();
                        present.getSignState();
                        break;
                }
            }
        };
        sign.subscribe(subscriber);
    }

    private void loadData(){
        present.getSignList(page,pageSize,0);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new SignListPresent();
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
    public void refresh(ListCommonResult<SignList> list) {
        ColpencilLogger.e("签到列表="+list.toString());
        int code = list.getCode();
        if (code==2){
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
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }else {
            refreshLayout.setVisibility(View.VISIBLE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.GONE);
        }
        lists.clear();
        lists.addAll(list.getData());
        aapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void loadMore(ListCommonResult<SignList> list) {
        if (list.getData().size()==0){
        }else {
            lists.addAll(list.getData());
            aapter.notifyDataSetChanged();
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
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void getSignState(EntityResult<SignInfo> result) {
        ColpencilLogger.e("签到信息："+result.toString());
        int code = result.getCode();
        String message = result.getMessage();
        String status = result.getData().status;
        switch (code){
            case 0:
                if ("0".equals(status)){  // 未签到
                    tv_rigth.setText("签到");
                    tv_qian.setBackground(getResources().getDrawable(R.mipmap.qiandao));
                    tv_qian.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SignListActivity.this, SignInActivity.class);
                            intent.putExtra("signStatus",status);
                            startActivity(intent);
                        }
                    });
                    tv_rigth.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SignListActivity.this, SignInActivity.class);
                            intent.putExtra("signStatus",status);
                            startActivity(intent);
                        }
                    });
                }else if ("1".equals(status)){  // 已签到
                    tv_rigth.setText("签退");
                    tv_qian.setBackground(getResources().getDrawable(R.mipmap.qiantui));
                    tv_qian.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SignListActivity.this, SignInActivity.class);
                            intent.putExtra("signStatus",status);
                            startActivity(intent);
                        }
                    });
                    tv_rigth.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SignListActivity.this, SignInActivity.class);
                            intent.putExtra("signStatus",status);
                            startActivity(intent);
                        }
                    });
                }else {
                    tv_rigth.setText("已签退");
                    tv_qian.setBackground(getResources().getDrawable(R.mipmap.has_qiantui));
                    tv_qian.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SignListActivity.this, SignInActivity.class);
                            intent.putExtra("signStatus",status);
                            startActivity(intent);
                        }
                    });
                    tv_rigth.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SignListActivity.this, SignInActivity.class);
                            intent.putExtra("signStatus",status);
                            startActivity(intent);
                        }
                    });
                }

                SharedPreferencesUtil.getInstance(SignListActivity.this).setString("signStatus", status);

                break;
            case 1:

                break;
            case 3:

                break;
        }
    }
}
