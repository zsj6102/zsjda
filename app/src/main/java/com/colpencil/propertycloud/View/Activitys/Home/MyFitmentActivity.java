package com.colpencil.propertycloud.View.Activitys.Home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Fragments.Fragment2;
import com.colpencil.propertycloud.View.Fragments.Home.AdviceListFragment;
import com.colpencil.propertycloud.View.Fragments.Home.CurrentProgressFragment;
import com.colpencil.propertycloud.View.Fragments.Home.LiveStreamingListFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import butterknife.Bind;

/**
 * @Description: 我的装修
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_fitment
)
public class MyFitmentActivity extends ColpencilActivity{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tab_layout)
    TabLayout tab_layout;

    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private String[] title = {"当前进度","委托监理","装修直播","物业意见"};
    private TabLayout.TabLayoutOnPageChangeListener listener;
    private MyAdapter myAdapter;

    @Override
    protected void initViews(View view) {
        myAdapter = new MyAdapter(getSupportFragmentManager());
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

        tv_title.setText("我的装修");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    class  MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0){
                return new CurrentProgressFragment();
            }else if (position == 1){
                return new Fragment2();
            }else if (position == 2){
                return new LiveStreamingListFragment();
            }else {
                return new AdviceListFragment();
            }
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
}
