package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.Approve;
import com.colpencil.propertycloud.Bean.CurProgress;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.MyFragmentActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Fragments.CloseManager.FitmentProcessFragment;
import com.google.gson.Gson;
import com.lzy.imagepicker.bean.ImageItem;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

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
 * @Description: 装修进度
 * @Email DramaScript@outlook.com
 * @date 2016/11/17
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_fitment_process
)
public class FitmentProcessActivity extends ColpencilActivity {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tab_layout)
    TabLayout tab_layout;

    @Bind(R.id.viewpager)
    ViewPager viewpager;

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

    @Bind(R.id.ll_show)
    LinearLayout ll_show;

    @Bind(R.id.iv_shen)
    ImageView iv_shen;

    @Bind(R.id.tvError)
    TextView tvError;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.ll_rigth)
    LinearLayout ll_rigth;

    private int isOnce = 0;

    private List<String> titleList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private TabLayout.TabLayoutOnPageChangeListener listener;
    private MyAdapter myAdapter;
    private Gson gson;
    private Observable<RxBusMsg> register;

    @Override
    protected void initViews(View view) {
        ll_show.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        setStatecolor(getResources().getColor(R.color.white));
        getProcess();
        tv_title.setText("装修助手");
        tv_rigth.setText("新申请");
        tv_rigth.setVisibility(View.VISIBLE);
        iv_shen.setVisibility(View.GONE);
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ll_rigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FitmentProcessActivity.this, MaterialManagementActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });
        iv_shen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              crtDecora();
                Intent intent = new Intent(FitmentProcessActivity.this, MaterialManagementActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });

        myAdapter = new MyAdapter(getSupportFragmentManager(), fragmentList);

        register = RxBus.get().register("cg", RxBusMsg.class);
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
                        // TODO: 2016/11/21 刷新操作
//                        titleList.clear();
//                        fragmentList.clear();
//                        tab_layout.removeAllTabs();
//                        getProcess();
//                        myAdapter.notifyDataSetChanged();
//                        ColpencilLogger.e("通知下面列表刷新...");
                        RxBusMsg msg = new RxBusMsg();
                        msg.setType(0);
                        RxBus.get().post("up", msg);
                        finish();
                        break;
                }
            }
        };
        register.subscribe(subscriber);
    }

    /**
     * 初始化tab
     *
     * @param size
     */
    private void initWight(int size) {
        if (size > 4) {
            tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tab_layout.setTabsFromPagerAdapter(myAdapter);
        listener = new TabLayout.TabLayoutOnPageChangeListener(tab_layout);
        viewpager.addOnPageChangeListener(listener);
        viewpager.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    class MyAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        public MyAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }

    private void getProcess() {
        HashMap<String, String> map = new HashMap<>();
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .curProgress(map)
                .map(new Func1<ResultListInfo<CurProgress>, ResultListInfo<CurProgress>>() {
                    @Override
                    public ResultListInfo<CurProgress> call(ResultListInfo<CurProgress> curProgressResultListInfo) {
                        return curProgressResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<CurProgress>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultListInfo<CurProgress> curProgressResultListInfo) {
                        int code = curProgressResultListInfo.code;
                        String message = curProgressResultListInfo.message;
                        switch (code) {
                            case 0:
                                ll_show.setVisibility(View.VISIBLE);
                                rlProgress.setVisibility(View.GONE);
                                rlError.setVisibility(View.GONE);
                                rlEmpty.setVisibility(View.GONE);
                                gson = new Gson();
                                //设置tab标题
                                for (int i = 0; i < curProgressResultListInfo.data.size(); i++) {
                                    titleList.add("我的装修" + (i + 1));
                                }
                                for (int i = 0; i < titleList.size(); i++) {
                                    fragmentList.add(FitmentProcessFragment.newInstance(i + 1, gson.toJson(curProgressResultListInfo.data.get(i))));
                                }
                                if (titleList.size() == 1) {
                                    tab_layout.setVisibility(View.GONE);
                                }
                                initWight(titleList.size());
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("update", rxBusMsg);
                                myAdapter.notifyDataSetChanged();
                                break;
                            case 1:
                                ll_show.setVisibility(View.GONE);
                                rlProgress.setVisibility(View.GONE);
                                rlError.setVisibility(View.VISIBLE);
                                rlEmpty.setVisibility(View.GONE);
                                btnReloadEmpty.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getProcess();
                                    }
                                });
                                tvError.setText(message);
                                break;
                            case 2:
                                rlProgress.setVisibility(View.GONE);
                                rlError.setVisibility(View.GONE);
                                rlEmpty.setVisibility(View.VISIBLE);
                                btnReload.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getProcess();
                                    }
                                });
                                break;
                            case 3:
                                startActivity(new Intent(FitmentProcessActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    /**
     * 生成装修申请id
     */
    private void crtDecora() {
        HashMap<String, String> map = new HashMap<>();
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .crtDecora(map)
                .map(new Func1<ResultInfo<Approve>, ResultInfo<Approve>>() {
                    @Override
                    public ResultInfo<Approve> call(ResultInfo<Approve> approveResultInfo) {
                        return approveResultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<Approve>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo<Approve> approveResultInfo) {
                        int code = approveResultInfo.code;
                        String message = approveResultInfo.message;
                        switch (code) {
                            case 0:
                                Approve data = approveResultInfo.data;
                                Intent intent = new Intent(FitmentProcessActivity.this, MaterialManagementActivity.class);
                                intent.putExtra("approveid", data.approveid);
                                startActivity(intent);
                                break;
                            case 1:
                                ToastTools.showShort(FitmentProcessActivity.this, message);
                                break;
                            case 3:
                                startActivity(new Intent(FitmentProcessActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

}