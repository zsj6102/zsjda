package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Present.Home.VilageSelectPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.HomeActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.CityPickerActivity;
import com.colpencil.propertycloud.View.Adapter.VilageAdapter;
import com.colpencil.propertycloud.View.Imples.VilageSelectView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author 汪 亮
 * @Description: 小区选择
 * @Email DramaScript@outlook.com
 * @date 2016/9/9
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_village_select
)
public class VilageSelectActivity extends ColpencilActivity implements VilageSelectView {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.lv_normal)
    ListView lv_normal;

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

    private View bottom;

    private VilageAdapter adapter;
    private VilageSelectPresent present;
    private LinearLayoutManager layoutManager;
    private List<CellInfo> vilageList = new ArrayList<>();

    private String memberId;
    private int flag;  // 1是从登陆跳转  2是从主界面跳转进来的
    private int isOnce;
    private String shortName;
    private String cityName;

    @Override
    protected void initViews(View view) {
        setStatecolor(getResources().getColor(R.color.white));
        String comuid = SharedPreferencesUtil.getInstance(this).getString("comuid");
        flag = getIntent().getIntExtra("flag", 0);
        isOnce = getIntent().getIntExtra("isOnce", 0);
        cityName = getIntent().getStringExtra("cityName");
        rlProgress.setVisibility(View.VISIBLE);
        memberId = SharedPreferencesUtil.getInstance(this).getString("memberId");
        tv_title.setText("当前小区");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layoutManager = new LinearLayoutManager(this);
        bottom = LayoutInflater.from(this).inflate(R.layout.village_select_bottom, null);
        lv_normal.addFooterView(bottom);
        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VilageSelectActivity.this, CityPickerActivity.class);
                intent.putExtra("isOnce", 0);
                intent.putExtra("flag", 2);
                startActivity(intent);
            }
        });
        if (SharedPreferencesUtil.getInstance(this).getBoolean("isProprietor", false) && getIntent().getBooleanExtra("isFirstIn", false)) {
            bottom.setVisibility(View.VISIBLE);
        } else {
            bottom.setVisibility(View.GONE);
        }
        adapter = new VilageAdapter(this, vilageList, comuid);
        lv_normal.setAdapter(adapter);
        loadData();
        lv_normal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferencesUtil.getInstance(VilageSelectActivity.this).setString("propertytel", vilageList.get(position).getPropertytel());
                SharedPreferencesUtil.getInstance(VilageSelectActivity.this).setString("servicetel", vilageList.get(position).getServicetel());
                SharedPreferencesUtil.getInstance(VilageSelectActivity.this).setString("shortnm", vilageList.get(position).getShortname());
                SharedPreferencesUtil.getInstance(VilageSelectActivity.this).setString("comuid", vilageList.get(position).getCommunity_id() + "");
                adapter.setComuid(vilageList.get(position).getCommunity_id() + "");
                adapter.notifyDataSetChanged();
                shortName = vilageList.get(position).getShortname();
                if (isOnce == 1) {// 说明是第一次进入这个界面
                    Intent intent = new Intent(VilageSelectActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    mAm.finishAllActivity();
                } else {
                    if (flag == 1) {  // 登陆进来，但是没有选择小区
                        present.select(memberId, vilageList.get(position).getCommunity_id() + "");
                    } else { // 从首页进来
                        if (SharedPreferencesUtil.getInstance(VilageSelectActivity.this).getBoolean("isLogin", false)) {
                            present.select(memberId, vilageList.get(position).getCommunity_id() + "");
                        } else {
                            RxBusMsg msg = new RxBusMsg();
                            msg.setType(2);
                            msg.setMsg(shortName);
                            RxBus.get().post(VilageSelectActivity.this.getClass().getSimpleName(), msg);
                            mAm.finishActivity(CityPickerActivity.class);
                            finish();
                        }
                    }
                }
            }
        });
    }

    private void loadData() {
        if (SharedPreferencesUtil.getInstance(this).getBoolean("isProprietor", false)) {    //业主
            if (getIntent().getBooleanExtra("isFirstIn", false)) {
                present.getOrderCells(true);
            } else {
                present.loadCellByName(cityName);
            }
        } else {
            present.loadCellByName(cityName);
        }
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new VilageSelectPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void refresh(ResultListInfo<CellInfo> result) {
        ColpencilLogger.e("size = " + result.data.size());
        if (result.data.size() == 0) { // TODO: 2016/9/19 数据为空  显示空的界面
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.VISIBLE);
            lv_normal.setVisibility(View.GONE);
            btnReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadData();
                }
            });
        } else {
            // TODO: 2016/9/19 显示成功的界面
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.GONE);
            lv_normal.setVisibility(View.VISIBLE);
        }
        vilageList.clear();
        vilageList.addAll(result.data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadError(String msg) {
        // TODO: 2016/9/19 加载出错
        rlProgress.setVisibility(View.GONE);
        rlError.setVisibility(View.VISIBLE);
        rlEmpty.setVisibility(View.GONE);
        lv_normal.setVisibility(View.GONE);
        btnReloadEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    @Override
    public void select(ResultInfo<String> resultInfo) {
        int code = resultInfo.code;
        String msg = resultInfo.message;
        if (code == 0) {
            if (flag == 1) {    //登录界面进来的
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
                mAm.finishAllActivity();
            } else {    //首页进来的
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(2);
                rxBusMsg.setMsg(shortName);
                RxBus.get().post(VilageSelectActivity.class.getSimpleName(), rxBusMsg);
                finish();
                mAm.finishActivity(CityPickerActivity.class);
                mAm.finishActivity(VilageSelectActivity.class);
            }
        } else {
            ToastTools.showShort(this, msg);
        }
    }
}