package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import com.colpencil.propertycloud.Tools.KeyBoard.KeyboardUtil;
import com.colpencil.propertycloud.Tools.SpanUtils;
import com.colpencil.propertycloud.Ui.PasswordView;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.Md5Utils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeCount;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.HashMap;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.orhanobut.dialogplus.DialogPlus.newDialog;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_change_paypassword
)
public class ChangePayActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.ll_rigth)
    RelativeLayout rl_right;
    @Bind(R.id.tv_rigth)
    TextView tv_right;
    @Bind(R.id.password_view)
    PasswordView passwordView1;
    @Bind(R.id.tv1)
    TextView tv1;

    private Observable<RxBusMsg> observable;
    private DialogPlus dialogPlus;
    private View view1;
    private DialogPlus dp;
    private TextView tv_text;

    @Override
    protected void initViews(View view) {
        tv_title.setText("修改支付密码");
        tv_right.setText("忘记密码");
        tv_right.setVisibility(View.VISIBLE);
        ll_left.setOnClickListener(this);
        rl_right.setOnClickListener(this);
        final KeyboardUtil keyboardUtil = new KeyboardUtil(this, true);
        passwordView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardUtil.attachTo(passwordView1);
            }
        });
        keyboardUtil.setOnOkClick(new KeyboardUtil.OnOkClick() {
            @Override
            public void onOkClick() {
                if (passwordView1.mText.length() < 6) {
                    ToastTools.showShort(ChangePayActivity.this, "密码长度必须为6位数字");
                    return;
                }
                changePay();
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
            showView();
        } else if (id == R.id.ll_rigth) {
            Intent intent = new Intent(this, ForgetPayActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void changePay() {
        String pwd1 = passwordView1.mText.toString();
        HashMap<String, String> params = new HashMap<>();
        params.put("oldPayPassword", Md5Utils.encode(pwd1));
        params.put("flag", 1 + "");
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .changePay(params)
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
                            Intent intent = new Intent(ChangePayActivity.this, ChangePayActivity2.class);
                            intent.putExtra("state", 0);
                            intent.putExtra("oldPayPassword", passwordView1.mText.toString());
                            startActivity(intent);
                        } else if (resultInfo.code == 3) {
                            startActivity(new Intent(ChangePayActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            if (dp == null) {
                                view1 = LayoutInflater.from(ChangePayActivity.this).inflate(R.layout.dialog_input_error, null);
                                dp = DialogPlus.newDialog(ChangePayActivity.this)
                                        .setContentHolder(new ViewHolder(view1))
                                        .setCancelable(true)
                                        .setGravity(Gravity.CENTER)
                                        .setExpanded(false)
                                        .create();
                                tv_text = (TextView) view1.findViewById(R.id.tv_message);
                            }
                            if (resultInfo.errorNum > 0) {
                                tv_text.setText(SpanUtils.spanToTextView(ChangePayActivity.this,
                                        "您输入的密码有误，请确认后重新输入。您还有" + resultInfo.errorNum + "次机会。",
                                        21,
                                        22, R.color.main_red));
                            } else {
                                tv_text.setText(SpanUtils.spanToTextView(ChangePayActivity.this,
                                        "由于您今天密码输错超过5次，请明天再试！",
                                        11,
                                        12, R.color.main_red));
                            }
                            dp.show();
                            CountDownTimer timer = new CountDownTimer(1000, 2000) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    if (dp != null) {
                                        dp.dismiss();
                                        passwordView1.setText("");
                                    }
                                }
                            };
                            timer.start();
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

    private void showView() {
        if (dialogPlus == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_leave, null);
            dialogPlus = newDialog(this)
                    .setContentHolder(new ViewHolder(view))
                    .setCancelable(false)
                    .setGravity(Gravity.CENTER)
                    .setExpanded(false)
                    .create();
            view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogPlus.dismiss();
                    ColpencilFrame.getInstance().finishActivity(ChangePayActivity.class);
                    ColpencilFrame.getInstance().finishActivity(ForgetPayActivity.class);
                    finish();
                }
            });
            view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogPlus.dismiss();
                }
            });
        }
        dialogPlus.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showView();
        }
        return true;
    }
}
