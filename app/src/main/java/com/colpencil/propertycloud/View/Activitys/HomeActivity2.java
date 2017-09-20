package com.colpencil.propertycloud.View.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.WarnDialog;
import com.colpencil.propertycloud.View.Activitys.OpenDoor.OpenDoorActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Fragments.Fragment2;
import com.colpencil.propertycloud.View.Fragments.Fragment3;
import com.colpencil.propertycloud.View.Fragments.Home.HomeFragment;
import com.colpencil.propertycloud.View.Fragments.Intimate.IntimateFragment;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.StatusBarUtil;
import com.yinghe.whiteboardlib.bean.Image;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

public class HomeActivity2 extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.content)
    RelativeLayout content;

    @Bind(R.id.menu_iv)
    ImageView menu_iv;

    @Bind(R.id.home)
    TextView home;

    @Bind(R.id.life)
    TextView life;

    @Bind(R.id.lin)
    TextView lin;

    @Bind(R.id.manager)
    TextView manager;

    private List<Fragment> fragments;
    private HomeFragment homeFragment;
    private IntimateFragment intimateFragment;
    private Fragment3 fragment3;
    private Fragment2 fragment2;
    private List<TextView> views;
    //当前选中的views的下标
    private int currentIndex = 0;
    //旧的views下标
    private int oldIndex = 0;
    private boolean isMenuSelect = false;

    private ColpencilFrame instance;
    private Observable<RxBusMsg> message;
    private boolean isProprietor;
    private boolean isLogin;
    private String propertytel;
    private String servicetel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        isProprietor = SharedPreferencesUtil.getInstance(HomeActivity2.this).getBoolean("isProprietor", false);
        isLogin = SharedPreferencesUtil.getInstance(HomeActivity2.this).getBoolean("isLogin", false);
        propertytel = SharedPreferencesUtil.getInstance(HomeActivity2.this).getString("propertytel");
        servicetel = SharedPreferencesUtil.getInstance(HomeActivity2.this).getString("servicetel");
        instance = ColpencilFrame.getInstance();
        instance.addActivity(this);
        StatusBarUtil.setTranslucent(this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_pink));
        if (savedInstanceState == null) {
            initFragments();
            initViews();
        }

        message = RxBus.get().register("message", RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                if (rxBusMsg.getType()==1){
                    String content = rxBusMsg.getContent();
                    String title = rxBusMsg.getTitle();
                    WarnDialog.showNotif(HomeActivity2.this,title,content);
                }
            }
        };
        message.subscribe(subscriber);
    }

    private void initViews() {
        home.setOnClickListener(this);
        life.setOnClickListener(this);
        lin.setOnClickListener(this);
        manager.setOnClickListener(this);

        // 默认第一个为选中状态
        home.setSelected(true);
        views = new ArrayList<TextView>();
        views.add(home);
        views.add(life);
        views.add(lin);
        views.add(manager);

        menu_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin) {
                    if (isProprietor) {
                        Intent intent = new Intent(HomeActivity2.this, OpenDoorActivity.class);
                        startActivity(intent);
                    }else {
                        WarnDialog.show(HomeActivity2.this, propertytel, servicetel);
                    }
                }else {
                    startActivity(new Intent(HomeActivity2.this, LoginActivity.class));
                }
            }
        });
    }

    private void initFragments() {
        homeFragment = new HomeFragment();
        intimateFragment = new IntimateFragment();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        fragments = new ArrayList<Fragment>();
        fragments.add(homeFragment);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(intimateFragment);
//        默认加载前两个Fragment，其中第一个展示，第二个隐藏
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content,homeFragment)
                .add(R.id.content,fragment2)
                .hide(fragment2)
                .show(homeFragment)
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home:
                currentIndex = 0;
                break;
            case R.id.life:
                currentIndex = 1;
                break;
            case R.id.lin:
                currentIndex = 2;
                break;
            case R.id.manager:
                currentIndex = 3;
                break;
        }
        // 规避策略将凸起的view还原
        menu_iv.setSelected(false);
        isMenuSelect = false;

        showCurrentFragment(currentIndex);
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                instance.finishAllActivity();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 展示当前选中的Fragment
     * @param currentIndex
     */
    private void showCurrentFragment(int currentIndex) {
        if (currentIndex != oldIndex){
            views.get(oldIndex).setSelected(false);
            views.get(currentIndex).setSelected(true);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(fragments.get(oldIndex));
            if (!fragments.get(currentIndex).isAdded()){
                ft.add(R.id.content,fragments.get(currentIndex));
            }
            ft.show(fragments.get(currentIndex)).commit();
            oldIndex = currentIndex;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("message",message);
    }
}
