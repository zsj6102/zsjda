package com.colpencil.tenement.View.Activitys.Workbeach;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.Present.Home.SelectPresente;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.MainActivity;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Adpaters.VillageAdapter;
import com.colpencil.tenement.View.Imples.SelectView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author 汪 亮
 * @Description: 小区选择界面
 * @Email DramaScript@outlook.com
 * @date 2016/8/29
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_select
)
public class SelectActivity extends ColpencilActivity implements SelectView {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_loaction)
    TextView tv_loaction;

    @Bind(R.id.lv_select)
    ListView lv_select;

    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;

    @Bind(R.id.rlEmpty)
    RelativeLayout rlEmpty;

    @Bind(R.id.rlError)
    RelativeLayout rlError;

    @Bind(R.id.ll_show)
    LinearLayout ll_show;

    @Bind(R.id.btnReloadEmpty)
    Button btnReloadEmpty;

    @Bind(R.id.btnReload)
    Button btnReload;

    private VillageAdapter adapter;
    private SelectPresente presente;
    private List<Village> villages = new ArrayList<>();

    private String greet;
    private String loaction;
    private int flag; // 0 从登陆跳转  1 从主页

    @Override
    protected void initViews(View view) {
        flag = getIntent().getIntExtra("flag", 0);
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        rlProgress.setVisibility(View.VISIBLE);
        loaction = SharedPreferencesUtil.getInstance(TenementApplication.getInstance()).getString(StringConfig.PLOTNAME);
        if (!TextUtils.isEmpty(loaction)){
            tv_loaction.setText("当前定位小区："+loaction);
        }
        tv_title.setText("小区选择");
        ll_left.setOnClickListener((v -> {
            finish();
        }));
        presente.loadVillages();
        lv_select.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferencesUtil.getInstance(TenementApplication.getInstance())
                        .setString(StringConfig.PLOTID, villages.get(position).plotId);
                SharedPreferencesUtil.getInstance(TenementApplication.getInstance())
                        .setString(StringConfig.PLOTNAME, villages.get(position).plot);
                SharedPreferencesUtil.getInstance(TenementApplication.getInstance())
                        .setString(StringConfig.PLOTNAME, villages.get(position).plot);
                RxBusMsg msg = new RxBusMsg();
                msg.setType(0);
                msg.setName(villages.get(position).plot);
                msg.setGreet(greet);
                RxBus.get().post(StringConfig.FRESHDATA, msg);
                if (flag==0){
                    startActivity(new Intent(SelectActivity.this, MainActivity.class));
                }else {

                }
                finish();
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presente = new SelectPresente();
        return presente;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadMore(List<Village> result) {
        villages.addAll(result);
        adapter = new VillageAdapter(this, result, R.layout.item_select);
        lv_select.setAdapter(adapter);
    }

    @Override
    public void loadError(String msg) {
        ToastTools.showShort(this,false,msg);
    }

    @Override
    public void loadCode(int code) {
        if (code==0){
            //成功
            ll_show.setVisibility(View.VISIBLE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.GONE);
        }else if (code == 1){
            //失败
            ll_show.setVisibility(View.GONE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.VISIBLE);
            rlEmpty.setVisibility(View.GONE);
            btnReloadEmpty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presente.loadVillages();
                }
            });
        }else if (code == 2){
            //无数据
            ll_show.setVisibility(View.GONE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.VISIBLE);
            btnReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presente.loadVillages();
                }
            });
        }else if (code ==3){
            //未登录
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void loadGreet(String greet) {
        this.greet = greet;
        SharedPreferencesUtil.getInstance(TenementApplication.getInstance()).setString("greet",greet);
    }
}
