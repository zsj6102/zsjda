package com.colpencil.tenement.View.Fragments.TodayTask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.ToayTask.AddTaskActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.UITools;

import butterknife.Bind;
import info.hoang8f.android.segmented.SegmentedGroup;


/**
 * @author C B
 * @Description:
 * @Email DramaScript@outlook.com
 * @date 2016/6/30
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_today_task
)
public class TodayTaskFragment extends ColpencilFragment implements OnCheckedChangeListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.segmented)
    SegmentedGroup group;

    @Bind(R.id.viewPager)
    ViewPager vp;

    @Bind(R.id.button1)
    RadioButton button1;

    @Bind(R.id.button2)
    RadioButton button2;

    @Bind(R.id.tv_time)
    TextView tv_time;

    private MyPagerAdapter adapter;

    @Override
    protected void initViews(View view) {


        ll_left.setVisibility(View.INVISIBLE);
        tv_title.setText(getString(R.string.today_task_title));
        tv_rigth.setVisibility(View.VISIBLE);
        tv_rigth.setText("添加任务");
        tv_time.setText(TimeUtil.getTimeStrFromMillisHour(System.currentTimeMillis()));
        group.setOnCheckedChangeListener(this);
        adapter = new MyPagerAdapter(getChildFragmentManager());
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(3);
        vp.setOnPageChangeListener(new OnPageChangeListener() {
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

        ll_rigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddTaskActivity.class));
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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.button1:
                vp.setCurrentItem(0);
                break;
            case R.id.button2:
                vp.setCurrentItem(1);
                break;
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return TodayTaskItemFragment.newInstance(0);
            } else {
                return TodayTaskItemFragment.newInstance(1);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}

