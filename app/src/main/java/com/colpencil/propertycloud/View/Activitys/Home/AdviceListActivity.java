package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.AdviceList;
import com.colpencil.propertycloud.Bean.CellState;
import com.colpencil.propertycloud.Bean.ListAdvice;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.WarnDialog;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Fragments.CloseManager.FitmentProcessFragment;
import com.colpencil.propertycloud.View.Fragments.Home.AdviceListFragment;
import com.google.gson.Gson;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_fitment_process
)
public class AdviceListActivity extends ColpencilActivity {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tab_layout)
    TabLayout tab_layout;

    @Bind(R.id.viewpager)
    ViewPager viewpager;

    @Bind(R.id.iv_shen)
    ImageView iv_shen;

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

    private Gson gson;

    private List<String> titleList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private TabLayout.TabLayoutOnPageChangeListener listener;
    private MyAdapter myAdapter;
    private int aprovid;
    private boolean hasShen;

    @Override
    protected void initViews(View view) {
        hasShen = SharedPreferencesUtil.getInstance(this).getBoolean("hasShen", false);
        aprovid = getIntent().getIntExtra("aprovid", 0);
        ll_show.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        iv_shen.setVisibility(View.GONE);
        setStatecolor(getResources().getColor(R.color.white));
        iv_shen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadState();
            }
        });
        myAdapter = new MyAdapter(getSupportFragmentManager(), fragmentList);
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
        viewpager.setOffscreenPageLimit(5);

        tv_title.setText("装修中物业意见");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getData();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

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

    /**
     * 获取装修意见
     */
    private void getData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("approveId", aprovid + "");
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getAdviceList(map)
                .map(new Func1<ResultListInfo<ListAdvice>, ResultListInfo<ListAdvice>>() {
                    @Override
                    public ResultListInfo<ListAdvice> call(ResultListInfo<ListAdvice> adviceListResultListInfo) {
                        return adviceListResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<ListAdvice>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultListInfo<ListAdvice> result) {
                        int code = result.code;
                        String message = result.message;
                        switch (code) {
                            case 0:
                                ll_show.setVisibility(View.VISIBLE);
                                rlProgress.setVisibility(View.GONE);
                                rlError.setVisibility(View.GONE);
                                rlEmpty.setVisibility(View.GONE);
                                gson = new Gson();
                                //设置tab标题
                                for (int i = 0; i < result.data.size(); i++) {
                                    titleList.add("我的装修" + (i + 1));
                                }
                                for (int i = 0; i < titleList.size(); i++) {
                                    fragmentList.add(AdviceListFragment.newInstance(i + 1, gson.toJson(result.data.get(i))));
                                }
                                if (titleList.size() == 1) {
                                    tab_layout.setVisibility(View.GONE);
                                }
                                initWight(titleList.size());
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
                                        getData();
                                    }
                                });
                                break;
                            case 2:
                                rlProgress.setVisibility(View.GONE);
                                rlError.setVisibility(View.GONE);
                                rlEmpty.setVisibility(View.VISIBLE);
                                btnReload.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getData();
                                    }
                                });
                                break;
                            case 3:
                                startActivity(new Intent(AdviceListActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
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

    private void loadState() {
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
                                mIntent.setClass(AdviceListActivity.this, FitmentProcessActivity.class);
                                startActivity(mIntent);
                            } else {
                                WarnDialog.showInfo(AdviceListActivity.this, "您当前身份为租客，暂无查看权限。请联系您的房东或物业修改。");
                            }
                        } else if (login.code == 3) {
                            startActivity(new Intent(AdviceListActivity.this, LoginActivity.class));
                        }
                    }
                });
    }
}
