package com.colpencil.propertycloud.Present.Home;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.OrderPayInfo;
import com.colpencil.propertycloud.Bean.PayInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Bean.WalletInfo;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.DecimalUtil;
import com.colpencil.propertycloud.Tools.KeyBoard.KeyboardUtil;
import com.colpencil.propertycloud.Tools.KeyBoard.MyKeyBoardView;
import com.colpencil.propertycloud.Tools.Pay.LhjalipayUtils;
import com.colpencil.propertycloud.Tools.WarnDialog;
import com.colpencil.propertycloud.Ui.PasswordView;
import com.colpencil.propertycloud.View.Activitys.CloseManager.ChangePayActivity2;
import com.colpencil.propertycloud.View.Activitys.CloseManager.ForgetPayActivity;
import com.colpencil.propertycloud.View.Activitys.Home.PayActivity;
import com.colpencil.propertycloud.View.Activitys.Home.PaySureActivity;
import com.colpencil.propertycloud.View.Activitys.Home.VilageSelectActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Imples.OrderPayView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.Md5Utils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.orhanobut.dialogplus.DialogPlus.newDialog;

/**
 * @author 汪 亮
 * @Description: 订单支付
 * @Email DramaScript@outlook.com
 * @date 2016/9/6
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_order_pay
)
public class OrderPayActivity extends ColpencilActivity implements View.OnClickListener, OrderPayView {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_paymoney_all)
    TextView tv_paymoney_all;

    @Bind(R.id.rl_zhi)
    RelativeLayout rl_zhi;

    @Bind(R.id.rl_wei)
    RelativeLayout rl_wei;

    @Bind(R.id.rl_yin)
    RelativeLayout rl_yin;

    @Bind(R.id.rl_wallet)
    LinearLayout rl_wallet;

    @Bind(R.id.iv_zhi)
    ImageView iv_zhi;

    @Bind(R.id.iv_wei)
    ImageView iv_wei;

    @Bind(R.id.iv_yin)
    ImageView iv_yin;

    @Bind(R.id.iv_wallet)
    ImageView iv_wallet;

    @Bind(R.id.tv_pay)
    TextView tv_pay;

    @Bind(R.id.divide)
    View divide;

    @Bind(R.id.not_enough_money)
    TextView not_enough_money;

    private OrderPayPresent present;
    private String ordersn;
    private double totalAmount;

    private int pay = 0;
    private int type;
    private Observable<RxBusMsg> paySub;

    private DialogPlus dialogPlus;
    private WalletInfo walletInfo;
    private PasswordView passwordView;
    private MyKeyBoardView keyBoardView;
    private KeyboardUtil util;

    @Override
    protected void initViews(View view) {
        type = getIntent().getIntExtra("type", 0);
        ordersn = getIntent().getStringExtra("ordersn");
        totalAmount = getIntent().getDoubleExtra("totalAmount", 0);
        tv_paymoney_all.setText("￥" + totalAmount);
        tv_title.setText("订单支付");
        rl_zhi.setOnClickListener(this);
        rl_wei.setOnClickListener(this);
        rl_yin.setOnClickListener(this);
        tv_pay.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        rl_wallet.setOnClickListener(this);
        paySub = RxBus.get().register("pay", RxBusMsg.class);
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
                    case 0:// 支付成功
                        MobclickAgent.onEvent(OrderPayActivity.this, "comitPaySuc");
                        switch (type) {
                            case 0:
                                RxBusMsg rMsg = new RxBusMsg();
                                rMsg.setType(4);
                                RxBus.get().post("refreshPay", rMsg);
                                RxBusMsg mRxbusMsg = new RxBusMsg();
                                mRxbusMsg.setType(4);
                                RxBus.get().post(VilageSelectActivity.class.getSimpleName(), mRxbusMsg);
                                mAm.finishActivity(PaySureActivity.class);
                                WarnDialog.showInfoAdd(OrderPayActivity.this, "支付成功");
                                break;
                            case 1:
                                RxBusMsg msg = new RxBusMsg();
                                msg.setType(1);
                                RxBus.get().post("cg", msg);
                                mAm.finishActivity(PaySureActivity.class);
                                WarnDialog.showInfoAdd(OrderPayActivity.this, "支付成功");
                                break;
                            case 2:
                                mAm.finishActivity(PayActivity.class);
                                WarnDialog.showInfoAdd(OrderPayActivity.this, "支付成功");
                                break;
                        }
                        break;
                    case 1:// 支付失败
                        MobclickAgent.onEvent(OrderPayActivity.this, "comitPayFail");
                        ToastTools.showShort(OrderPayActivity.this, false, "支付失败");
                        break;
                    case 2:
                        loadWallet();
                        break;
                }
            }
        };
        paySub.subscribe(subscriber);
        loadWallet();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new OrderPayPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_zhi:
                iv_zhi.setImageDrawable(getResources().getDrawable(R.mipmap.check));
                iv_wei.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                iv_yin.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                iv_wallet.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                pay = 0;
                break;
            case R.id.rl_wei:
                iv_zhi.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                iv_wei.setImageDrawable(getResources().getDrawable(R.mipmap.check));
                iv_yin.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                iv_wallet.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                pay = 1;
                break;
            case R.id.rl_yin:
                iv_zhi.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                iv_wei.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                iv_yin.setImageDrawable(getResources().getDrawable(R.mipmap.check));
                iv_wallet.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                pay = 2;
                break;
            case R.id.rl_wallet:
                iv_zhi.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                iv_wei.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                iv_yin.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                iv_wallet.setImageDrawable(getResources().getDrawable(R.mipmap.check));
                pay = 3;
                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_pay:
                // TODO: 2016/9/6 调起相应的支付方式
                if (walletInfo != null && walletInfo.isPayPassword) {
                    if (pay == 0) {
                        paycode("alipayApp", null);
                    } else {
                        showDialog();
                    }
                } else {
                    if (pay == 0) {
                        paycode("alipayApp", null);
                    } else {
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
                                Intent intent = new Intent(OrderPayActivity.this, ChangePayActivity2.class);
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
                break;
        }
    }

    @Override
    public void getOrderInfo(OrderPayInfo orderPayInfo) {

    }

    private void getOrderInfo(String ordersn, String payMethod, String amount) {
        HashMap<String, String> map = new HashMap<>();
        map.put("ordersn", ordersn);
        map.put("payMethod", payMethod);
        map.put("amount", amount);
        RetrofitManager.getInstance(1, this, HostUrl.BASE_PAY_URL)
                .createApi(HostApi.class)
                .pay(map)
                .map(new Func1<PayInfo, PayInfo>() {
                    @Override
                    public PayInfo call(PayInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PayInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误：" + e.getMessage());
                    }

                    @Override
                    public void onNext(PayInfo resultInfo) {
                        int code = resultInfo.result;
                        String message = resultInfo.message;
                        switch (code) {
                            case 0:
                                ToastTools.showShort(OrderPayActivity.this, false, message);
                                break;
                            case 1:
                                LhjalipayUtils.getInstance(OrderPayActivity.this).pay(resultInfo.data.reStr);
                                ColpencilLogger.e("订单信息：" + resultInfo.data.reStr);
                                break;
                        }
                    }
                });
    }

    public void loadWallet() {
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
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
                        Log.e("error----->", e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo<WalletInfo> result) {
                        if (result.code == 0) {
                            walletInfo = result.data;
                            if (result.data.ableAccount - getIntent().getDoubleExtra("totalAmount", 0.0f) > 0.0001) {      //可以付款
                                iv_zhi.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                                iv_wei.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                                iv_yin.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                                iv_wallet.setImageDrawable(getResources().getDrawable(R.mipmap.check));
                                not_enough_money.setVisibility(View.VISIBLE);
                                iv_wallet.setVisibility(View.VISIBLE);
                                rl_wallet.setClickable(true);
                                not_enough_money.setText("¥" + DecimalUtil.caculateFees(result.data.ableAccount));
                                pay = 3;
                            } else {
                                iv_zhi.setImageDrawable(getResources().getDrawable(R.mipmap.check));
                                iv_wei.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                                iv_yin.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                                iv_wallet.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                                not_enough_money.setVisibility(View.VISIBLE);
                                iv_wallet.setVisibility(View.GONE);
                                not_enough_money.setText("余额不足");
                                rl_wallet.setClickable(false);
                                pay = 0;
                            }
                        } else if (result.code == 3) {
                            startActivity(new Intent(OrderPayActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });
    }

    private void paycode(String payType, String password) {
        if (dialogPlus != null) {
            dialogPlus.dismiss();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("payMethod", payType);
        map.put("amount", totalAmount + "");
        if (type == 2) {
            map.put("sellId", getIntent().getIntExtra("sellId", 0) + "");
            map.put("remark", getIntent().getStringExtra("remark"));
        } else {
            map.put("ordersn", ordersn);
        }
        if (!payType.endsWith("alipayApp")) {
            map.put("payPassword", Md5Utils.encode(password));
        }
        DialogTools.showLoding(this, "提示", "正在付款");
        RetrofitManager.getInstance(1, this, HostUrl.BASE_PAY_URL)
                .createApi(HostApi.class)
                .pay(map)
                .map(new Func1<PayInfo, PayInfo>() {
                    @Override
                    public PayInfo call(PayInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PayInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误：" + e.getMessage());
                    }

                    @Override
                    public void onNext(PayInfo resultInfo) {
                        DialogTools.dissmiss();
                        int code = resultInfo.code;
                        String message = resultInfo.message;
                        if (code == 0) {
                            if (pay == 0) {
                                LhjalipayUtils.getInstance(OrderPayActivity.this).pay(resultInfo.data.reStr);
                            } else {
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("mine", rxBusMsg);
                                if (type == 0) {
                                    RxBusMsg rMsg = new RxBusMsg();
                                    rMsg.setType(4);
                                    RxBus.get().post("refreshPay", rMsg);
                                    RxBusMsg mRxbusMsg = new RxBusMsg();
                                    mRxbusMsg.setType(4);
                                    RxBus.get().post(VilageSelectActivity.class.getSimpleName(), mRxbusMsg);
                                    mAm.finishActivity(PaySureActivity.class);
                                } else if (type == 1) {
                                    RxBusMsg rMsg = new RxBusMsg();
                                    rMsg.setType(0);
                                    RxBus.get().post("cg", rMsg);
                                    mAm.finishActivity(PaySureActivity.class);
                                }
                                WarnDialog.showDetail(OrderPayActivity.this, "付款成功", walletInfo.balancePaymentUrl);
                            }
                        } else if (code == 3) {
                            startActivity(new Intent(OrderPayActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            ToastTools.showShort(OrderPayActivity.this, false, message);
                        }
                    }
                });
    }

    private void showDialog() {
        if (dialogPlus == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.popup_pay, null);
            dialogPlus = newDialog(this)
                    .setContentHolder(new ViewHolder(view))
                    .setCancelable(false)
                    .setGravity(Gravity.BOTTOM)
                    .setExpanded(false)
                    .create();
            view.findViewById(R.id.rl_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogPlus.dismiss();
                }
            });
            TextView tv_forget = (TextView) view.findViewById(R.id.tv_forget);
            tv_forget.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            passwordView = (PasswordView) view.findViewById(R.id.password_view);
            keyBoardView = (MyKeyBoardView) view.findViewById(R.id.keyboard_view);
            util = new KeyboardUtil(this, true, keyBoardView);
            passwordView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    passwordView.setFocusable(true);
                    passwordView.requestFocus();
                    util.attachTo(passwordView);
                }
            });
            util.setOnOkClick(new KeyboardUtil.OnOkClick() {
                @Override
                public void onOkClick() {
                    if (passwordView.mText.toString().length() < 6) {
                        ToastTools.showShort(OrderPayActivity.this, "请输入完成的支付密码");
                    } else {
                        paycode("balancePayment", passwordView.mText.toString());
                    }
                }
            });
            tv_forget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (walletInfo != null && walletInfo.isPayPassword) {
                        Intent intent = new Intent(OrderPayActivity.this, ForgetPayActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(OrderPayActivity.this, ChangePayActivity2.class);
                        intent.putExtra("state", 2);
                        startActivity(intent);
                    }
                }
            });
        }
        util.attachTo(passwordView);
        dialogPlus.show();
    }

}
