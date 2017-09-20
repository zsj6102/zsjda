package com.colpencil.tenement.View.Activitys.Workbeach;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.tenement.Bean.VillageBus;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Ui.BottomDialog;
import com.colpencil.tenement.View.Activitys.VillageSelectActivity;
import com.colpencil.tenement.View.Fragments.Workbeach.AdviceFragment;
import com.orhanobut.dialogplus.DialogPlus;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import butterknife.Bind;
import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * @Description:
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/26
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_advice
)
public class AdviceActivity extends VillageSelectActivity implements RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.segmented_advice)
    SegmentedGroup segmented_advice;

    @Bind(R.id.button1)
    RadioButton button1;

    @Bind(R.id.button2)
    RadioButton button2;

    @Bind(R.id.button3)
    RadioButton button3;

    @Bind(R.id.button4)
    RadioButton button4;

    @Bind(R.id.vp_advice)
    ViewPager vp_advice;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.iv_down)
    ImageView iv_down;

    @Bind(R.id.ll_title)
    LinearLayout ll_title;

    private MyViewPagerAdapter pagerAdapter;

    public static final int EQUIPMENT_UN = 1; // 待处理
    public static final int EQUIPMENT_ING = 2;  // 处理中
    public static final int EQUIPMENT_END = 3;   // 已完成
    private static final int EQUIPMENT_ASSIGN = 0; //待指派

    @Override
    protected void initViews(View view) {
        setStatusBaropen(false);
        tv_title.setText("投诉建议");
        setFlag(0);
        String plot = SharedPreferencesUtil.getInstance(this).getString("plot");
//        tv_title.setText("全部小区");
        tv_rigth.setVisibility(View.VISIBLE);
        iv_down.setVisibility(View.GONE);
        tv_rigth.setText("筛选");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button1.setChecked(true);
        segmented_advice.setOnCheckedChangeListener(this);
        pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        vp_advice.setAdapter(pagerAdapter);
        vp_advice.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        button1.setChecked(true);
                        button2.setChecked(false);
                        button3.setChecked(false);
                        button4.setChecked(false);
                        break;
                    case 1:
                        button1.setChecked(false);
                        button2.setChecked(true);
                        button3.setChecked(false);
                        button4.setChecked(false);
                        break;
                    case 2:
                        button1.setChecked(false);
                        button2.setChecked(false);
                        button3.setChecked(true);
                        button4.setChecked(false);
                        break;
                    case 3:
                        button1.setChecked(false);
                        button2.setChecked(false);
                        button3.setChecked(false);
                        button4.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tv_rigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTopDialog();
            }
        });
    }


    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.button1:
                vp_advice.setCurrentItem(0);
                break;
            case R.id.button2:
                vp_advice.setCurrentItem(1);
                break;
            case R.id.button3:
                vp_advice.setCurrentItem(2);
                break;
            case R.id.button4:
                vp_advice.setCurrentItem(3);
                break;
        }
    }

    class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // 每个条目返回的fragment
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return AdviceFragment.newInstance(EQUIPMENT_ASSIGN);
            } else if (position == 1) {
                return AdviceFragment.newInstance(EQUIPMENT_UN);
            } else if(position == 2) {
                return AdviceFragment.newInstance(EQUIPMENT_ING);
            } else {
                return AdviceFragment.newInstance(EQUIPMENT_END);
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
