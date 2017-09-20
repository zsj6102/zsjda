package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Fragments.CloseManager.ComplainHistoryFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * @author 汪 亮
 * @Description: 历史投诉
 * @Email DramaScript@outlook.com
 * @date 2016/9/5
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_complain_history
)
public class ComplaintHistroyActivity extends ColpencilActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.segmented)
    SegmentedGroup segmented;

    @Bind(R.id.button1)
    RadioButton button1;

    @Bind(R.id.button2)
    RadioButton button2;

    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private MyPagerAdapter adapter;
    private List<Fragment> fragments = new ArrayList<>();
    private int mPos;

    @Override
    protected void initViews(View view) {
        mPos = getIntent().getIntExtra("mPos", 0);
        ll_left.setOnClickListener(this);
        segmented.setOnCheckedChangeListener(this);
        fragments.add(ComplainHistoryFragment.newInstance(0));
        fragments.add(ComplainHistoryFragment.newInstance(1));
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        if (mPos == 0) {
            viewpager.setCurrentItem(0);
            button1.setChecked(true);
        } else {
            viewpager.setCurrentItem(1);
            button2.setChecked(true);
        }
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        button1.setChecked(true);
                        button2.setChecked(false);
                        break;
                    case 1:
                        button1.setChecked(false);
                        button2.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
        int id = v.getId();
        if (id == R.id.ll_left) {
            if (getIntent().getIntExtra("type", 0) != 1) {
                Intent intent = new Intent(this, ComplaintActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.button1) {
            button1.setChecked(true);
            viewpager.setCurrentItem(0, false);
        } else {
            button2.setChecked(true);
            viewpager.setCurrentItem(1, false);
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (getIntent().getIntExtra("type", 0) != 1) {
                Intent intent = new Intent(this, ComplaintActivity.class);
                startActivity(intent);
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
