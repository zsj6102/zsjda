package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.CheckPermissionsActivity;
import com.colpencil.propertycloud.View.Activitys.Home.RepairsHistoryActivity;
import com.colpencil.propertycloud.View.Fragments.CloseManager.CommonRepairFragment;
import com.colpencil.propertycloud.View.Fragments.CloseManager.FamilyRepairFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * @author 汪 亮
 * @Description: 物业报修
 * @Email DramaScript@outlook.com
 * @date 2016/9/9
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_repairs
)
public class TenementRepairsActivity extends CheckPermissionsActivity implements OnClickListener, RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;
    @Bind(R.id.tv_rigth)
    TextView tv_rigth;
    @Bind(R.id.scrollview)
    NoScrollViewPager viewPager;
    @Bind(R.id.segmented)
    SegmentedGroup segmented;
    @Bind(R.id.button1)
    RadioButton button1;
    @Bind(R.id.button2)
    RadioButton button2;

    private List<Fragment> fragments = new ArrayList<>();
    private MyPagerAdapter adapter;

    @Override
    protected void initViews(View view) {
        tv_rigth.setText("报修记录");
        tv_rigth.setVisibility(View.VISIBLE);
        ll_left.setOnClickListener(this);
        ll_rigth.setOnClickListener(this);
        fragments.add(new FamilyRepairFragment());
        fragments.add(new CommonRepairFragment());
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        segmented.setOnCheckedChangeListener(this);
        button1.setChecked(true);
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
            finish();
        } else if (id == R.id.ll_rigth) {
            Intent intent = new Intent(this, RepairsHistoryActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.button1) {
            button1.setChecked(true);
            viewPager.setCurrentItem(0, false);
        } else {
            button2.setChecked(true);
            viewPager.setCurrentItem(1, false);
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
}
