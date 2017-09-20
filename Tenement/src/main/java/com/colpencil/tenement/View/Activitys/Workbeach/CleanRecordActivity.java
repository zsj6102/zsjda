package com.colpencil.tenement.View.Activitys.Workbeach;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.tenement.Bean.CleanRecord;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Present.Home.CleanRecordPresenter;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Ui.BottomDialog;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Adpaters.CleanRecordAdapter;
import com.colpencil.tenement.View.Imples.CleanRecordView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.AutoLoadRecylerView;

import org.feezu.liuli.timeselector.TimeSelector;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author 陈宝
 * @Description: 保洁记录
 * @Email DramaScript@outlook.com
 * @date 2016/8/24
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_common)
public class CleanRecordActivity extends ColpencilActivity implements CleanRecordView, SwipeRefreshLayout.OnRefreshListener, AutoLoadRecylerView.loadMoreListener {

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

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


    private String communityId = "";

    private CleanRecordPresenter presenter;
    private int page = 1;
    private int pageSize = 10;
    private int type;
    private int self;
    private CleanRecordAdapter adapter;
    private List<CleanRecord> records = new ArrayList<>();

    private LinearLayoutManager layoutManager;

    public DialogPlus dialogPlus;

    private RelativeLayout rl_select_villige;
    public TextView tv_village;
    private Button btn_search;
    private ImageView ck_switch;
    private boolean isChecked = false;
    private RelativeLayout rl_select_time;
    private String beginTime = "";
    private String endTime = "";
    private RelativeLayout rl_select_time2;
    private TextView tv_endTime;
    private TextView tv_beginTime;
    private String village;

    @Override
    protected void initViews(View view) {
        setStatusBaropen(false);
        tv_rigth.setVisibility(View.VISIBLE);
        tv_rigth.setText("筛选");
        type = getIntent().getIntExtra(StringConfig.TYPE, 0);
        refreshLayout.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        if (type == 0) {
            tv_title.setText(getString(R.string.cleaning_record));
        } else {
            tv_title.setText(getString(R.string.patrol_record));
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

        adapter = new CleanRecordAdapter(this, records, R.layout.item_green_clean_record, type);
        listView.setAdapter(adapter);
        loadData();

        ll_rigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToDialog();
            }
        });

    }

    private void showToDialog() {
        View view = View.inflate(this, R.layout.dialog_select_clean, null);
        dialogPlus = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(view))
                .setCancelable(true)
                .setGravity(Gravity.TOP)
                .setExpanded(false)
                .create();
        dialogPlus
                .show();
        rl_select_villige = (RelativeLayout) view.findViewById(R.id.rl_select_villige);
        rl_select_time = (RelativeLayout) view.findViewById(R.id.rl_select_time1);
        rl_select_time2 = (RelativeLayout) view.findViewById(R.id.rl_select_time2);
        ck_switch = (ImageView) view.findViewById(R.id.switch_img);
        tv_village = (TextView) view.findViewById(R.id.tv_village);
        tv_village.setText(village);
        tv_beginTime = (TextView) view.findViewById(R.id.tv_beginTime);
        tv_beginTime.setText(beginTime);
//        tv_endTime = (TextView) view.findViewById(R.id.tv_endTime);
//        tv_endTime.setText(endTime);
        btn_search = (Button) view.findViewById(R.id.search_btn);
        rl_select_villige.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogTools.showLoding(CleanRecordActivity.this, "温馨提示", "正在获取全部小区。。。");
                presenter.loadVillage();
            }
        });
        rl_select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeSelector timeSelector = new TimeSelector(CleanRecordActivity.this, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        tv_beginTime.setText(time.substring(0, 10).replace("-", "-"));
                        beginTime = time.substring(0, 10).replace("-", "-");
                    }
                }, "2016-10-15 21:54", "2019-11-29 21:54");
                timeSelector.setMode(TimeSelector.MODE.YMD);
                timeSelector.show();
            }
        });
        //记住当前状态
        if(isChecked){
            ck_switch.setImageDrawable(getResources().getDrawable(R.mipmap.open));
        } else {
            ck_switch.setImageDrawable(getResources().getDrawable(R.mipmap.close));
        }
        ck_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChecked == false){
                    ck_switch.setImageDrawable(getResources().getDrawable(R.mipmap.open));
                    self = 1;
                    isChecked = true;
                } else {
                    ck_switch.setImageDrawable(getResources().getDrawable(R.mipmap.close));
                    self = 0;
                    isChecked = false;
                }
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page=1;
                loadData();
                dialogPlus.dismiss();
            }
        });
    }

    private void loadData() {
        presenter.loadRecord(communityId, type + 1, page, pageSize,beginTime,endTime, self);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CleanRecordPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void refresh(ListCommonResult<CleanRecord> list) {
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
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            refreshLayout.setVisibility(View.VISIBLE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.GONE);
        }
        records.clear();
        records.addAll(list.getData());
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void loadMore(ListCommonResult<CleanRecord> list) {
        if (list.getData().size() == 0) {
        } else {
            records.addAll(list.getData());
            adapter.notifyDataSetChanged();
            listView.setLoading(false);
        }

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

    @Override
    public void loadVillageError(String msg) {
        DialogTools.dissmiss();
        ToastTools.showShort(this,false,"请求失败！");
    }

    @Override
    public void loadCommunity(ListCommonResult<Village> result) {
        DialogTools.dissmiss();
        int code = result.getCode();
        String message = result.getMessage();
        switch (code) {
            case 0:
                List<String> villageList = new ArrayList<>();
                for (Village village : result.getData()) {
                    villageList.add(village.shortName);
                }
                new BottomDialog.Builder(this)
                        .setTitle("选择小区")
                        .setDataList(villageList)
                        .setPositiveText("确认")
                        .setNegativeText("取消")
                        .onPositive(dialog -> {
                            communityId = result.getData().get(dialog.position).plotId;
                            village = villageList.get(dialog.position);
                            tv_village.setText(villageList.get(dialog.position));
                        })
                        .onNegative(dialog -> {

                        }).show();
                break;
            case 1:
                ToastTools.showShort(this,false,message );
                break;
            case 2:
                ToastTools.showShort(this,false,message );
                break;
            case 3:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }


    @OnClick(R.id.ll_left)
    void backOnClick() {
        finish();
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
