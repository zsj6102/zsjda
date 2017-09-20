package com.colpencil.tenement.View.Activitys;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Fragments.FragmentFactory;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColBottomNavigation.ColBottomNavigation;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColBottomNavigation.ColBottomNavigationItem;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColBottomNavigation.ColBottomNavigationViewPager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Description: 主界面
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/7/25
 */
public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.hostactivity_view_pager)
    protected ColBottomNavigationViewPager hostactivity_view_pager;

    @Bind(R.id.hostactivity_bottom_navigation)
    protected ColBottomNavigation hostactivity_bottom_navigation;

    private ArrayList<ColBottomNavigationItem> bottomNavigationItems = new ArrayList<>();

    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initBottom();
    }

    /**
     * 设置导航栏
     */
    private void initBottom() {
        //设置导航栏的内容（图片，文字，背景颜色）
        ColBottomNavigationItem item1 = new ColBottomNavigationItem(R.string.tab_1, R.mipmap.work_beach, R.color.mian_blue);
        ColBottomNavigationItem item2 = new ColBottomNavigationItem(R.string.tab_2, R.mipmap.work_beach, R.color.mian_blue);
        ColBottomNavigationItem item3 = new ColBottomNavigationItem(R.string.tab_3, R.mipmap.work_beach, R.color.mian_blue);
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        hostactivity_bottom_navigation.addItems(bottomNavigationItems);
        //导航栏颜色设置
        hostactivity_bottom_navigation.setAccentColor(Color.parseColor("#12b7f5"));//当前fragment指定菜单图标与字体颜色
        hostactivity_bottom_navigation.setInactiveColor(Color.parseColor("#cccccc"));//未指向图标颜色
//        hostactivity_bottom_navigation.setColored(true);
        hostactivity_bottom_navigation.setOnTabSelectedListener(new ColBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                //Fragment 变化
                hostactivity_view_pager.setCurrentItem(position, false);
                if(position==0){
                    //若需要取消消息，则设置"" ,不可传空null。
                    setMessage ("",0);
                }
                return true;
            }
        });
        //设置ViewPager
        hostactivity_view_pager.setOffscreenPageLimit(5);
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        hostactivity_view_pager.setAdapter(myViewPagerAdapter);
        setMessage("l2",0);
    }

    /**
     * 发送消息
     * @param message 显示文本内容
     * @param position 第几个菜单显示
     */
    private void setMessage(String message,int position){
        hostactivity_bottom_navigation.setNotification(message, position);
    }

    class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // 每个条目返回的fragment
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return FragmentFactory.creatFragment(0, HomeActivity.this);
            } else if (position == 1) {
                return FragmentFactory.creatFragment(1, HomeActivity.this);
            } else {
                return FragmentFactory.creatFragment(2, HomeActivity.this);
            }
        }
        @Override
        public int getCount() {
            return 3;
        }
    }
}