package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.MenberInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.Present.CloseManager.MenberPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.MenberAdapter;
import com.colpencil.propertycloud.View.Imples.IMenberView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 成员管理
 * @Email DramaScript@outlook.com
 * @date 2016/11/15
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_menber_manger
)
public class MenberManagerActivity extends ColpencilActivity implements View.OnClickListener, IMenberView {

    @Bind(R.id.lv_menber)
    ListView lv_menber;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.tv_ok)
    TextView tv_ok;

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
    private int hseid;

    private List<MenberInfo> mdata = new ArrayList<>();
    private MenberAdapter adapter;
    private Observable<RxBusMsg> register;
    private MenberPresent present;
    private int userType;
    private String info;
    private int usrid = 0;

    @Override
    protected void initViews(View view) {
        hseid = getIntent().getIntExtra("hseid", 0);
        info = getIntent().getStringExtra("info");
        userType = SharedPreferencesUtil.getInstance(this).getInt("userType");
        lv_menber.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        tv_title.setText("成员管理");
        int userType = getIntent().getIntExtra("userType", 0);

        ll_left.setOnClickListener(this);
        ll_rigth.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        ll_rigth.setVisibility(View.INVISIBLE);
        present.loadData(hseid);
        adapter = new MenberAdapter(MenberManagerActivity.this, mdata, R.layout.item_menber);
        lv_menber.setAdapter(adapter);
        register = RxBus.get().register("add", RxBusMsg.class);
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
                        present.loadData(hseid);
                        break;
                }
            }
        };
        register.subscribe(subscriber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("add", register);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new MenberPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_rigth:
//                List<MenberInfo> mlist = new ArrayList<>();
//                for (int i = 0; i < mdata.size(); i++) {
//                    MenberInfo info = mdata.get(i);
//                    if (info.type) {
//                        info.type = false;
//                    } else {
//                        info.type = true;
//                    }
//                    mlist.add(info);
//                }
//                adapter.clear();
//                adapter.addAll(mlist);
//                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_ok:
                Intent intent = new Intent(this, AddMemberBeforeActivity.class);
                intent.putExtra("hseid", hseid);
                intent.putExtra("info", info);
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取服务器信息
     */
    private void loadInfo() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("hseid", hseid + "");
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .memberList(map)
                .map(new Func1<ResultListInfo<MenberInfo>, ResultListInfo<MenberInfo>>() {
                    @Override
                    public ResultListInfo<MenberInfo> call(ResultListInfo<MenberInfo> menberInfoResultListInfo) {
                        return menberInfoResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<MenberInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultListInfo<MenberInfo> result) {
                    }
                });
    }

    @Override
    public void loadResult(ResultListInfo<MenberInfo> result) {
        int code = result.code;
        if (code == 0) {
            List<MenberInfo> data = result.data;
            String memberId = SharedPreferencesUtil.getInstance(this).getString("memberId");
            List<String> listId = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).user_type == 1) {
                    if (memberId.equals(data.get(i).usrid + "")) {
                        usrid = 1;
                    }
                }
            }
            ColpencilLogger.e("memberId=" + memberId + ",usrid=" + usrid);
            if (usrid != 0) { // 说明这个人是业主
                tv_rigth.setVisibility(View.GONE);
                tv_rigth.setText("注销为游客");
                tv_ok.setVisibility(View.VISIBLE);
                adapter.setQuan(true);
            } else {
                tv_rigth.setVisibility(View.GONE);
                tv_ok.setVisibility(View.GONE);
                adapter.setQuan(false);
            }
            if (data.size() == 0) {
                rlProgress.setVisibility(View.GONE);
                rlError.setVisibility(View.GONE);
                rlEmpty.setVisibility(View.VISIBLE);
                btnReload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        present.loadData(hseid);
                    }
                });
            } else {
                lv_menber.setVisibility(View.VISIBLE);
                rlProgress.setVisibility(View.GONE);
                rlError.setVisibility(View.GONE);
                rlEmpty.setVisibility(View.GONE);
            }
            adapter.clear();
            adapter.addAll(data);
            adapter.notifyDataSetChanged();
        } else if (code == 1) {
            lv_menber.setVisibility(View.GONE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.VISIBLE);
            rlEmpty.setVisibility(View.GONE);
            btnReloadEmpty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadInfo();
                    present.loadData(hseid);
                }
            });
        } else if (code == 3) {
            startActivity(new Intent(MenberManagerActivity.this, LoginActivity.class));
        }
    }

}
