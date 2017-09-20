package com.colpencil.propertycloud.View.Fragments.Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.Bean.CellState;
import com.colpencil.propertycloud.Bean.CityBean;
import com.colpencil.propertycloud.Bean.HomeData;
import com.colpencil.propertycloud.Bean.ModuleInfo;
import com.colpencil.propertycloud.Bean.ModuleResult;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Service.LocationService;
import com.colpencil.propertycloud.Tools.GlideLoader;
import com.colpencil.propertycloud.Tools.WarnDialog;
import com.colpencil.propertycloud.Ui.BaseDialog;
import com.colpencil.propertycloud.Ui.indicator.VpSwipeRefreshLayout;
import com.colpencil.propertycloud.View.Activitys.ApiCloudActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.TenementRepairsActivity;
import com.colpencil.propertycloud.View.Activitys.Home.ComplaintActivity;
import com.colpencil.propertycloud.View.Activitys.Home.FitmentHomeActivity;
import com.colpencil.propertycloud.View.Activitys.Home.PayFeesActivity;
import com.colpencil.propertycloud.View.Activitys.Home.ScanpayActivity;
import com.colpencil.propertycloud.View.Activitys.Home.VilageSelectActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.CityPickerActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.GuideActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.HomeAdapter;
import com.colpencil.propertycloud.View.Adapter.ModuleAdapter;
import com.colpencil.propertycloud.View.Pager.HomePager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilGridView;
import com.rd.PageIndicatorView;
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
 * @Description: 首页
 * @Email DramaScript@outlook.com
 * @date 2016/6/30
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_home
)
public class HomeFragment extends ColpencilFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.tv_vilage_name)
    TextView tv_vilage_name;

    @Bind(R.id.ll_select)
    LinearLayout ll_select;

    @Bind(R.id.bg_normal)
    VpSwipeRefreshLayout bg_normal;

    @Bind(R.id.lv_normal)
    ListView lv_normal;

    @Bind(R.id.scan)
    LinearLayout scan;

    private Intent intent;
    private String comuid;
    private Observable<RxBusMsg> carObservable;
    private Banner banner;
    private ImageView pic1;
    private ImageView pic2;
    private ImageView pic3;
    private TextView tv_news1;
    private TextView tv_news2;
    private Observable<RxBusMsg> msgObservable;

    private HomeAdapter adapter;
    private TextView tv_gonggao;
    private LinearLayout ll_gon_more;
    private RelativeLayout rl_gong_detail;
    private TextView tv_time;
    private LinearLayout ll_news_more;
    private RelativeLayout rl_news_detail1;
    private RelativeLayout rl_news_detail2;
    private TextView tv_time1;
    private TextView tv_time2;
    private ColpencilGridView gridview;
    private LinearLayout ll_content;

    //轮播的图片 和 跳转
    private List<String> benner = new ArrayList<>();
    private List<String> bennerUrl = new ArrayList<>();
    //中间广告的
    private List<String> ad = new ArrayList<>();
    private List<String> adUrl = new ArrayList<>();
    //最下面的广告
    private List<String> last = new ArrayList<String>();
    private List<String> lastUrl = new ArrayList<>();
    private RelativeLayout rl1;
    /**
     * 首页数据
     */
    private HomeData homeData;

    private MyViewPagerAdapter mAdapter;
    private ViewPager viewpager;
    private PageIndicatorView piv;
    private ModuleAdapter moduleAdapter;
    private List<ModuleInfo> moduleInfoList = new ArrayList<>();
    private View headerView5;
    private View headerView6;
    private String mUrls;
    private int serviceId;

    private List<HomePager> pagers = new ArrayList<>();

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    protected void initViews(View view) {
        comuid = SharedPreferencesUtil.getInstance(getActivity()).getString("comuid");
        if (!TextUtils.isEmpty(SharedPreferencesUtil.getInstance(getActivity()).getString("shortnm"))) {
            tv_vilage_name.setText(SharedPreferencesUtil.getInstance(getActivity()).getString("cityName") +
                    SharedPreferencesUtil.getInstance(getActivity()).getString("shortnm"));
        } else {
            tv_vilage_name.setText("定位中...");
            getActivity().startService(new Intent(getActivity(), LocationService.class));
        }
        initBus();
        //初始化下拉刷新控件
        bg_normal.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        bg_normal.setOnRefreshListener(this);

        //头部1
        View headView1 = View.inflate(getActivity(), R.layout.item_home_head, null);
        lv_normal.addHeaderView(headView1);
        //头部5
        headerView5 = View.inflate(getActivity(), R.layout.item_home_head5, null);
        lv_normal.addHeaderView(headerView5);
        //头部6
        headerView6 = View.inflate(getActivity(), R.layout.item_home_head6, null);
        lv_normal.addHeaderView(headerView6);
        //头部2
        View headView2 = View.inflate(getActivity(), R.layout.item_home_head2, null);
        lv_normal.addHeaderView(headView2);
        //头部3
        View headView3 = View.inflate(getActivity(), R.layout.item_home_head3, null);
        lv_normal.addHeaderView(headView3);
        //头部4
        View headView4 = View.inflate(getActivity(), R.layout.item_home_head4, null);
        lv_normal.addHeaderView(headView4);

        adapter = new HomeAdapter(getActivity(), last);
        lv_normal.setAdapter(adapter);

        initHeadView(headView1);
        initHeadView2(headView2);
        initHeadView3(headView3);
        initHeadView4(headView4);
        initHeadView5();
        initHeadView6();

        initListener();

        bg_normal.post(new Runnable() {
            @Override
            public void run() {
                bg_normal.setRefreshing(true);
                onRefresh();
            }
        });
        loadModule();
        lv_normal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 5) {
                    if (!TextUtils.isEmpty(lastUrl.get(position - 6))) {
                        intent = new Intent(getActivity(), ApiCloudActivity.class);
                        intent.putExtra("startUrl", lastUrl.get(position - 6));
                        getActivity().startActivity(intent);
                    }
                }
            }
        });
    }

    private void initBus() {
        carObservable = RxBus.get().register(VilageSelectActivity.class.getSimpleName(), RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg eventCar) {
                int type = eventCar.getType();
                switch (type) {
                    case 2:
                        tv_vilage_name.setText(SharedPreferencesUtil.getInstance(getActivity()).getString("cityName") + eventCar.getMsg());
                        comuid = SharedPreferencesUtil.getInstance(getActivity()).getString("comuid");
                        loadData();
                        break;
                    case 3:
                        serviceId = eventCar.getId();
                        mUrls = eventCar.getContent();
                        String code = eventCar.getMsg();
                        int isOwner = eventCar.getIsOwner();
                        if (TextUtils.isEmpty(mUrls)) {      //这个为空表示原生，如果不为空表示h5
                            if (code.equals("_pay")) {     //缴费助手
                                if (isOwner == 0) {
                                    startActivity(new Intent(getActivity(), PayFeesActivity.class));
                                } else {
                                    loadState(0);
                                }
                            } else if (code.equals("_baoxiu")) {       //报修
                                if (isOwner == 0) {
                                    startActivity(new Intent(getActivity(), TenementRepairsActivity.class));
                                } else {
                                    loadState(1);
                                }
                            } else if (code.equals("_tousu")) {       //投诉
                                if (isOwner == 0) {
                                    startActivity(new Intent(getActivity(), ComplaintActivity.class));
                                } else {
                                    loadState(2);
                                }
                            } else if (code.equals("_zhuangxiu")) {       //装修助手
                                if (isOwner == 0) {
                                    startActivity(new Intent(getActivity(), FitmentHomeActivity.class));
                                } else {
                                    loadState(3);
                                }
                            }
                        } else {
                            if (isOwner == 0) {
                                Intent intent = new Intent(getActivity(), ApiCloudActivity.class);
                                intent.putExtra("startUrl", mUrls);
                                startActivity(intent);
                            } else {
                                loadState(4);
                            }
                        }
                        break;
                    case 4:
                        loadModule();
                        break;
                    case 5:
                        if (tv_vilage_name != null) {
                            tv_vilage_name.setText(SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).getString("cityName")
                                    + SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).getString("shortnm"));
                        }
                        break;
                }
            }
        };
        carObservable.subscribe(subscriber);
        msgObservable = RxBus.get().register("message", RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber1 = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg msg) {
                Log.e("error------>", "开始抖动");
                if (msg.getType() == 2) {     //抖动抽奖icon
                    if (moduleInfoList.size() > 0) {
                        moduleAdapter.shake();
                        moduleAdapter.shake();
                    } else {
                        for (int i = 0; i < pagers.size(); i++) {
                            HomePager pager = pagers.get(i);
                            pager.startAnim();
                            pager.startAnim();
                        }
                    }
                } else if (msg.getType() == 4) {
                    if (moduleInfoList.size() > 0) {
                        moduleAdapter.clearAnim();
                    } else {
                        for (int i = 0; i < pagers.size(); i++) {
                            HomePager pager = pagers.get(i);
                            pager.mAdapter.clearAnim();
                        }
                    }
                }
            }
        };
        msgObservable.subscribe(subscriber1);
    }

    private void initListener() {
        ll_select.setOnClickListener(this);
        scan.setOnClickListener(this);
        ll_gon_more.setOnClickListener(this);
        rl_gong_detail.setOnClickListener(this);
        rl1.setOnClickListener(this);
        rl_news_detail1.setOnClickListener(this);
        rl_news_detail2.setOnClickListener(this);
        ll_news_more.setOnClickListener(this);
        pic1.setOnClickListener(this);
        pic2.setOnClickListener(this);
        pic3.setOnClickListener(this);
    }

    /**
     * 初始化第四个
     *
     * @param headView4
     */
    private void initHeadView4(View headView4) {
        ll_news_more = (LinearLayout) headView4.findViewById(R.id.ll_news_more);
        rl_news_detail1 = (RelativeLayout) headView4.findViewById(R.id.rl_news_detail1);
        rl_news_detail2 = (RelativeLayout) headView4.findViewById(R.id.rl_news_detail2);
        tv_news1 = (TextView) headView4.findViewById(R.id.tv_news1);
        tv_time1 = (TextView) headView4.findViewById(R.id.tv_time1);
        tv_news2 = (TextView) headView4.findViewById(R.id.tv_news2);
        tv_time2 = (TextView) headView4.findViewById(R.id.tv_time2);
    }

    /**
     * 初始化第三个
     *
     * @param headView3
     */
    private void initHeadView3(View headView3) {
        pic1 = (ImageView) headView3.findViewById(R.id.pic1);
        pic2 = (ImageView) headView3.findViewById(R.id.pic2);
        pic3 = (ImageView) headView3.findViewById(R.id.pic3);
    }

    /**
     * 初始化第二个
     *
     * @param headView2
     */
    private void initHeadView2(View headView2) {
        ll_gon_more = (LinearLayout) headView2.findViewById(R.id.ll_gon_more);
        rl_gong_detail = (RelativeLayout) headView2.findViewById(R.id.rl_gong_detail);
        tv_gonggao = (TextView) headView2.findViewById(R.id.tv_gonggao);
        tv_time = (TextView) headView2.findViewById(R.id.tv_time);
        rl1 = (RelativeLayout) headView2.findViewById(R.id.rl1);
    }

    /**
     * 初始化头部
     *
     * @param headView
     */
    private void initHeadView(View headView) {
        viewpager = (ViewPager) headView.findViewById(R.id.viewpager);
        banner = (Banner) headView.findViewById(R.id.banner);
        banner.setImageLoader(new GlideLoader());
        banner.isAutoPlay(true);
    }

    private void initHeadView5() {
        gridview = (ColpencilGridView) headerView5.findViewById(R.id.gridview);
        moduleAdapter = new ModuleAdapter(getActivity(), moduleInfoList, R.layout.item_module);
        gridview.setAdapter(moduleAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModuleInfo info = moduleInfoList.get(position);
                mUrls = info.url;
                serviceId = info.serviceId;
                if (TextUtils.isEmpty(mUrls)) {      //这个为空表示原生，如果不为空表示h5
                    if (info.code.equals("_pay")) {     //缴费助手
                        if (info.isOwner == 0) {
                            startActivity(new Intent(getActivity(), PayFeesActivity.class));
                        } else {
                            loadState(0);
                        }
                    } else if (info.code.equals("_baoxiu")) {       //报修
                        if (info.isOwner == 0) {
                            startActivity(new Intent(getActivity(), TenementRepairsActivity.class));
                        } else {
                            loadState(1);
                        }
                    } else if (info.code.equals("_tousu")) {       //投诉
                        if (info.isOwner == 0) {
                            startActivity(new Intent(getActivity(), ComplaintActivity.class));
                        } else {
                            loadState(2);
                        }
                    } else if (info.code.equals("_zhuangxiu")) {       //装修助手
                        if (info.isOwner == 0) {
                            startActivity(new Intent(getActivity(), FitmentHomeActivity.class));
                        } else {
                            loadState(3);
                        }
                    }
                } else {
                    if (info.code.equals("_choujiang")) {
                        moduleAdapter.clearAnim();
                    }
                    if (info.isOwner == 0) {
                        Intent intent = new Intent(getActivity(), ApiCloudActivity.class);
                        intent.putExtra("startUrl", mUrls);
                        startActivity(intent);
                    } else {
                        loadState(4);
                    }
                }
            }
        });

    }

    private void initHeadView6() {
        viewpager = (ViewPager) headerView6.findViewById(R.id.viewpager);
        ll_content = (LinearLayout) headerView6.findViewById(R.id.ll_content);
        piv = (PageIndicatorView) headerView6.findViewById(R.id.indicator);
        piv.setSelectedColor(Color.parseColor("#fc74a2"));
        piv.setUnselectedColor(getResources().getColor(R.color.line2));
    }

    /**
     * 获取首页数据
     */
    private void loadData() {
        benner.clear();
        bennerUrl.clear();
        ad.clear();
        adUrl.clear();
        last.clear();
        lastUrl.clear();
        HashMap<String, String> map = new HashMap<>();
        map.put("communityId", comuid);
        RetrofitManager.getInstance(1, getActivity(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getIndexData(map)
                .map(new Func1<HomeData, HomeData>() {
                    @Override
                    public HomeData call(HomeData homeData) {
                        return homeData;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        bg_normal.post(new Runnable() {
                            @Override
                            public void run() {
                                bg_normal.setRefreshing(false);
                            }
                        });
                    }

                    @Override
                    public void onNext(final HomeData homeData) {
                        bg_normal.setRefreshing(false);
                        int code = homeData.code;
                        bg_normal.post(new Runnable() {
                            @Override
                            public void run() {
                                bg_normal.setRefreshing(false);
                            }
                        });
                        switch (code) {
                            case 0:
                                HomeFragment.this.homeData = homeData;
                                //物业公共
                                if (homeData.noticeData.size() != 0) {
                                    tv_gonggao.setText(homeData.noticeData.get(0).title);
                                    tv_time.setText(homeData.noticeData.get(0).notice_time);
                                }
                                // 最新新闻
                                if (homeData.articleData.size() > 1) {
                                    rl_news_detail1.setVisibility(View.VISIBLE);
                                    rl_news_detail2.setVisibility(View.VISIBLE);
                                    tv_news1.setText(homeData.articleData.get(0).title);
                                    tv_time1.setText(homeData.articleData.get(0).news_time);
                                    tv_time2.setText(homeData.articleData.get(1).title);
                                    tv_news2.setText(homeData.articleData.get(1).news_time);
                                } else if (homeData.articleData.size() == 1) {
                                    rl_news_detail1.setVisibility(View.VISIBLE);
                                    rl_news_detail2.setVisibility(View.GONE);
                                    tv_news1.setText(homeData.articleData.get(0).title);
                                    tv_time1.setText(homeData.articleData.get(0).news_time);
                                }
                                for (int i = 0; i < homeData.advData.size(); i++) {
                                    if (homeData.advData.get(i).type == 1) {
                                        benner.add(homeData.advData.get(i).files);
                                        bennerUrl.add(homeData.advData.get(i).target);
                                    } else if (homeData.advData.get(i).type == 2) {
                                        ad.add(homeData.advData.get(i).files);
                                        adUrl.add(homeData.advData.get(i).target);
                                    } else if (homeData.advData.get(i).type == 3) {
                                        last.add(homeData.advData.get(i).files);
                                        lastUrl.add(homeData.advData.get(i).target);
                                    }
                                }
                                //给轮播设置数据
                                if (benner.size() == 0) {
                                    bennerUrl.add("rgerg");
                                    //设置图片集合
                                    banner.setImages(benner);
                                    //banner设置方法全部调用完毕时最后调用
                                    banner.start();
                                } else {
                                    //设置图片集合
                                    banner.setImages(benner);
                                    //banner设置方法全部调用完毕时最后调用
                                    banner.start();
                                    banner.setOnBannerClickListener(new OnBannerClickListener() {
                                        @Override
                                        public void OnBannerClick(int position) {
                                            if (!TextUtils.isEmpty(bennerUrl.get(position - 1))) {
                                                MobclickAgent.onEvent(getActivity(), "homeBanner");
                                                intent = new Intent(getActivity(), ApiCloudActivity.class);
                                                intent.putExtra("startUrl", bennerUrl.get(position - 1));
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                                //给中间三张广告图设置数据
                                ImageLoader.getInstance().displayImage(ad.get(0), pic1);
                                ImageLoader.getInstance().displayImage(ad.get(1), pic2);
                                ImageLoader.getInstance().displayImage(ad.get(2), pic3);
                                adapter.notifyDataSetChanged();
                                break;
                            case 1:
                                ToastTools.showShort(getActivity(), homeData.message);
                                break;
                            case 3:
                                break;
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select://  小区选择
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isProprietor", false)) {
                    intent = new Intent(getActivity(), VilageSelectActivity.class);
                } else {
                    intent = new Intent(getActivity(), CityPickerActivity.class);
                }
                intent.putExtra("flag", 2);
                intent.putExtra("isOnce", 0);
                intent.putExtra("isFirstIn", true);
                startActivity(intent);
                break;
            case R.id.pic1:
                MobclickAgent.onEvent(getActivity(), "homeAdd");
                intent = new Intent(getActivity(), ApiCloudActivity.class);
                intent.putExtra("startUrl", adUrl.get(0));
                startActivity(intent);
                break;
            case R.id.pic2:
                MobclickAgent.onEvent(getActivity(), "homeAdd2");
                intent = new Intent(getActivity(), ApiCloudActivity.class);
                intent.putExtra("startUrl", adUrl.get(1));
                startActivity(intent);
                break;
            case R.id.pic3:
                MobclickAgent.onEvent(getActivity(), "homeAdd3");
                intent = new Intent(getActivity(), ApiCloudActivity.class);
                intent.putExtra("startUrl", adUrl.get(2));
                startActivity(intent);
                break;
            case R.id.rl1:
                break;
            case R.id.scan:
                intent = new Intent(getActivity(), ScanpayActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_gon_more:
                intent = new Intent(getActivity(), ApiCloudActivity.class);
                intent.putExtra("startUrl", homeData.noticeMore);
                startActivity(intent);
                break;
            case R.id.rl_gong_detail:
                if (homeData.noticeData != null) {
                    intent = new Intent(getActivity(), ApiCloudActivity.class);
                    intent.putExtra("startUrl", homeData.noticeData.get(0).url);
                    startActivity(intent);
                }
                break;
            case R.id.ll_news_more:
                intent = new Intent(getActivity(), ApiCloudActivity.class);
                intent.putExtra("startUrl", homeData.articleMore);
//                intent.putExtra("startUrl", "http://112.74.133.239/apicloud/html/kid/camera.html");
                startActivity(intent);
                break;
            case R.id.rl_news_detail1:
                intent = new Intent(getActivity(), ApiCloudActivity.class);
                intent.putExtra("startUrl", homeData.articleData.get(0).url);
                startActivity(intent);
                break;
            case R.id.rl_news_detail2:
                intent = new Intent(getActivity(), ApiCloudActivity.class);
                intent.putExtra("startUrl", homeData.articleData.get(1).url);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(VilageSelectActivity.class.getSimpleName(), carObservable);
        RxBus.get().unregister("message", msgObservable);
    }

    private void loadState(final int type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("communityId", SharedPreferencesUtil.getInstance(getActivity()).getString("comuid"));
        params.put("serviceId", serviceId + "");
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
                            if (login.data.isClick()) {
                                if (type == 0) {
                                    startActivity(new Intent(getActivity(), PayFeesActivity.class));
                                } else if (type == 1) {
                                    startActivity(new Intent(getActivity(), TenementRepairsActivity.class));
                                } else if (type == 2) {
                                    startActivity(new Intent(getActivity(), ComplaintActivity.class));
                                } else if (type == 3) {
                                    startActivity(new Intent(getActivity(), FitmentHomeActivity.class));
                                } else {
                                    Intent intent = new Intent(getActivity(), ApiCloudActivity.class);
                                    intent.putExtra("startUrl", mUrls);
                                    startActivity(intent);
                                }
                            } else {
                                WarnDialog.show(getActivity(), login.data.getPropertytel(), login.data.getServicetel());
                            }
                        } else {
                            Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                            startActivityForResult(mIntent, type);
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            if (requestCode == 0) {
                loadState(0);
            } else if (requestCode == 1) {
                loadState(1);
            } else if (requestCode == 2) {
                loadState(2);
            } else if (requestCode == 3) {
                loadState(3);
            } else if (requestCode == 4) {
                loadState(4);
            }
        }
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    private void loadModule() {
        final HashMap<String, String> params = new HashMap<>();
        params.put("appCode", 1 + "");
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadmodule(params)
                .map(new Func1<ModuleResult, ModuleResult>() {
                    @Override
                    public ModuleResult call(ModuleResult gsonTest) {
                        return gsonTest;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModuleResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ModuleResult result) {
                        moduleInfoList.clear();
                        pagers.clear();
                        if (result.code == 0) {
                            if (result.data != null && result.data.size() > 0) {
                                if (result.data.size() > 1) {
                                    gridview.setVisibility(View.GONE);
                                    ll_content.setVisibility(View.VISIBLE);
                                    List<View> views = new ArrayList<>();
                                    for (int i = 0; i < result.data.size(); i++) {
                                        HomePager pager = new HomePager(getActivity(), result.data.get(i));
                                        views.add(pager.inflateView());
                                        pagers.add(pager);
                                    }
                                    mAdapter = new MyViewPagerAdapter(views);
                                    viewpager.setAdapter(mAdapter);
                                    piv.setCount(views.size());
                                    piv.setViewPager(viewpager);
                                } else {
                                    ll_content.setVisibility(View.GONE);
                                    gridview.setVisibility(View.VISIBLE);
                                    moduleInfoList.clear();
                                    for (int i = 0; i < result.data.size(); i++) {
                                        for (int j = 0; j < result.data.get(i).size(); j++) {
                                            moduleInfoList.add(result.data.get(i).get(j));
                                        }
                                    }
                                    moduleAdapter.notifyDataSetChanged();
                                }
                            } else {
                                ll_content.setVisibility(View.GONE);
                                gridview.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    /**
     * 自定义的PagerAdapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {

        private List<View> views = null;

        public MyViewPagerAdapter(List<View> views) {
            super();
            this.views = views;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(views.get(position));
            return views.get(position);
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

}