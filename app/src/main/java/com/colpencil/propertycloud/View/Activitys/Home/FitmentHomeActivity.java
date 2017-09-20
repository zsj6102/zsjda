package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.Ad;
import com.colpencil.propertycloud.Bean.CellState;
import com.colpencil.propertycloud.Bean.ListBean;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.GlideLoader;
import com.colpencil.propertycloud.Tools.WarnDialog;
import com.colpencil.propertycloud.View.Activitys.ApiCloudActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.FitmentProcessActivity2;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.umeng.analytics.MobclickAgent;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;

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
 * @Description: 装修助手首页
 * @Email DramaScript@outlook.com
 * @date 2016/11/16
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_fitment_home
)
public class FitmentHomeActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.banner)
    Banner banner;

    @Bind(R.id.iv_yijian)
    ImageView iv_yijian;

    @Bind(R.id.iv_riji)
    ImageView iv_riji;

    @Bind(R.id.iv_jindu)
    ImageView iv_jindu;

    @Bind(R.id.iv_shen)
    ImageView iv_shen;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.tv_shen)
    TextView tv_shen;

    private Intent intent;

    private boolean hasShen;

    private List<String> images = new ArrayList<>();
    private Observable<RxBusMsg> observable;


    @Override
    protected void initViews(View view) {
        hasShen = SharedPreferencesUtil.getInstance(this).getBoolean("hasShen", false);
        tv_title.setText("装修助手");
//        tv_rigth.setText("我的装修许可申请");
        ll_left.setOnClickListener(this);
        iv_yijian.setOnClickListener(this);
        iv_jindu.setOnClickListener(this);
        iv_shen.setVisibility(View.VISIBLE);
        tv_shen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FitmentHomeActivity.this, MaterialManagementActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });
        // 设置广告栏
        banner.setImageLoader(new GlideLoader());
        banner.isAutoPlay(true);
        observable = RxBus.get().register("up", RxBusMsg.class);
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
                        intent = new Intent(FitmentHomeActivity.this, FitmentProcessActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        observable.subscribe(subscriber);
        getAd();
        ll_rigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("up", observable);
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
            case R.id.ll_left:
                finish();
                break;
            case R.id.iv_yijian:  // 物业意见
                loadState(1);
                break;
            case R.id.iv_jindu://  装修进度
                loadState(2);
                break;
//            case R.id.btn_apply_new:
//                loadState(3);
//                break;
        }
    }

    /**
     * 获取广告
     */
    private void getAd() {
        HashMap<String, String> map = new HashMap<>();
        map.put("advTp", "0");
        map.put("advCode", "24");
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getAd(map)
                .map(new Func1<ListBean<Ad>, ListBean<Ad>>() {
                    @Override
                    public ListBean<Ad> call(ListBean<Ad> listBean) {
                        return listBean;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ListBean<Ad>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息：" + e.getMessage());
                    }

                    @Override
                    public void onNext(final ListBean<Ad> listBean) {
                        int code = listBean.code;
                        String message = listBean.message;
                        switch (code) {
                            case 0:

                                iv_riji.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (TextUtils.isEmpty(listBean.jUrl)) {
                                            ToastTools.showShort(FitmentHomeActivity.this, "敬请期待");
                                            return;
                                        }
                                        intent = new Intent(FitmentHomeActivity.this, ApiCloudActivity.class);
                                        intent.putExtra("startUrl", listBean.jUrl);
                                        startActivity(intent);
                                    }
                                });
                                iv_shen.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (TextUtils.isEmpty(listBean.wUrl)) {
                                            ToastTools.showShort(FitmentHomeActivity.this, "敬请期待");
                                            return;
                                        }
                                        intent = new Intent(FitmentHomeActivity.this, LoadUriActivity.class);
                                        intent.putExtra("url", listBean.wUrl);
                                        intent.putExtra("title", "我的申请");
                                        startActivity(intent);
                                    }
                                });
                                for (int i = 0; i < listBean.data.size(); i++) {
                                    images.add(listBean.data.get(i).files);
                                }
                                if (images.size() == 0) {
                                    images.add("ddd");
                                    //设置图片集合
                                    banner.setImages(images);
                                    //banner设置方法全部调用完毕时最后调用
                                    banner.start();
                                } else {
                                    //设置图片集合
                                    banner.setImages(images);
                                    //banner设置方法全部调用完毕时最后调用
                                    banner.start();
                                    banner.setOnBannerClickListener(new OnBannerClickListener() {
                                        @Override
                                        public void OnBannerClick(int position) {
                                            if (listBean.data.size() != 0) {
                                                MobclickAgent.onEvent(FitmentHomeActivity.this, "fitBanner");
                                                intent = new Intent(FitmentHomeActivity.this, LoadUriActivity.class);
                                                intent.putExtra("url", listBean.data.get(position - 1).target);
                                                addCount(listBean.data.get(position - 1).aid);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                                break;
                            case 1:
                                ToastTools.showShort(FitmentHomeActivity.this, false, message);
                                break;
                            case 2:

                                break;
                            case 3:

                                break;
                        }
                    }
                });
    }

    private void addCount(int aid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("aid", aid + "");
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .adCount(map)
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

                    }
                });
    }

    private void loadState(final int type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("communityId", SharedPreferencesUtil.getInstance(this).getString("comuid"));
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
                            if (login.data.isHasShen()) {
                                Intent mIntent = new Intent();
                                if (type == 1) {
                                    mIntent.setClass(FitmentHomeActivity.this, AdviceListActivity.class);
                                } else if (type == 2) {
                                    mIntent.setClass(FitmentHomeActivity.this, FitmentProcessActivity2.class);
                                } else {
                                    mIntent.setClass(FitmentHomeActivity.this, MaterialManagementActivity.class);
                                    mIntent.putExtra("type", 0);
                                }
                                startActivity(mIntent);
                            } else {
                                WarnDialog.showInfo(FitmentHomeActivity.this, "您当前身份为租客，暂无查看权限。请联系您的房东或物业修改。");
                            }
                        } else if (login.code == 3) {
                            startActivity(new Intent(FitmentHomeActivity.this, LoginActivity.class));
                        }
                    }
                });
    }

}
