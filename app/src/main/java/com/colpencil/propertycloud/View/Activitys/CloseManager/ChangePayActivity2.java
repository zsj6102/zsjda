package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.KeyBoard.KeyboardUtil;
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
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.HashMap;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_change_paypassword
)
public class ChangePayActivity2 extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.password_view)
    PasswordView passwordView1;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.password_view1)
    PasswordView passwordView2;
    private int mState;

    private DialogPlus dialogPlus;
    private KeyboardUtil keyboardUtil;

    public static Integer State_InitPayPassword=2;
    @Override
    protected void initViews(View view) {
        mState = getIntent().getIntExtra("state", 0);
        if (State_InitPayPassword == mState) {
            tv_title.setText("设置支付密码");
        } else {
            tv_title.setText("修改支付密码");
        }
        tv1.setText("请输入六位支付密码");
        tv2.setText("请重复输一遍您的支付密码");
        ll_left.setOnClickListener(this);
        keyboardUtil = new KeyboardUtil(this, true);
        passwordView1.setOnClickListener(this);
        passwordView2.setOnClickListener(this);
        passwordView1.setListener(new PasswordView.InputFinishListener() {
            @Override
            public void inputFinish() {
                tv1.setVisibility(View.GONE);
                passwordView1.setVisibility(View.GONE);
                tv2.setVisibility(View.VISIBLE);
                passwordView2.setVisibility(View.VISIBLE);
                keyboardUtil.attachTo(passwordView2);
            }
        });
        keyboardUtil.setOnOkClick(new KeyboardUtil.OnOkClick() {
            @Override
            public void onOkClick() {
                if (tv2.getVisibility() == View.VISIBLE) {
                    if (mState == 0 || mState == 2) {
                        changePay();
                    } else {
                        getbackPay();
                    }
                } else {
                    return;
                }
            }
        });
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
        } else if (id == R.id.password_view) {
            keyboardUtil.attachTo(passwordView1);
        } else if (id == R.id.password_view1) {
            keyboardUtil.attachTo(passwordView2);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showView();
        }
        return true;
    }

    private void showView() {
        if (dialogPlus == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_leave, null);
            dialogPlus = DialogPlus.newDialog(this)
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

    private void getbackPay() {
        String pwd1 = passwordView1.getText().toString();
        String pwd2 = passwordView2.getText().toString();
        if (!pwd1.equals(pwd2)) {
            ToastTools.showShort(this, "两次输入的密码不一致");
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("payPassword", Md5Utils.encode(pwd1));
        params.put("flag", 2 + "");
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getbackPay(params)
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
                            RxBusMsg msg = new RxBusMsg();
                            msg.setType(1);
                            RxBus.get().post("refresh", msg);
                            View view = LayoutInflater.from(ChangePayActivity2.this).inflate(R.layout.dialog_input_error, null);
                            final DialogPlus dp = DialogPlus.newDialog(ChangePayActivity2.this)
                                    .setContentHolder(new ViewHolder(view))
                                    .setCancelable(false)
                                    .setGravity(Gravity.CENTER)
                                    .setExpanded(false)
                                    .create();
                            ((TextView) view.findViewById(R.id.tv_message)).setText(resultInfo.message);
                            dp.show();
                            CountDownTimer timer = new CountDownTimer(1000, 2000) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    if (dp != null) {
                                        dp.dismiss();
                                    }
                                    finish();
                                }
                            };
                            timer.start();
                        } else if (resultInfo.code == 3) {
                            startActivity(new Intent(ChangePayActivity2.this, LoginActivity.class));
                            finish();
                        } else {
                            ToastTools.showShort(ChangePayActivity2.this, resultInfo.message);
                        }
                    }
                });
    }

    private void changePay() {
        String pwd1 = passwordView1.mText.toString();
        String pwd2 = passwordView2.mText.toString();
        if (!pwd1.equals(pwd2)) {
            ToastTools.showShort(this, "两次输入的密码不一致");
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("payPassword", Md5Utils.encode(pwd1));
        params.put("flag", 2 + "");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("oldPayPassword"))) {
            params.put("oldPayPassword", Md5Utils.encode(getIntent().getStringExtra("oldPayPassword")));
        }
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
                            RxBusMsg msg = new RxBusMsg();
                            msg.setType(1);
                            RxBus.get().post("refresh", msg);
                            RxBusMsg rxBusMsg = new RxBusMsg();
                            rxBusMsg.setType(2);
                            RxBus.get().post("pay", rxBusMsg);
                            View view = LayoutInflater.from(ChangePayActivity2.this).inflate(R.layout.dialog_input_error, null);
                            final DialogPlus dp = DialogPlus.newDialog(ChangePayActivity2.this)
                                    .setContentHolder(new ViewHolder(view))
                                    .setCancelable(false)
                                    .setGravity(Gravity.CENTER)
                                    .setExpanded(false)
                                    .create();
                            ((TextView) view.findViewById(R.id.tv_message)).setText(resultInfo.message);
                            dp.show();
                            CountDownTimer timer = new CountDownTimer(1000, 2000) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    if (dp != null) {
                                        dp.dismiss();
                                    }
                                    finish();
                                }
                            };
                            timer.start();
                        } else if (resultInfo.code == 3) {
                            startActivity(new Intent(ChangePayActivity2.this, LoginActivity.class));
                            finish();
                        } else {
                            ToastTools.showShort(ChangePayActivity2.this, resultInfo.message);
                        }
                    }
                });
    }

}
