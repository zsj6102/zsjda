package com.colpencil.tenement.View.Activitys.Workbeach;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Bean.VillageBus;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Ui.BottomDialog;
import com.colpencil.tenement.View.Activitys.VillageSelectActivity;
import com.colpencil.tenement.View.Fragments.Workbeach.OwnerRepairFragment;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import butterknife.Bind;
import butterknife.OnClick;
import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * @author 陈宝
 * @Description:业主报修
 * @Email DramaScript@outlook.com
 * @date 2016/8/25
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_owner_repair)
public class OwnerRepairActivity extends VillageSelectActivity implements RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.segmented)
    SegmentedGroup group;

    @Bind(R.id.viewPager)
    ViewPager vp;

    @Bind(R.id.button1)
    RadioButton button1;

    @Bind(R.id.button2)
    RadioButton button2;

    @Bind(R.id.button3)
    RadioButton button3;

    @Bind(R.id.button4)
    RadioButton button4;

    @Bind(R.id.iv_down)
    ImageView iv_down;

    @Bind(R.id.ll_title)
    LinearLayout ll_title;

    private MyPagerAdapter adapter;

    @Override
    protected void initViews(View view) {
        setStatusBaropen(false);
        tv_title.setText(getString(R.string.owner_repair_title));
        tv_rigth.setText("筛选");
        setFlag(0);
        String plot = SharedPreferencesUtil.getInstance(this).getString("plot");
//        tv_title.setText(R.string.title_owner_repair);
        tv_rigth.setVisibility(View.VISIBLE);
        iv_down.setVisibility(View.GONE);
        group.setOnCheckedChangeListener(this);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
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
                    default:
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
                vp.setCurrentItem(0);
                break;
            case R.id.button2:
                vp.setCurrentItem(1);
                break;
            case R.id.button3:
                vp.setCurrentItem(2);
                break;
            case R.id.button4:
                vp.setCurrentItem(3);
            default:
                break;
        }
    }


    @OnClick(R.id.ll_left)
    void backOnClick() {
        finish();
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return OwnerRepairFragment.newInstance(0);
            } else if (position == 1) {
                return OwnerRepairFragment.newInstance(1);
            } else if (position == 2){
                return OwnerRepairFragment.newInstance(2);
            } else {
                return OwnerRepairFragment.newInstance(3);
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
