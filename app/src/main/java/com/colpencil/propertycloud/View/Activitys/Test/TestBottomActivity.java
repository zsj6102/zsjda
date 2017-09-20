package com.colpencil.propertycloud.View.Activitys.Test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.HomeActivity;
import com.colpencil.propertycloud.View.Fragments.FragmentFactory;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColBottomNavigation.ColBottomNavigationViewPager;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColBottomNavigationSuper.BadgeItem;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColBottomNavigationSuper.BottomNavigationBar;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColBottomNavigationSuper.BottomNavigationItem;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestBottomActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {


    @Bind(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    @Bind(R.id.hostactivity_view_pager)
    protected ColBottomNavigationViewPager hostactivity_view_pager;

    private MyViewPagerAdapter myViewPagerAdapter;

    int lastSelectedPosition = 0;

    BadgeItem numberBadgeItem;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testbottom);
        ButterKnife.bind(this);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(android.R.drawable.ic_menu_send, "Home").setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(android.R.drawable.ic_menu_send, "Books").setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher, "").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(android.R.drawable.ic_menu_send, "Movies & TV").setActiveColorResource(R.color.brown))
                .addItem(new BottomNavigationItem(android.R.drawable.ic_menu_send, "Games").setActiveColorResource(R.color.grey))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);

        numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.blue)
                .setText("" + lastSelectedPosition)
                .setHideOnSelect(true);

        //设置ViewPager
        hostactivity_view_pager.setOffscreenPageLimit(6);
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        hostactivity_view_pager.setAdapter(myViewPagerAdapter);
    }

    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;
//        if (numberBadgeItem != null) {
//            numberBadgeItem.setText(Integer.toString(position));
//        }
        hostactivity_view_pager.setCurrentItem(position, false);
    }

    @Override
    public void onTabUnselected(int position) {
    }

    @Override
    public void onTabReselected(int position) {

    }

    class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // 每个条目返回的fragment
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return FragmentFactory.creatFragment(0, TestBottomActivity.this);
            } else if (position == 1) {
                return FragmentFactory.creatFragment(1, TestBottomActivity.this);
            } else if (position == 2) {
                return FragmentFactory.creatFragment(2, TestBottomActivity.this);
            }  else if (position == 3) {
                return FragmentFactory.creatFragment(3, TestBottomActivity.this);
            }else {
                return FragmentFactory.creatFragment(3, TestBottomActivity.this);
            }
        }
        @Override
        public int getCount() {
            return 5;
        }
    }


}
