package com.colpencil.propertycloud.View.Activitys;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.CellState;
import com.colpencil.propertycloud.Bean.KeyList;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Bean.VersionInfo;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.VersionUtils;
import com.colpencil.propertycloud.Tools.WarnDialog;
import com.colpencil.propertycloud.Ui.BaseDialog;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Fragments.FragmentFactory;
import com.google.gson.Gson;
import com.izhihuicheng.api.lling.LLingOpenDoorConfig;
import com.izhihuicheng.api.lling.LLingOpenDoorHandler;
import com.izhihuicheng.api.lling.LLingOpenDoorStateListener;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ClickUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NetUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

;

/**
 * @author 汪 亮
 * @Description: 主界面
 * @Email DramaScript@outlook.com
 * @date 2016/7/30
 */
public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.vp)
    ViewPager vp;

    @Bind(R.id.rg_group)
    RadioGroup rg_group;

    @Bind(R.id.rb_main)
    RadioButton rb_main;

    @Bind(R.id.rb_life)
    RadioButton rb_life;

    @Bind(R.id.rb_home)
    RadioButton rb_home;

    @Bind(R.id.rb_lin)
    RadioButton rb_lin;

    @Bind(R.id.rb_wuye)
    RadioButton rb_wuye;

    private MyViewPagerAdapter myViewPagerAdapter;
    private ColpencilFrame instance;
    private Observable<RxBusMsg> message;

    private Gson gson;

    private SoundPool soundPool;

    private List<String> keyList = new ArrayList<>();
    public static final int RS_OD_SUCCESS = 1;
    public static final int RS_OD_FAILD = 2;
    public static final int RS_OD_ERROR = 3;
    public static final int RS_CONN_ERROR = 4;
    public static final int RS_CONN_NOFOUND = 5;

    private String snn = "";

    private boolean flag = false;

    private int lastPosition = 0;

    private boolean open = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ToastTools.showShort(HomeActivity.this, true, "开门成功！");
                    log(snn);
                    soundPool.load(HomeActivity.this, R.raw.collide, 100);
                    soundPool.play(1, 1, 1, 0, 0, 1);
                    DialogTools.dissmiss();
                    MobclickAgent.onEvent(HomeActivity.this, "openDoorSuceess");
                    break;
                case 1:
                    MobclickAgent.onEvent(HomeActivity.this, "openDoorFail");
                    ToastTools.showShort(HomeActivity.this, false, "设备连接失败！");
                    DialogTools.dissmiss();
                    break;
                case 2:
                    ToastTools.showShort(HomeActivity.this, false, "设备未找到！");
                    DialogTools.dissmiss();
                    break;
                case 3:
                    ToastTools.showShort(HomeActivity.this, false, "开门异常！");
                    DialogTools.dissmiss();
                    break;
                case 4:
                    ToastTools.showShort(HomeActivity.this, false, "开门失败！");
                    DialogTools.dissmiss();
                    break;
            }
        }
    };
    private boolean back = false;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        type = getIntent().getIntExtra("type", 0);
//        update();
        checkVersion();
//        ToastTools.showShort(this,"我是增量更新后的！");
        instance = ColpencilFrame.getInstance();
        instance.addActivity(this);
        StatusBarUtil.setTranslucent(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.main_pink));
        vp.setOffscreenPageLimit(6);
        vp.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));

        if (type == 2) {
            vp.setCurrentItem(3);
            rb_wuye.setChecked(true);
        }

//        rb_life.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, ApiCloudActivity.class);
//                intent.putExtra("startUrl", HostUrl.BASE_HOST_PATH + "/property/preferred_life/preferred_life.do");
//                startActivity(intent);
//                if (lastPosition == 0) {
//                    rb_home.setChecked(true);
//                } else if (lastPosition == 2) {
//                    rb_lin.setChecked(true);
//                } else {
//                    rb_wuye.setChecked(true);
//                }
//            }
//        });

        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        lastPosition = 0;
                        vp.setCurrentItem(0, false);
                        break;
                    case R.id.rb_life:
                        lastPosition = 1;
                        vp.setCurrentItem(1, false);
                        break;
                    case R.id.rb_lin:
                        lastPosition = 2;
                        vp.setCurrentItem(2, false);
                        break;
                    case R.id.rb_wuye:
                        lastPosition = 3;
                        vp.setCurrentItem(3, false);
                        break;
                }
            }
        });

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
                ColpencilLogger.e("--------------切换了=" + rxBusMsg.getType());
                final String url = rxBusMsg.getUrl();
                switch (rxBusMsg.getType()) {
                    case 1:
                        String content = rxBusMsg.getContent();
                        String title = rxBusMsg.getTitle();
                        WarnDialog.showNotif(HomeActivity.this, title, content);
                        break;
                    case 2:
                        ColpencilLogger.e("----------收到红包！");
                        BaseDialog baseDialog = BaseDialog.getInstance(0, new BaseDialog.BaseDialogListener() {
                            @Override
                            public void ok(int type, int resultCode) {
                                Intent i = new Intent();
                                i.setClass(HomeActivity.this, ApiCloudActivity.class);
                                i.putExtra("startUrl", url);
                                startActivity(i);
                                ColpencilLogger.e("startUrl:" + url);
                                RxBusMsg rxBusMsg1 = new RxBusMsg();
                                rxBusMsg1.setType(4);
                                RxBus.get().post("message", rxBusMsg1);
                            }

                            @Override
                            public void cancel(int type, int resultCode) {

                            }
                        }, rxBusMsg.getLogo(), Integer.valueOf(rxBusMsg.getsType()));
                        baseDialog
                                .bulidBackground(Color.WHITE)
                                .bulidContent(rxBusMsg.getOrganizer(), getResources().getColor(R.color.red));
                        baseDialog.show(getSupportFragmentManager(), "");
                        break;
                }
            }
        };
        message.subscribe(subscriber);
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        gson = new Gson();
        getOpenKey();

        rb_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickUtils.isFastDoubleClick()) {
                    return;
                }
                if (SharedPreferencesUtil.getInstance(HomeActivity.this).getBoolean("isLogin", false)) {
                    if (NetUtils.isConnected(HomeActivity.this)) {
                        HomeActivity.this.flag = true;
                        open = true;
                        loadState();
                    } else {
                        if (SharedPreferencesUtil.getInstance(HomeActivity.this).getBoolean("isProprietor", false)) {
                            openDoor();
                        } else {
                            WarnDialog.show(HomeActivity.this, SharedPreferencesUtil.getInstance(HomeActivity.this).getString("propertytel"), SharedPreferencesUtil.getInstance(HomeActivity.this).getString("servicetel"));
                        }
                    }

                } else {
                    Intent mIntent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(mIntent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Subscribe
    public void onEvent(RxBusMsg rxBusMsg) {
        int flag = rxBusMsg.getType();
        final String url = rxBusMsg.getUrl();
        switch (flag) {
            case 0:
                ColpencilLogger.e("----------收到红包！");
                BaseDialog baseDialog = BaseDialog.getInstance(0, new BaseDialog.BaseDialogListener() {
                    @Override
                    public void ok(int type, int resultCode) {
                        Intent i = new Intent();
                        i.setClass(HomeActivity.this, ApiCloudActivity.class);
                        i.putExtra("startUrl", url);
                        startActivity(i);
                        ColpencilLogger.e("startUrl:" + url);
                    }

                    @Override
                    public void cancel(int type, int resultCode) {

                    }
                }, rxBusMsg.getLogo(), Integer.valueOf(rxBusMsg.getsType()));
                baseDialog
                        .bulidBackground(Color.WHITE)
                        .bulidContent(rxBusMsg.getOrganizer(), getResources().getColor(R.color.red));
                baseDialog.show(getSupportFragmentManager(), "");
                break;
        }
    }

    /**
     * 开门操作
     */
    private void openDoor() {
        DialogTools.showNoCancelLoding(this, "温馨提示", "正在开门中。。。");
        if (NetUtils.isConnected(this)) {
            if (keyList.size() == 0) {
                WarnDialog.showInfo(this, "");
                return;
            }
            String[] keys = new String[keyList.size()];
            for (int i = 0; i < keyList.size(); i++) {
                keys[i] = keyList.get(i);
            }
            LLingOpenDoorConfig config = new LLingOpenDoorConfig(9, keys);
            LLingOpenDoorHandler handler = LLingOpenDoorHandler
                    .getSingle(HomeActivity.this);
            handler.doOpenDoor(config, listener);
        } else {
            String keyJson = SharedPreferencesUtil.getInstance(this).getString("keys");
            if (!TextUtils.isEmpty(keyJson)) {
                KeyList jsonKeyList = gson.fromJson(keyJson, KeyList.class);
                String[] keys = new String[jsonKeyList.key.size()];
                for (int i = 0; i < jsonKeyList.key.size(); i++) {
                    keys[i] = jsonKeyList.key.get(i);
                }
                LLingOpenDoorConfig config = new LLingOpenDoorConfig(9, keys);
                LLingOpenDoorHandler handler = LLingOpenDoorHandler
                        .getSingle(HomeActivity.this);
                handler.doOpenDoor(config, listener);
            } else {
                ToastTools.showShort(this, false, "请先连接网络获取钥匙！");
            }

        }

    }

    private LLingOpenDoorStateListener listener = new LLingOpenDoorStateListener() {

        public void onOpenSuccess(String deviceKey, String sn, int openType) {
            Log.i("BORTURN", "开门结束：" + System.currentTimeMillis());
            Log.e("s", "sn" + sn);
            snn = sn;
            handler.sendEmptyMessage(0);
        }

        public void onOpenFaild(int errCode, int openType, String deviceKey,
                                String sn, String desc) {
            switch (errCode) {
                case RS_CONN_ERROR:
                    Log.i("BORTURN", "设备连接失败!");
                    ColpencilLogger.e("errCode=" + errCode);
                    handler.sendEmptyMessage(1);
                    break;
                case RS_CONN_NOFOUND:
                    Log.i("BORTURN", "设备未找到!");
                    handler.sendEmptyMessage(2);
                    break;
                case RS_OD_ERROR:
                    Log.i("BORTURN", "开门异常!");
                    handler.sendEmptyMessage(3);
                    break;
                case RS_OD_FAILD:
                    Log.i("BORTURN", "开门失败!");
                    handler.sendEmptyMessage(4);
                    break;
                default:
                    break;
            }
        }

        public void onConnectting(String deviceKey, String sn, int openType) {
            Log.i("BORTURN", "开始连接!");
        }

        public void onFoundDevice(String deviceKey, String sn, int openType) {
            Log.i("BORTURN", "找到可用的设备!");
        }

        public void onRunning() {
            Log.i("BORTURN", "onRunning");
        }

    };

    /**
     * 获取开门钥匙
     */
    private void getOpenKey() {
        HashMap<String, String> map = new HashMap<>();
        map.put("ownerType", 1 + "");
        RetrofitManager.getInstance(1, HomeActivity.this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .openKey(map)
                .map(new Func1<ResultListInfo<String>, ResultListInfo<String>>() {
                    @Override
                    public ResultListInfo<String> call(ResultListInfo<String> stringResultListInfo) {
                        return stringResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息" + e.getMessage());
                    }

                    @Override
                    public void onNext(final ResultListInfo<String> stringResultListInfo) {
                        String message = stringResultListInfo.message;
                        int code = stringResultListInfo.code;
                        switch (code) {
                            case 0:
                                keyList.clear();
                                keyList.addAll(stringResultListInfo.data);
                                KeyList kList = new KeyList();
                                for (String s : stringResultListInfo.data) {
                                    kList.key.add(s);
                                }
                                String json = gson.toJson(kList);
                                ColpencilLogger.e("keyJson：" + json);
                                SharedPreferencesUtil.getInstance(HomeActivity.this).setString("keys", json);
                                if (open) {
                                    openDoor();
                                }
                                break;
                            case 1:
                                ToastTools.showShort(HomeActivity.this, false, message);
                                break;
                            case 2:
                                if (flag) {
                                    WarnDialog.show(HomeActivity.this, SharedPreferencesUtil.getInstance(HomeActivity.this).getString("propertytel"), SharedPreferencesUtil.getInstance(HomeActivity.this).getString("servicetel"), message);
                                }
                                flag = false;
                                break;
                            case 3:
//                                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    /**
     * 开门成功向服务器发送日志
     */
    private void log(final String sn) {
        HashMap<String, String> map = new HashMap<>();
        map.put("ownerType", 1 + "");
        map.put("sn", sn);
        RetrofitManager.getInstance(1, HomeActivity.this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .log(map)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo resultInfo) {
                        int code = resultInfo.code;
                        String message = resultInfo.message;
                        switch (code) {
                            case 0:
                                snn = "";
                                break;
                            case 1:
                                ToastTools.showShort(HomeActivity.this, false, message);
                                break;
                            case 3:
//                                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
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
            } else if (position == 2) {
                return FragmentFactory.creatFragment(2, HomeActivity.this);
            } else {
                return FragmentFactory.creatFragment(3, HomeActivity.this);
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
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
                MobclickAgent.onKillProcess(this);
                instance.finishAllActivity();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("message", message);
        EventBus.getDefault().unregister(this);
    }

    public void loadState() {
        HashMap<String, String> params = new HashMap<>();
        params.put("communityId", SharedPreferencesUtil.getInstance(this).getString("comuid"));
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadCellState(params)
                .map(new Func1<ResultInfo<CellState>, ResultInfo<CellState>>() {
                    @Override
                    public ResultInfo<CellState> call(ResultInfo<CellState> loginInfoResultInfo) {
                        return loginInfoResultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<CellState>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ResultInfo<CellState> login) {
                        int code = login.code;
                        String message = login.message;
                        switch (code) {
                            case 0:
                                SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("propertytel", login.data.getPropertytel());
                                SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("servicetel", login.data.getServicetel());
                                if (login.data.isIsProprietor()) {
                                    getOpenKey();
                                } else {
                                    WarnDialog.show(HomeActivity.this, login.data.getPropertytel(), login.data.getServicetel());
                                }
                                break;
                            case 1:
                                ToastTools.showShort(HomeActivity.this, false, message);
                                break;
                            case 3:
                                Intent mIntent = new Intent(HomeActivity.this, LoginActivity.class);
                                mIntent.putExtra("requestLogin", 100);
                                startActivityForResult(mIntent, 1);
                                break;
                        }

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            if (requestCode == 1) {
                loadState();
            }
        }
    }

    /**
     * 检查版本更新
     */
    public void checkVersion() {
        HashMap<String, String> params = new HashMap<>();
        params.put("devType", "android");
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .checkVersion(params)
                .map(new Func1<ResultInfo<VersionInfo>, ResultInfo<VersionInfo>>() {
                    @Override
                    public ResultInfo<VersionInfo> call(ResultInfo<VersionInfo> versionInfoResultInfo) {
                        return versionInfoResultInfo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<VersionInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo<VersionInfo> stringResultInfo) {
                        if (stringResultInfo.code == 0) {
                            int versionCode = VersionUtils.getVersionCode(HomeActivity.this);
                            if (stringResultInfo.data.getVerCode() != null && versionCode < stringResultInfo.data.getVerCode()) {
                                WarnDialog.showUpdateDialog(HomeActivity.this, stringResultInfo.data.getUrl(), stringResultInfo.data.getDetail(), stringResultInfo.data.isForceUpdate());
                            }
                        }
                    }
                });
    }

    /**
     * 更新补丁
     */
    private void update() {
//        HashMap<String, String> map = new HashMap<>();
//        Observable<EntityResult<AnFixBean>> observable = RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
//                .createApi(HostApi.class)
//                .update(map)
//                .map(new Func1<EntityResult<AnFixBean>, EntityResult<AnFixBean>>() {
//                    @Override
//                    public EntityResult<AnFixBean> call(EntityResult<AnFixBean> anFixBeanEntityResult) {
//                        return anFixBeanEntityResult;
//                    }
//                }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//
//        Subscriber<EntityResult<AnFixBean>> subscriber = new Subscriber<EntityResult<AnFixBean>>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(EntityResult<AnFixBean> anFixBeanEntityResult) {
//                int code = anFixBeanEntityResult.getCode();
//                AnFixBean data = anFixBeanEntityResult.getData();
//                switch (code) {
//                    case 0:
//                        if (TextUtils.isEmpty(data.md5)) {
//                            ColpencilLogger.e("当前暂无更新！");
//                        } else {
//                            download(data.path);
//                            ColpencilLogger.e("下载地址：" + data.path);
//                        }
//                        break;
//                    case 1:
//                        DialogTools.dissmiss();
//                        ColpencilLogger.e("请求失败！");
//                        break;
//
//                }
//            }
//        };
//
//        observable.subscribe(subscriber);
    }

    //下载具体操作
    private void download(String downloadUrl) {
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
////                .addInterceptor(new LoggerInterceptor("TAG"))
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                //其他配置
//                .build();
//        OkHttpUtils.initClient(okHttpClient);
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/android_patch.apatch");
//        ColpencilLogger.e("路径：" + file.exists());
//        if (file.exists() == true) {
//            file.delete();
//            ColpencilLogger.e("路径：" + file.exists());
//        }
//        OkHttpUtils
//                .get()
//                .url(downloadUrl)
//                .build()
//                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "android_patch.apatch")//
//                {
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(File response, int id) {
//                        DialogTools.dissmiss();
//                        ColpencilLogger.e("更新成功，重新进入app，获得更好的体验！");
//                        if (new File(response.getAbsolutePath()).exists()) {
//                            ColpencilLogger.e("have some patch");
//                            try {
//                                CluodApplaction.getInstance().getPatchManager().addPatch(response.getAbsolutePath());
//                            } catch (Exception e) {
//                                ColpencilLogger.e("Test=" + e);
//                            }
//                        } else {
//                            ColpencilLogger.e("have no patch");
//                        }
//                    }
//                });
    }

//    public void showDialog(final VersionInfo info) {
//        View view = LayoutInflater.from(this).inflate(R.layout.dialog_check_version, null);
//        final AlertDialog dialog = new AlertDialog.Builder(this).create();
//        Window window = dialog.getWindow();
//        window.setContentView(view);
////        dialog.setCancelable(false);
//        WindowManager.LayoutParams lp = window.getAttributes();
//        DisplayMetrics d = getResources().getDisplayMetrics();// 获取屏幕尺寸
//        lp.width = (int) (d.widthPixels * 0.75); // 宽度为屏幕80%
//        lp.gravity = Gravity.CENTER; // 中央居中
//        window.setAttributes(lp);
//        ((TextView) view.findViewById(R.id.tv_message)).setText(info.getDetail());
//        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                Uri content_url = Uri.parse(info.getUrl());
//                intent.setData(content_url);
//                startActivity(intent);
//                System.exit(0);
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }
}
