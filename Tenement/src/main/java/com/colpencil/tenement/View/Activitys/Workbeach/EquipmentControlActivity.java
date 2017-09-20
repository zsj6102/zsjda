package com.colpencil.tenement.View.Activitys.Workbeach;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.tenement.Bean.Event;
import com.colpencil.tenement.Bean.RxEquiment;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Ui.BottomDialog;
import com.colpencil.tenement.View.Activitys.VillageSelectActivity;
import com.colpencil.tenement.View.Fragments.Workbeach.EquipmentFragment;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.UITools;

import org.feezu.liuli.timeselector.TimeSelector;

import butterknife.Bind;
import info.hoang8f.android.segmented.SegmentedGroup;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 设备管理界面
 * @Email DramaScript@outlook.com
 * @date 2016/8/23
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_equipment
)
public class EquipmentControlActivity extends VillageSelectActivity implements RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.segmented_equipment)
    SegmentedGroup segmented_equipment;

    @Bind(R.id.button1)
    RadioButton button1;

    @Bind(R.id.button2)
    RadioButton button2;

    @Bind(R.id.button3)
    RadioButton button3;

    @Bind(R.id.vp_equipment)
    ViewPager vp_equipment;

    private MyViewPagerAdapter pagerAdapter;

    public static final int EQUIPMENT_POLING = 0; // 巡检
    public static final int EQUIPMENT_REPAIL = 1;  //维修
    public static final int EQUIPMENT_KEEP = 2;   // 保养
    private Observable<Event> start;
    private DialogPlus plus;
    private EditText et_id;
    private EditText et_name;
    private TextView tv_village;
    private String communityId = "";
    private boolean isSee = false;  // false  不可以  true  可以
    private int self = 0;
    private String devCode;
    private String devName;
    private RelativeLayout rl_select_time;
    private RelativeLayout rl_select_time2;
    private TextView tv_endTime;
    private TextView tv_beginTime;
    private String beginTime = "";
    private String endTime = "";
    private String plot = "";


    @Override
    protected void initViews(View view) {
        setStatusBaropen(false);
        start = RxBus.get().register("start", Event.class);
        tv_rigth.setVisibility(View.VISIBLE);
        tv_rigth.setText("筛选");
        tv_title.setText("设备管理");
        button1.setChecked(true);
        segmented_equipment.setOnCheckedChangeListener(this);
        pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        vp_equipment.setAdapter(pagerAdapter);
        vp_equipment.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                        break;
                    case 1:
                        button1.setChecked(false);
                        button2.setChecked(true);
                        button3.setChecked(false);
                        break;
                    case 2:
                        button1.setChecked(false);
                        button2.setChecked(false);
                        button3.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Subscriber<Event> subscriber = new Subscriber<Event>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Event event) {
                ColpencilLogger.e("---" + event.getFlag());
                switch (event.getFlag()) {
                    case 1:
                        vp_equipment.setCurrentItem(0);
                        break;
                    case 2:
                        vp_equipment.setCurrentItem(1);
                        break;
                    case 3:
                        vp_equipment.setCurrentItem(2);
                        break;
                }
            }
        };
        start.subscribe(subscriber);

        ll_rigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTop();
            }
        });

    }

    /**
     * 顶部筛选
     */
    private void showTop() {
        setFlag(1);
        View view = View.inflate(EquipmentControlActivity.this, R.layout.dialog_select_equipment, null);
        plus = DialogPlus.newDialog(EquipmentControlActivity.this)
                .setContentHolder(new ViewHolder(view))
                .setCancelable(true)
                .setGravity(Gravity.TOP)
                .setExpanded(true, UITools.convertDpToPixel(330))
                .create();
        plus.show();
        RelativeLayout rl_select_villige = (RelativeLayout) view.findViewById(R.id.rl_select_villige);
        rl_select_time = (RelativeLayout) view.findViewById(R.id.rl_select_time1);
        rl_select_time2 = (RelativeLayout) view.findViewById(R.id.rl_select_time2);
        RelativeLayout ll_is_see = (RelativeLayout) view.findViewById(R.id.ll_is_see);
        ImageView iv_is_see = (ImageView) view.findViewById(R.id.iv_is_see);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Button btn_clear = (Button) view.findViewById(R.id.btn_clear);
        tv_village = (TextView) view.findViewById(R.id.tv_village);
        tv_village.setText(plot);
        tv_beginTime = (TextView) view.findViewById(R.id.tv_beginTime);
        tv_beginTime.setText(beginTime);
        tv_endTime = (TextView) view.findViewById(R.id.tv_endTime);
        tv_endTime.setText(endTime);
        et_id = (EditText) view.findViewById(R.id.et_id);
        et_id.setText(devCode);
        et_name = (EditText) view.findViewById(R.id.et_name);
        et_name.setText(devName);
        rl_select_villige.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVilageSelectBottom();
            }
        });
        setButtonCallback(new BottomDialog.ButtonCallback() {
            @Override
            public void onClick(@NonNull BottomDialog dialog) {
                tv_village.setText(lists.get(dialog.position).plot);
                plot = lists.get(dialog.position).plot;
                communityId = lists.get(dialog.position).plotId;
            }
        });
        if (isSee==false){
            iv_is_see.setImageDrawable(getResources().getDrawable(R.mipmap.un_open));
        }else {
            iv_is_see.setImageDrawable(getResources().getDrawable(R.mipmap.open));
        }
        ll_is_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSee==true){
                    iv_is_see.setImageDrawable(getResources().getDrawable(R.mipmap.un_open));
                    isSee = false;
                    self = 0;
                }else {
                    iv_is_see.setImageDrawable(getResources().getDrawable(R.mipmap.open));
                    isSee = true;
                    self = 1;
                }
            }
        });
        rl_select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeSelector timeSelector = new TimeSelector(EquipmentControlActivity.this, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        tv_beginTime.setText(time.substring(0, 10).replace("-", "-"));
                        beginTime = time.substring(0, 10).replace("-", "-");
                    }
                }, "2016-10-15 21:54", "2019-11-29 21:54");
                timeSelector.setMode(TimeSelector.MODE.YMD);
                timeSelector.show();
            }
        });
        rl_select_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeSelector timeSelector = new TimeSelector(EquipmentControlActivity.this, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        tv_endTime.setText(time.substring(0, 10).replace("-", "-"));
                        endTime = time.substring(0, 10).replace("-", "-");
                    }
                }, "2016-10-15 21:54", "2019-11-29 21:54");
                timeSelector.setMode(TimeSelector.MODE.YMD);
                timeSelector.show();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/10/14 筛选完毕发送通知
                devCode = et_id.getText().toString();
                devName = et_name.getText().toString();
                RxEquiment rx = new RxEquiment();
                rx.setFlag(0);
                rx.setCommunityId(communityId);
                rx.setDevCode(devCode);
                rx.setDevName(devName);
                rx.setSelf(self);
                rx.setStartDate(beginTime);
                rx.setEndDate(endTime);
                RxBus.get().post("eq",rx);
                plus.dismiss();
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_village.setText("");
                tv_beginTime.setText("");
                tv_endTime.setText("");
                et_id.setText("");
                et_name.setText("");
                plot = "";
                communityId = "";
                devCode = "";
                devName = "";
                self = 0;
                beginTime = "";
                endTime = "";
                iv_is_see.setImageDrawable(getResources().getDrawable(R.mipmap.un_open));
                isSee = false;
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
                vp_equipment.setCurrentItem(0);
                break;
            case R.id.button2:
                vp_equipment.setCurrentItem(1);
                break;
            case R.id.button3:
                vp_equipment.setCurrentItem(2);
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
                return EquipmentFragment.newInstance(EQUIPMENT_POLING);
            } else if (position == 1) {
                return EquipmentFragment.newInstance(EQUIPMENT_REPAIL);
            } else {
                return EquipmentFragment.newInstance(EQUIPMENT_KEEP);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
