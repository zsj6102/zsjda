package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.EntityResult;
import com.colpencil.propertycloud.Bean.PayFees;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Bean.WalletInfo;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.ApiCloudActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.colpencil.propertycloud.R.id.refreshLayout;
import static com.colpencil.propertycloud.R.id.swipe;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_wallet
)
public class WalletActivity extends ColpencilActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.ll_usable)
    LinearLayout ll_usable;
    @Bind(R.id.ll_unavailable)
    LinearLayout ll_unavailable;
    @Bind(R.id.ll_detail)
    LinearLayout ll_detail;
    @Bind(R.id.ll_change)
    LinearLayout ll_change;
    @Bind(R.id.used_balance)
    TextView used_balance;
    @Bind(R.id.unavaible_balance)
    TextView unavaible_balance;
    @Bind(R.id.total_balance)
    TextView total_balance;
    @Bind(R.id.ll_forget)
    LinearLayout ll_forget;
    @Bind(R.id.ll_content)
    LinearLayout ll_content;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;

    private WalletInfo info;
    private Observable<RxBusMsg> observable;

    @Override
    protected void initViews(View view) {
        setStatecolor(getResources().getColor(R.color.main_blue));
        ll_left.setOnClickListener(this);
        ll_usable.setOnClickListener(this);
        ll_unavailable.setOnClickListener(this);
        ll_detail.setOnClickListener(this);
        ll_change.setOnClickListener(this);
        ll_forget.setOnClickListener(this);
        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipe.setOnRefreshListener(this);
        loadData();
        initBus();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_left) {
            finish();
        } else if (id == R.id.ll_change) {
            if (info != null && info.isPayPassword) {
                Intent intent = new Intent(this, ChangePayActivity.class);
                startActivity(intent);
            } else {
                showDialog();
            }
        } else if (id == R.id.ll_forget) {
            if (info != null & info.isPayPassword) {
                Intent intent = new Intent(this, ForgetPayActivity.class);
                startActivity(intent);
            } else {
                showDialog();
            }
        } else if (id == R.id.ll_usable) {
            if (info != null) {
                Intent intent = new Intent(this, ApiCloudActivity.class);
                intent.putExtra("startUrl", info.ableAccountUrl);
                startActivity(intent);
            }
        } else if (id == R.id.ll_unavailable) {
            if (info != null) {
                Intent intent = new Intent(this, ApiCloudActivity.class);
                intent.putExtra("startUrl", info.disableAccountUrl);
                startActivity(intent);
            }
        } else if (id == R.id.ll_detail) {
            if (info != null) {
                Intent intent = new Intent(this, ApiCloudActivity.class);
                intent.putExtra("startUrl", info.balancePaymentUrl);
                startActivity(intent);
            }
        }
    }

    private void loadData() {
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadmywallet(new HashMap<String, String>())
                .map(new Func1<ResultInfo<WalletInfo>, ResultInfo<WalletInfo>>() {
                    @Override
                    public ResultInfo<WalletInfo> call(ResultInfo<WalletInfo> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<WalletInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        swipe.setRefreshing(false);
                    }

                    @Override
                    public void onNext(ResultInfo<WalletInfo> result) {
                        swipe.setRefreshing(false);
                        if (result.code == 0 || result.code == 2) {
                            if (result.code == 0) {
                                info = result.data;
                                if (result.data.ableAccount - 0.00 > 0) {
                                    used_balance.setText("￥" + caculateFees(result.data.ableAccount));
                                } else {
                                    used_balance.setText("￥0.00");
                                }
                                unavaible_balance.setText("￥" + caculateFees(result.data.disableAccount));
                                total_balance.setText("￥" + caculateFees(result.data.totalAccount));
                            }
                        } else if (result.code == 3) {
                            Intent intent = new Intent(WalletActivity.this, LoginActivity.class);
                            intent.putExtra("requestLogin", 100);
                            startActivityForResult(intent, 1);
                        } else {
                            ToastTools.showShort(WalletActivity.this, result.message);
                        }
                    }
                });
    }

    private void initBus() {
        observable = RxBus.get().register("refresh", RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg msg) {
                if (msg.getType() == 1) {
                    loadData();
                }
            }
        };
        observable.subscribe(subscriber);
    }

    /**
     * 获取某项的总金额
     *
     * @param fees
     * @return
     */
    private String caculateFees(double fees) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(fees);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("refresh", observable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            loadData();
        }
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    private void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_set_paypwd, null);
        final DialogPlus firstDialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(view))
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setExpanded(false)
                .create();
        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WalletActivity.this, ChangePayActivity2.class);
                intent.putExtra("state", 2);
                intent.putExtra("isPayPassword", 0);
                startActivity(intent);
                firstDialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstDialog.dismiss();
            }
        });
        firstDialog.show();
    }
}
