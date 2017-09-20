package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.DecimalUtil;
import com.colpencil.propertycloud.Ui.CheckNumView;
import com.colpencil.propertycloud.Ui.PasswordView;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeCount;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.HashMap;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.colpencil.propertycloud.R.id.ll_rigth;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_forget_pay
)
public class ForgetPayActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.password_view)
    CheckNumView passwordView;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_get)
    TextView tv_get;
    @Bind(R.id.tv_mobile)
    TextView tv_mobile;

    private Observable<RxBusMsg> observable;

    @Override
    protected void initViews(View view) {
        tv_title.setText("找回支付密码");
        ll_left.setOnClickListener(this);
        tv_get.setOnClickListener(this);
        tv_mobile.setText("您的手机号：" + DecimalUtil.strReplacestar(SharedPreferencesUtil.getInstance(this).getString("mobile"), 3, 7));
        passwordView.setListener(new CheckNumView.InputFinishListener() {
            @Override
            public void inputFinish() {
                check();
            }
        });
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
        } else if (id == R.id.tv_get) {
            getcheck();
        }
    }

    private void getcheck() {
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getCode(SharedPreferencesUtil.getInstance(this).getString("mobile"), 8, 0)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultInfo resultInfo) {
                        if (resultInfo.code == 0 || resultInfo.code == 2) {
                            TimeCount count = new TimeCount(60000, 1000, tv_get);
                            count.start();
                        }
                    }
                });
    }

    private void check() {
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", SharedPreferencesUtil.getInstance(this).getString("mobile"));
        params.put("flag", "8");
        params.put("validCd", passwordView.getText().toString());
        params.put("terminal", "0");
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .chkValidCode(params)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultInfo resultInfo) {
                        if (resultInfo.code == 0 || resultInfo.code == 2) {
                            Intent intent = new Intent(ForgetPayActivity.this, ChangePayActivity2.class);
                            intent.putExtra("state", 1);
                            intent.putExtra("validCd", passwordView.getText().toString());
                            startActivity(intent);
                            passwordView.setText("");
                        } else if (resultInfo.code == 3) {
                            startActivity(new Intent(ForgetPayActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            ToastTools.showShort(ForgetPayActivity.this, "验证码输入有误，请重新输入");
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
                    finish();
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("refresh", observable);
    }
}
