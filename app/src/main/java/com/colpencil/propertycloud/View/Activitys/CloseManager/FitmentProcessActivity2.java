package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Home.MaterialManagementActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Fragments.CloseManager.NewFitmentProgressFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_fitment_process
)
public class FitmentProcessActivity2 extends ColpencilActivity {

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

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    private List<String> titleList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private TabLayout.TabLayoutOnPageChangeListener listener;
    private MyAdapter myAdapter;

    private Observable<RxBusMsg> register;


    @Override
    protected void initViews(View view) {
        ll_show.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        setStatecolor(getResources().getColor(R.color.white));
        tv_title.setText("装修许可证申请");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_rigth.setText("新申请");
        tv_rigth.setVisibility(View.VISIBLE);
        iv_shen.setVisibility(View.GONE);
        tv_rigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FitmentProcessActivity2.this, MaterialManagementActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });
        iv_shen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FitmentProcessActivity2.this, MaterialManagementActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });
        getIds();
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
                        titleList.clear();
                        fragmentList.clear();
                        tab_layout.removeAllTabs();
                        getIds();
                        ColpencilLogger.e("通知下面列表刷新...");
                        break;
                    case 1:
                        RxBusMsg rxBusMsg1 = new RxBusMsg();
                        rxBusMsg1.setType(0);
                        RxBus.get().post("update", rxBusMsg1);
                        break;
                }
            }
        };
        register.subscribe(subscriber);
    }

    private void initWight(int size) {
        if (size > 4) {
            tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        myAdapter = new MyAdapter(getSupportFragmentManager(), fragmentList);
        tab_layout.setTabsFromPagerAdapter(myAdapter);
        listener = new TabLayout.TabLayoutOnPageChangeListener(tab_layout);
        viewpager.addOnPageChangeListener(listener);
        viewpager.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
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
    }

    private void getIds() {
        HashMap<String, String> map = new HashMap<>();
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getCurProgessIds(map)
                .map(new Func1<ResultListInfo<String>, ResultListInfo<String>>() {
                    @Override
                    public ResultListInfo<String> call(ResultListInfo<String> stringEntityResult) {
                        return stringEntityResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultListInfo<String> stringEntityResult) {
                        int code = stringEntityResult.code;
                        String message = stringEntityResult.message;
                        switch (code) {
                            case 0:
                                ll_show.setVisibility(View.VISIBLE);
                                rlProgress.setVisibility(View.GONE);
                                rlError.setVisibility(View.GONE);
                                rlEmpty.setVisibility(View.GONE);
                                for (int i = 0; i < stringEntityResult.data.size(); i++) {
                                    titleList.add("我的装修" + (i + 1));
                                }
                                for (int i = 0; i < titleList.size(); i++) {
                                    fragmentList.add(NewFitmentProgressFragment.newInstance(i + 1, stringEntityResult.data.get(i)));
                                }
                                for (int i = 0; i < stringEntityResult.data.size(); i++) {
                                    tab_layout.addTab(tab_layout.newTab().setText(titleList.get(i)));
                                }
                                if (titleList.size() == 1) {
                                    tab_layout.setVisibility(View.GONE);
                                }
                                initWight(titleList.size());
                                break;
                            case 1:
                                ll_show.setVisibility(View.GONE);
                                rlProgress.setVisibility(View.GONE);
                                rlError.setVisibility(View.VISIBLE);
                                rlEmpty.setVisibility(View.GONE);
                                btnReloadEmpty.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getIds();
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
                                        getIds();
                                    }
                                });
                                break;
                            case 3:
                                startActivity(new Intent(FitmentProcessActivity2.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    class MyAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragmentList;

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
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

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
