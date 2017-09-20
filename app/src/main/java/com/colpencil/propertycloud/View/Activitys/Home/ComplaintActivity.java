package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.ComplaintType;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Present.Home.ComplaintPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.CheckPermissionsActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Fragments.Home.ComplaintFragment;
import com.colpencil.propertycloud.View.Fragments.Home.PraiseFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * @author 汪 亮
 * @Description: 物业投诉
 * @Email DramaScript@outlook.com
 * @date 2016/9/5
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_complaint
)
public class ComplaintActivity extends CheckPermissionsActivity implements OnClickListener, RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.viewpager)
    NoScrollViewPager viewpager;

    @Bind(R.id.segmented)
    SegmentedGroup segmented;

    @Bind(R.id.button1)
    RadioButton button1;

    @Bind(R.id.button2)
    RadioButton button2;

    private ComplaintPresent present;
    private Intent intent;

    public static final int IMAGE_ITEM_ADD = -1;

    private List<Fragment> fragments = new ArrayList<>();

    private MyPagerAdapter adapter;

    private int mPos = 0;

    @Override
    protected void initViews(View view) {
        tv_rigth.setVisibility(View.VISIBLE);
        tv_rigth.setText("投诉记录");

        fragments.add(new ComplaintFragment());
        fragments.add(new PraiseFragment());

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        button1.setChecked(true);
        viewpager.setCurrentItem(0);

        ll_left.setOnClickListener(this);
        tv_rigth.setOnClickListener(this);
        segmented.setOnCheckedChangeListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new ComplaintPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:  // 返回
                finish();
                break;
            case R.id.tv_rigth: // 历史投诉
                intent = new Intent(this, ComplaintHistroyActivity.class);
                intent.putExtra("flag", 1);
                intent.putExtra("mPos", mPos);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.button1) {
            button1.setChecked(true);
            viewpager.setCurrentItem(0, false);
            tv_rigth.setText("投诉记录");
            mPos = 0;
        } else {
            button2.setChecked(true);
            viewpager.setCurrentItem(1, false);
            tv_rigth.setText("表扬记录");
            mPos = 1;
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
