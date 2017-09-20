package com.colpencil.propertycloud.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.FindGoodsId;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.Model;
import com.colpencil.propertycloud.View.Activitys.ApiCloudActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Fragments.Home.FindGoodsFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.ta.utdid2.android.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * @author 汪 亮
 * @Description:
 * @Email DramaScript@outlook.com
 * @date 2016/6/30
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_2
)
public class Fragment2 extends ColpencilFragment {

    private List<String> list = new ArrayList<>();
    private List<TextView> tvList = new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private LayoutInflater inflater;

    @Bind(R.id.tools_scrlllview)
    ScrollView scrollView;

    @Bind(R.id.goods_pager)
    ViewPager viewpager;

    @Bind(R.id.ll_search)
    LinearLayout ll_search;

    @Bind(R.id.iv_cat)
    ImageView iv_cat;

    @Bind(R.id.ll_show)
    LinearLayout ll_show;

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

    private List<FindGoodsId> findGoodsIds = new ArrayList<>();

    private int currentItem = 0;
    private ShopAdapter shopAdapter;

    @Bind(R.id.tools)
    LinearLayout toolsLayout;

    private Intent intent;

    @Override
    protected void initViews(View view) {
        ll_show.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        shopAdapter = new ShopAdapter(getChildFragmentManager());
        inflater = LayoutInflater.from(getActivity());
        getGoodsId();

    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void showToolsView(List<FindGoodsId> goodsIdList) {

        for (int i=0;i<goodsIdList.size();i++){
            list.add(goodsIdList.get(i).cat_name);
        }
        for (int i = 0; i < list.size(); i++) {
            View view = inflater.inflate(R.layout.item_list_layout, null);
            view.setId(i);
            view.setOnClickListener(toolsItemListener);
            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText(list.get(i));
            toolsLayout.addView(view);
            tvList.add(textView);
            views.add(view);
        }
        changeTextColor(0);
        initPager();
    }

    private void getGoodsId(){
        if(!NetworkUtils.isConnected(getActivity())){
            ll_show.setVisibility(View.GONE);
            rlProgress.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.GONE);
            rlError.setVisibility(View.VISIBLE);
            btnReloadEmpty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getGoodsId();
                }
            });
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        RetrofitManager.getInstance(1,getActivity(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .FindGoods(params)
                .map(new Func1<ResultListInfo<FindGoodsId>, ResultListInfo<FindGoodsId>>() {
                    @Override
                    public ResultListInfo<FindGoodsId> call(ResultListInfo<FindGoodsId> findGoodsIdResultListInfo) {
                        return findGoodsIdResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<FindGoodsId>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(final ResultListInfo<FindGoodsId> findGoodsIdResultListInfo) {
                        int code = findGoodsIdResultListInfo.code;
                        String message = findGoodsIdResultListInfo.message;
                        switch (code) {
                            case 0:
                                findGoodsIds.addAll(findGoodsIdResultListInfo.data);
                                ll_show.setVisibility(View.VISIBLE);
                                rlProgress.setVisibility(View.GONE);
                                rlEmpty.setVisibility(View.GONE);
                                ll_search.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        intent = new Intent(getActivity(), ApiCloudActivity.class);
                                        intent.putExtra("startUrl", findGoodsIdResultListInfo.search_url);
                                        intent.putExtra("isSearch", true);
                                        //TODO isSearch
                                        startActivity(intent);
                                    }
                                });
                                iv_cat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        intent = new Intent(getActivity(), ApiCloudActivity.class);
                                        intent.putExtra("startUrl", findGoodsIdResultListInfo.shopping_cart_url);
                                        startActivity(intent);
                                    }
                                });
                                ColpencilLogger.e("--------size="+findGoodsIdResultListInfo.data.size());
                                showToolsView(findGoodsIdResultListInfo.data);
                                break;
                            case 2:
                                ll_show.setVisibility(View.GONE);
                                rlProgress.setVisibility(View.GONE);
                                rlEmpty.setVisibility(View.VISIBLE);
                                btnReload.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getGoodsId();
                                    }
                                });
                                break;
                            case 1:
                                ll_show.setVisibility(View.GONE);
                                rlProgress.setVisibility(View.VISIBLE);
                                rlEmpty.setVisibility(View.GONE);
                                btnReloadEmpty.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getGoodsId();
                                    }
                                });
                                ToastTools.showShort(getActivity(), false, message);
                                break;
                            case 3:
                                break;
                        }
                    }
                });
    }

    private View.OnClickListener toolsItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            viewpager.setCurrentItem(v.getId());
        }
    };

    private void initPager() {
        viewpager.setAdapter(shopAdapter);
        viewpager.setOnPageChangeListener(onPageChangeListener);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int arg0) {
            if (viewpager.getCurrentItem() != arg0)
                viewpager.setCurrentItem(arg0,false);
            if (currentItem != arg0) {
                changeTextColor(arg0);
                changeTextLocation(arg0);
            }
            currentItem = arg0;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    };

    private class ShopAdapter extends FragmentPagerAdapter {
        public ShopAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {
            return new FindGoodsFragment().newInstance(findGoodsIds.get(index).cat_id);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    private void changeTextColor(int id) {
        for (int i = 0; i < tvList.size(); i++) {
            if (i != id) {
                tvList.get(i).setBackgroundColor(0x00000000);
                tvList.get(i).setTextColor(0xFF000000);
            }
        }
        tvList.get(id).setBackgroundColor(getResources().getColor(R.color.main_bg));
        tvList.get(id).setTextColor(getResources().getColor(R.color.main_pink));
    }

    private void changeTextLocation(int clickPosition) {
        int x = (views.get(clickPosition).getTop());
        scrollView.smoothScrollTo(0, x);
    }
}