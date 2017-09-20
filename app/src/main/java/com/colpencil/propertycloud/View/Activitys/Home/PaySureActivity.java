package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.CorfimPayFees;
import com.colpencil.propertycloud.Bean.PaySure;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.Present.Home.OrderPayActivity;
import com.colpencil.propertycloud.Present.Home.PaySurePresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.PaysureAdapter;
import com.colpencil.propertycloud.View.Imples.PaySureView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilListView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 缴费确认
 * @Email DramaScript@outlook.com
 * @date 2016/9/6
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_paysure
)
public class PaySureActivity extends ColpencilActivity implements PaySureView {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.lv_paysure)
    ColpencilListView lv_paysure;

    @Bind(R.id.tv_gopay)
    TextView tv_gopay;

    private PaysureAdapter adapter;
    private PaySurePresent present;
    private String billIds;

    private List<CorfimPayFees> corfimPayFeesList = new ArrayList<>();
    private TextView tv_paymoney_all;
    private int type;// 0:缴费助手   1 装修管理，押金费

    @Override
    protected void initViews(View view) {
        type = getIntent().getIntExtra("type", 0);
        billIds = getIntent().getStringExtra("billIds");
        tv_title.setText("缴费确认");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        View foot = View.inflate(this, R.layout.item_paysure_all, null);
        tv_paymoney_all = (TextView) foot.findViewById(R.id.tv_paymoney_all);
        lv_paysure.addFooterView(foot);

        getPayList();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new PaySurePresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        if (mPresenter == null)
            return;
        ((PaySurePresent) mPresenter).getPay();
    }

    @Override
    public void paySure(List<PaySure> paySureList) {

    }

    private void getPayList() {
        HashMap<String, String> map = new HashMap<>();
        map.put("billIds", billIds);
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .confirmPayment(map)
                .map(new Func1<ResultListInfo<CorfimPayFees>, ResultListInfo<CorfimPayFees>>() {
                    @Override
                    public ResultListInfo<CorfimPayFees> call(ResultListInfo<CorfimPayFees> corfimPayFeesResultListInfo) {
                        return corfimPayFeesResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<CorfimPayFees>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误：" + e.getMessage());
                    }

                    @Override
                    public void onNext(final ResultListInfo<CorfimPayFees> corfimPayFeesResultListInfo) {
                        int code = corfimPayFeesResultListInfo.code;
                        String message = corfimPayFeesResultListInfo.message;
                        switch (code) {
                            case 0:
                                corfimPayFeesList.addAll(corfimPayFeesResultListInfo.data);
                                tv_paymoney_all.setText("￥" + corfimPayFeesResultListInfo.totalAmount + "");
                                adapter = new PaysureAdapter(PaySureActivity.this, corfimPayFeesResultListInfo.data, R.layout.item_paysure);
                                lv_paysure.setAdapter(adapter);
                                tv_gopay.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        MobclickAgent.onEvent(PaySureActivity.this,"comitPay");
                                        Intent intent = new Intent(PaySureActivity.this, OrderPayActivity.class);
                                        intent.putExtra("ordersn", corfimPayFeesResultListInfo.ordersn);
                                        intent.putExtra("totalAmount", corfimPayFeesResultListInfo.totalAmount);
                                        intent.putExtra("type", type);
                                        startActivity(intent);
                                    }
                                });
                                break;
                            case 1:
                                ToastTools.showShort(PaySureActivity.this, false, message);
                                break;
                            case 3:
                                startActivity(new Intent(PaySureActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

}