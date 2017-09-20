package com.colpencil.propertycloud.View.Fragments.Intimate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.CellState;
import com.colpencil.propertycloud.Bean.Mine;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.WarnDialog;
import com.colpencil.propertycloud.View.Activitys.ApiCloudActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.CouponActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.LiveInfoActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.MineActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.PayFeesSelectActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.SettingActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.WalletActivity;
import com.colpencil.propertycloud.View.Activitys.Home.ComplaintHistroyActivity;
import com.colpencil.propertycloud.View.Activitys.Home.FitmentHomeActivity;
import com.colpencil.propertycloud.View.Activitys.Home.LoadUriActivity;
import com.colpencil.propertycloud.View.Activitys.Home.PayFeesActivity;
import com.colpencil.propertycloud.View.Activitys.Home.RepairsHistoryActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

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
 * @Description: 贴心管家
 * @Email DramaScript@outlook.com
 * @date 2016/11/16
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_intimate
)
public class IntimateFragment extends ColpencilFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    //下拉刷新
    @Bind(R.id.swipe)
    SwipeRefreshLayout bg_normal;
    //头像
    @Bind(R.id.cv_head)
    ImageView cv_head;
    //昵称
    @Bind(R.id.tv_name)
    TextView tv_name;
    //账号
    @Bind(R.id.tv_acout)
    TextView tv_acout;
    //积分
    @Bind(R.id.tv_jifen)
    TextView tv_jifen;
    //装修助手
    @Bind(R.id.ll_zx)
    LinearLayout ll_zx;
    //我的房产
    @Bind(R.id.ll_fan)
    LinearLayout ll_fan;
    //缴费助手
    @Bind(R.id.ll_jiao)
    LinearLayout ll_jiao;
    //我的订单
    @Bind(R.id.ll_order)
    LinearLayout ll_order;
    //家园互通
    @Bind(R.id.rl_child)
    RelativeLayout rl_child;
    //我的设置
    @Bind(R.id.rl_shou)
    RelativeLayout rl_shou;
    //已登录
    @Bind(R.id.rl_mine_show)
    RelativeLayout rl_mine_show;
    //登陆 \ 注册
    @Bind(R.id.tv_unlogin)
    TextView tv_unlogin;
    //我的钱包
    @Bind(R.id.rl_zj)
    LinearLayout rl_zj;
    //我的投诉
    @Bind(R.id.rl_ts)
    RelativeLayout rl_ts;
    //我的报修
    @Bind(R.id.rl_bx)
    RelativeLayout rl_bx;
    //钱包的余额
    @Bind(R.id.tv_balance)
    TextView tv_balance;
    //我的手机钥匙
    @Bind(R.id.rl_key)
    RelativeLayout rl_key;
    //我的现金券
    @Bind(R.id.rl_coupon)
    LinearLayout rl_coupon;
    //我的抵用券
    @Bind(R.id.rl_voucher)
    LinearLayout rl_voucher;
    //我的收藏
    @Bind(R.id.rl_collect)
    RelativeLayout rl_collect;
    //我的积分
    @Bind(R.id.rl_integral)
    RelativeLayout rl_integral;
    //我的优惠券
    @Bind(R.id.rl_youhui)
    RelativeLayout rl_youhui;
    //中奖记录
    @Bind(R.id.rl_zjrecord)
    RelativeLayout rl_zjrecord;

    @Bind(R.id.tv_coupon)
    TextView tv_coupon;

    private Intent intent;
    private Mine data;
    private Observable<RxBusMsg> mine;

    private int mState = 0;
    private ResultInfo<Mine> resultInfos;


    @Override
    protected void initViews(View view) {
        mine = RxBus.get().register("mine", RxBusMsg.class);
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
                        loadInfo();
                        break;
                }
            }
        };
        mine.subscribe(subscriber);
        initListener();
        //初始化下拉刷新控件
        bg_normal.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        bg_normal.setOnRefreshListener(this);
        bg_normal.post(new Runnable() {
            @Override
            public void run() {
                bg_normal.setRefreshing(true);
                onRefresh();
            }
        });
    }

    private void initListener() {
        rl_child.setOnClickListener(this);
        rl_shou.setOnClickListener(this);
        rl_zj.setOnClickListener(this);
        rl_mine_show.setOnClickListener(this);
        ll_zx.setOnClickListener(this);
        ll_fan.setOnClickListener(this);
        ll_jiao.setOnClickListener(this);
        ll_order.setOnClickListener(this);
        tv_unlogin.setOnClickListener(this);
        rl_ts.setOnClickListener(this);
        rl_bx.setOnClickListener(this);
        rl_key.setOnClickListener(this);
        rl_coupon.setOnClickListener(this);
        rl_voucher.setOnClickListener(this);
        rl_collect.setOnClickListener(this);
        rl_integral.setOnClickListener(this);
        rl_youhui.setOnClickListener(this);
        rl_zjrecord.setOnClickListener(this);
    }

    /**
     * 获取个人信息
     */
    private void loadInfo() {
        HashMap<String, String> map = new HashMap<>();
        String mobile = SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).getString("mobile");
        map.put("mobile", mobile);
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getMineInfo(map)
                .map(new Func1<ResultInfo<Mine>, ResultInfo<Mine>>() {
                    @Override
                    public ResultInfo<Mine> call(ResultInfo<Mine> mineResultInfo) {
                        return mineResultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<Mine>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        bg_normal.setRefreshing(false);
                        ColpencilLogger.e("服务器错误信息" + e.getMessage());
                        mState = 1;
                    }

                    @Override
                    public void onNext(ResultInfo<Mine> mineResultInfo) {
                        mState = 0;
                        resultInfos = mineResultInfo;
                        bg_normal.setRefreshing(false);
                        int code = mineResultInfo.code;
                        String message = mineResultInfo.message;
                        switch (code) {
                            case 0:
                                rl_mine_show.setVisibility(View.VISIBLE);
                                tv_unlogin.setVisibility(View.GONE);
                                data = mineResultInfo.data;
                                tv_name.setText(data.nickname);
                                tv_jifen.setText("积分：" + data.point);
                                tv_acout.setText("账号：" + data.mobile);
                                tv_balance.setText("余额 " + data.userAccount);
                                tv_coupon.setText("余额 " + data.user_redaccount);
                                ImageLoader.getInstance().displayImage(data.face, cv_head);
                                break;
                            case 1:
                                ToastTools.showShort(getActivity(), false, message);
                                break;
                            case 3:
                                rl_mine_show.setVisibility(View.GONE);
                                tv_unlogin.setVisibility(View.VISIBLE);
                                break;
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
        switch (v.getId()) {
            case R.id.rl_mine_show: // 我的资料
                intent = new Intent(getActivity(), MineActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_zx: // 装修助手
//                if (mState != 0) {
//                    ToastTools.showShort(getActivity(), "服务器繁忙！");
//                    return;
//                }
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)) {
                    loadState(1);
                } else {
                    Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                    mIntent.putExtra("requestLogin", 100);
                    startActivityForResult(mIntent, 1);
                }
                break;
            case R.id.ll_jiao: // 缴费助手
//                if (mState != 0) {
//                    ToastTools.showShort(getActivity(), "服务器繁忙！");
//                    return;
//                }
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)) {
                    loadState(2);
                } else {
                    Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                    mIntent.putExtra("requestLogin", 100);
                    startActivityForResult(mIntent, 2);
                }
                break;
            case R.id.ll_fan: //  所住小区
//                if (mState != 0) {
//                    ToastTools.showShort(getActivity(), "服务器繁忙！");
//                    return;
//                }
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)) {
                    loadState(3);
                } else {
                    Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                    mIntent.putExtra("requestLogin", 100);
                    startActivityForResult(mIntent, 3);
                }
                break;
            case R.id.ll_order://  我的订单
//                if (mState != 0) {
//                    ToastTools.showShort(getActivity(), "服务器繁忙！");
//                    return;
//                }
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)) {
                    Intent mIntent = new Intent();
                    mIntent.setClass(getActivity(), ApiCloudActivity.class);
                    mIntent.putExtra("startUrl", data.urls.get(1));
                    startActivity(mIntent);
                } else {
                    Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                    mIntent.putExtra("requestLogin", 100);
                    startActivityForResult(mIntent, 4);
                }
                break;
            case R.id.rl_ts: // 投诉
//                if (mState != 0) {
//                    ToastTools.showShort(getActivity(), "服务器繁忙！");
//                    return;
//                }
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)) {
                    loadState(5);
                } else {
                    Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                    mIntent.putExtra("requestLogin", 100);
                    startActivityForResult(mIntent, 5);
                }
                break;
            case R.id.rl_bx: // 报修
//                if (mState != 0) {
//                    ToastTools.showShort(getActivity(), "服务器繁忙！");
//                    return;
//                }
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)) {
                    loadState(6);
                } else {
                    Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                    mIntent.putExtra("requestLogin", 100);
                    startActivityForResult(mIntent, 6);
                }
                break;
            case R.id.rl_key://  我的手机钥匙
//                if (mState != 0) {
//                    ToastTools.showShort(getActivity(), "服务器繁忙！");
//                    return;
//                }
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)) {
                    loadState(7);
                } else {
                    Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                    mIntent.putExtra("requestLogin", 100);
                    startActivityForResult(mIntent, 7);
                }
                break;
            case R.id.rl_child:
//                if (mState != 0) {
//                    ToastTools.showShort(getActivity(), "服务器繁忙！");
//                    return;
//                }
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)) {
                    loadState(8);
                } else {
                    Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                    mIntent.putExtra("requestLogin", 100);
                    startActivityForResult(mIntent, 8);
                }
                break;
            case R.id.rl_zj://  我的钱包
//                if (mState != 0) {
//                    ToastTools.showShort(getActivity(), "服务器繁忙！");
//                    return;
//                }
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)) {
                    Intent mIntent = new Intent(getActivity(), WalletActivity.class);
                    startActivity(mIntent);
                } else {
                    Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                    mIntent.putExtra("requestLogin", 100);
                    startActivityForResult(mIntent, 10);
                }
                break;
            case R.id.rl_coupon://  我的现金券
//                if (mState != 0) {
//                    ToastTools.showShort(getActivity(), "服务器繁忙！");
//                    return;
//                }
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)) {
                    Intent mIntent = new Intent(getActivity(), CouponActivity.class);
                    startActivity(mIntent);
                } else {
                    Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                    mIntent.putExtra("requestLogin", 100);
                    startActivityForResult(mIntent, 11);
                }
                break;
            case R.id.rl_voucher:   //我的抵用券
//                if (mState != 0) {
//                    ToastTools.showShort(getActivity(), "服务器繁忙！");
//                    return;
//                }
                break;
            case R.id.rl_collect:   //我的收藏
//                if (mState != 0) {
//                    ToastTools.showShort(getActivity(), "服务器繁忙！");
//                    return;
//                }
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)) {
                    if (resultInfos != null & !TextUtils.isEmpty(resultInfos.collectUrl)) {
                        Intent mIntent = new Intent(getActivity(), ApiCloudActivity.class);
                        mIntent.putExtra("startUrl", resultInfos.collectUrl);
                        startActivity(mIntent);
                    }
                } else {
                    Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                    mIntent.putExtra("requestLogin", 100);
                    startActivityForResult(mIntent, 12);
                }
                break;
            case R.id.rl_integral:  //我的积分
//                if (mState != 0) {
//                    ToastTools.showShort(getActivity(), "服务器繁忙！");
//                    return;
//                }
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)) {
                    Intent mIntent = new Intent(getActivity(), ApiCloudActivity.class);
                    if (resultInfos != null && !TextUtils.isEmpty(resultInfos.pointUrl)) {
                        mIntent.putExtra("startUrl", resultInfos.pointUrl);
                    }
                    startActivity(mIntent);
                } else {
                    Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                    mIntent.putExtra("requestLogin", 100);
                    startActivityForResult(mIntent, 13);
                }
                break;
            case R.id.rl_youhui:    //我的优惠券
//                if (mState != 0) {
//                    ToastTools.showShort(getActivity(), "服务器繁忙！");
//                    return;
//                }
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)) {
                    Intent mIntent = new Intent(getActivity(), ApiCloudActivity.class);
                    if (resultInfos != null && !TextUtils.isEmpty(resultInfos.couponUrl)) {
                        mIntent.putExtra("startUrl", resultInfos.couponUrl);
                    }
                    startActivity(mIntent);
                } else {
                    Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                    mIntent.putExtra("requestLogin", 100);
                    startActivityForResult(mIntent, 14);
                }
                break;
            case R.id.rl_zjrecord:
//                if (mState != 0) {
//                    ToastTools.showShort(getActivity(), "服务器繁忙！");
//                    return;
//                }
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)) {
                    Intent mIntent = new Intent(getActivity(), ApiCloudActivity.class);
                    if (resultInfos != null && !TextUtils.isEmpty(resultInfos.awardUrl)) {
                        mIntent.putExtra("startUrl", resultInfos.awardUrl);
                    }
                    startActivity(mIntent);
                } else {
                    Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                    mIntent.putExtra("requestLogin", 100);
                    startActivityForResult(mIntent, 15);
                }
                break;
            case R.id.rl_shou:// 我的设置
//                if (mState != 0) {
//                    ToastTools.showShort(getActivity(), "服务器繁忙！");
//                    return;
//                }
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)) {
                    intent = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent);
                } else {
                    Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                    mIntent.putExtra("requestLogin", 100);
                    startActivityForResult(mIntent, 9);
                }
                break;
            case R.id.tv_unlogin:
//                if (mState != 0) {
//                    ToastTools.showShort(getActivity(), "服务器繁忙！");
//                    return;
//                }
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("mine", mine);
    }

    private void loadState(final int type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("communityId", SharedPreferencesUtil.getInstance(getActivity()).getString("comuid"));
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadCellState(params)
                .map(new Func1<ResultInfo<CellState>, ResultInfo<CellState>>() {
                    @Override
                    public ResultInfo<CellState> call(ResultInfo<CellState> loginInfoResultInfo) {
                        return loginInfoResultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<CellState>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultInfo<CellState> login) {
                        if (login.code == 0) {
                            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("propertytel", login.data.getPropertytel());
                            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("servicetel", login.data.getServicetel());
                            if (login.data.isIsProprietor()) {
                                if (type == 1) {   //装修助手
                                    Intent mIntent = new Intent();
                                    mIntent.setClass(getActivity(), FitmentHomeActivity.class);
                                    startActivity(mIntent);
                                } else if (type == 2) {   //缴费助手
                                    Intent mIntent = new Intent();
                                    mIntent.setClass(getActivity(), PayFeesSelectActivity.class);
                                    startActivity(mIntent);
                                } else if (type == 3) {   //我的房产
                                    Intent mIntent = new Intent();
                                    mIntent.setClass(getActivity(), LiveInfoActivity.class);
                                    startActivity(mIntent);
                                } else if (type == 5) {   //我的投诉
                                    Intent mIntent = new Intent();
                                    mIntent.setClass(getActivity(), ComplaintHistroyActivity.class);
                                    mIntent.putExtra("type", 1);
                                    startActivity(mIntent);
                                } else if (type == 6) {   //我的保修
                                    Intent mIntent = new Intent();
                                    mIntent.setClass(getActivity(), RepairsHistoryActivity.class);
                                    mIntent.putExtra("type", 1);
                                    startActivity(mIntent);
                                } else if (type == 7) {   //我的手机钥匙
                                    Intent mIntent = new Intent(getActivity(), LoadUriActivity.class);
                                    mIntent.putExtra("url", data.keyManagerUrl);
                                    mIntent.putExtra("type", 2);
                                    mIntent.putExtra("title", "钥匙管理");
                                    startActivity(mIntent);
                                } else if (type == 8) {
                                    Intent mIntent = new Intent();
                                    mIntent.setClass(getActivity(), ApiCloudActivity.class);
                                    mIntent.putExtra("startUrl", data.urls.get(0));
                                    startActivity(mIntent);
                                }
                            } else {
                                WarnDialog.show(getActivity(), login.data.getPropertytel(), login.data.getPropertytel());
                            }
                        } else if (login.code == 3) {
                            Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                            mIntent.putExtra("requestLogin", 100);
                            startActivityForResult(mIntent, type);
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            if (requestCode == 1) {
                loadState(1);
            } else if (requestCode == 2) {
                loadState(2);
            } else if (requestCode == 3) {
                loadState(3);
            } else if (requestCode == 4) {
                Intent mIntent = new Intent();
                mIntent.setClass(getActivity(), ApiCloudActivity.class);
                mIntent.putExtra("startUrl", this.data.urls.get(1));
                startActivity(mIntent);
            } else if (requestCode == 5) {
                loadState(5);
            } else if (requestCode == 6) {
                loadState(6);
            } else if (requestCode == 7) {
                loadState(7);
            } else if (requestCode == 8) {
                loadState(8);
            } else if (requestCode == 9) {
                Intent mIntent = new Intent(getActivity(), SettingActivity.class);
                startActivity(mIntent);
            } else if (requestCode == 10) {
                Intent mIntent = new Intent(getActivity(), WalletActivity.class);
                startActivity(mIntent);
            } else if (requestCode == 11) {
                Intent mIntent = new Intent(getActivity(), CouponActivity.class);
                startActivity(mIntent);
            } else if (requestCode == 12) {
                if (resultInfos != null && !TextUtils.isEmpty(resultInfos.collectUrl)) {
                    Intent mIntent = new Intent(getActivity(), ApiCloudActivity.class);
                    mIntent.putExtra("startUrl", resultInfos.collectUrl);
                    startActivity(mIntent);
                }
            } else if (requestCode == 13) {
                Intent mIntent = new Intent(getActivity(), ApiCloudActivity.class);
                if (resultInfos != null && !TextUtils.isEmpty(resultInfos.pointUrl)) {
                    mIntent.putExtra("startUrl", resultInfos.pointUrl);
                }
                startActivity(mIntent);
            } else if (requestCode == 14) {
                Intent mIntent = new Intent(getActivity(), ApiCloudActivity.class);
                if (resultInfos != null && !TextUtils.isEmpty(resultInfos.couponUrl)) {
                    mIntent.putExtra("startUrl", resultInfos.couponUrl);
                }
                startActivity(mIntent);
            } else if (requestCode == 15) {
                Intent mIntent = new Intent(getActivity(), ApiCloudActivity.class);
                if (resultInfos != null && !TextUtils.isEmpty(resultInfos.awardUrl)) {
                    mIntent.putExtra("startUrl", resultInfos.awardUrl);
                }
                startActivity(mIntent);
            }
        }
    }

    @Override
    public void onRefresh() {
        loadInfo();
    }
}