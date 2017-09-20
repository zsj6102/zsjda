package com.colpencil.tenement.View.Activitys.Workbeach;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Visitor;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Present.Home.VisitorPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Adpaters.VisitorAdapter;
import com.colpencil.tenement.View.Imples.VisitorView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author 汪 亮
 * @Description: 访客管理
 * @Email DramaScript@outlook.com
 * @date 2016/8/25
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_visitor
)
public class VisitorActivity extends ColpencilActivity implements VisitorView, SwipeRefreshLayout.OnRefreshListener, AutoLoadRecylerView.loadMoreListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.lv_visitor)
    AutoLoadRecylerView lv_visitor;

    @Bind(R.id.bg_visitor)
    SwipeRefreshLayout bg_visitor;

    @Bind(R.id.tv_visitor)
    TextView tv_visitor;

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

    private VisitorPresent present;
    private VisitorAdapter adapter;
    private int page = 1;
    private int pageSize = 10;
    private List<Visitor> visitors = new ArrayList<>();
    private String communityId;

    @Override
    protected void initViews(View view) {
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        rlProgress.setVisibility(View.VISIBLE);
        bg_visitor.setVisibility(View.INVISIBLE);
        tv_rigth.setVisibility(View.INVISIBLE);
        tv_title.setText("访客管理");
        tv_rigth.setText("扫码临时卡");
        communityId = SharedPreferencesUtil.getInstance(this).getString(StringConfig.PLOTID);
        //初始化下拉刷新控件
        bg_visitor.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        bg_visitor.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this);
        lv_visitor.setLayoutManager(layoutManager);
        lv_visitor.setLoadMoreListener(this);

        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VisitorActivity.this, VisitorRecodActivity.class));
            }
        });
        adapter = new VisitorAdapter(this, visitors, R.layout.item_visitor);
        lv_visitor.setAdapter(adapter);
        loadData();
    }

    private void loadData(){
        present.getVisitorList(communityId,page, pageSize);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new VisitorPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }


    @Override
    public void refresh(ListCommonResult<Visitor> result) {
        int code = result.getCode();
        if (code==2){
            // 数据为空
            bg_visitor.setVisibility(View.GONE);
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
        }else {
            bg_visitor.setVisibility(View.VISIBLE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.GONE);
        }
        visitors.clear();
        visitors.addAll(result.getData());
        adapter.notifyDataSetChanged();
        bg_visitor.setRefreshing(false);
    }

    @Override
    public void loadMore(ListCommonResult<Visitor> result) {
        if (result.getData().size()==0){
        }else {
            visitors.addAll(result.getData());
            adapter.notifyDataSetChanged();
        }
        lv_visitor.setLoading(false);
    }

    @Override
    public void loadError(String msg) {
        // 加载出错
        bg_visitor.setVisibility(View.GONE);
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
