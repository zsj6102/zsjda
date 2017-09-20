package com.colpencil.tenement.View.Activitys;

import android.Manifest;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.nearby.NearbySearch;
import com.amap.api.services.nearby.UploadInfo;
import com.amap.api.services.nearby.UploadInfoCallback;
import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.KeyList;
import com.colpencil.tenement.Bean.ResultInfo;
import com.colpencil.tenement.Bean.ResultListInfo;
import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Bean.VersionInfo;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Tools.VersionUtils;
import com.colpencil.tenement.Tools.WarnDialog;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Fragments.FragmentFactory;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.util.NetUtils;
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
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;

    @Bind(R.id.rg_group)
    RadioGroup rg_group;

    @Bind(R.id.iv_open_door)
    ImageView iv_open_door;

//    @Bind(R.id.rl_viewgroup)
//    RelativeLayout rl_viewgroup;

    @Bind(R.id.relativeLayout)
    RelativeLayout relativeLayout;

    @Bind(R.id.vp)
    ViewPager vp;

    private int screenWidth;

    private double radioWith = 4.32;
    private int radioMargin = 72;
    private double radioHigth = 20.21;
    private int screenHeight;
    private ColpencilFrame instance;
    private Observable<RxBusMsg> message;
    private String name;
    private String deviceId;

    private Gson gson;

    private SoundPool soundPool;

    private List<String> keyList = new ArrayList<>();
    public static final int RS_OD_SUCCESS = 1;
    public static final int RS_OD_FAILD = 2;
    public static final int RS_OD_ERROR = 3;
    public static final int RS_CONN_ERROR = 4;
    public static final int RS_CONN_NOFOUND = 5;

    private String snn = "";

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ToastTools.showShort(MainActivity.this, true, "开门成功！");
                    log(snn);
                    soundPool.load(MainActivity.this, R.raw.collide, 1);
                    soundPool.play(1, 1, 1, 0, 0, 1);
                    DialogTools.dissmiss();
                    break;
                case 1:
                    ToastTools.showShort(MainActivity.this, false, "设备连接失败！");
                    DialogTools.dissmiss();
                    break;
                case 2:
                    ToastTools.showShort(MainActivity.this, false, "设备未找到！");
                    DialogTools.dissmiss();
                    break;
                case 3:
                    ToastTools.showShort(MainActivity.this, false, "开门异常！");
                    DialogTools.dissmiss();
                    break;
                case 4:
                    ToastTools.showShort(MainActivity.this, false, "开门失败！");
                    DialogTools.dissmiss();
                    break;
            }

        }
    };
    private NearbySearch mNearbySearch;
    private String acount;
    private double latitude;
    private double longitude;

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        requestCodeQrcodePermissions();//请求权限
        initView();
        StatusBarUtil.setTranslucent(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.mian_blue));
        acount = SharedPreferencesUtil.getInstance(this).getString("acount");
        instance = ColpencilFrame.getInstance();
        instance.addActivity(this);
        mNearbySearch = NearbySearch.getInstance(getApplicationContext());
        checkVersion();

//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
////                .addInterceptor(new LoggerInterceptor("TAG"))
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                //其他配置
//                .build();
//        OkHttpUtils.initClient(okHttpClient);
//        update();
//        Toast.makeText(this,"我是更新后的！",Toast.LENGTH_SHORT).show();
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        gson = new Gson();
        getOpenKey();
        initLocation();
        vp.setOffscreenPageLimit(5);

    }

    /**
     * 初始化定位  上传自己的位置
     */
    private void initLocation() {
        //初始化定位
        mLocationClient = TenementApplication.getInstance().getmLocationClient();
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                        //获取纬度
                        latitude = amapLocation.getLatitude();
                        //获取经度
                        longitude = amapLocation.getLongitude();
                        amapLocation.getAccuracy();//获取精度信息
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(amapLocation.getTime());
                        df.format(date);//定位时间
                        amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                        amapLocation.getCountry();//国家信息
                        amapLocation.getProvince();//省信息
                        amapLocation.getCity();//城市信息
                        amapLocation.getDistrict();//城区信息
                        amapLocation.getStreet();//街道信息
                        amapLocation.getStreetNum();//街道门牌号信息
                        amapLocation.getCityCode();//城市编码
                        amapLocation.getAdCode();//地区编码
                        //获取定位信息
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
//                        Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                        ColpencilLogger.e("经纬度："+amapLocation.getLatitude()+","+ amapLocation.getLongitude());
                        mNearbySearch.startUploadNearbyInfoAuto(new UploadInfoCallback()  {
                            //设置自动上传数据和上传的间隔时间
                            @Override
                            public UploadInfo OnUploadInfoCallback() {
                                UploadInfo loadInfo = new UploadInfo();
                                loadInfo.setCoordType(NearbySearch.AMAP);
                                //位置信息
                                loadInfo.setPoint(new LatLonPoint(latitude, longitude));
                                //用户id信息
                                loadInfo.setUserID(acount);
                                ColpencilLogger.e("---------------上传位置中acount:"+acount);
                                return loadInfo;
                            }
                        },10000);
                    }else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            }
        };
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = TenementApplication.getInstance().getmLocationOption();
        mLocationClient.startLocation();
    }

    /**
     * 初始化view
     */
    private void initView() {

        /*screenWidth = ScreenUtil.getInstance().getScreenWidth(this);
        screenHeight = ScreenUtil.getInstance().getScreenHeight(this);
        int margin = screenWidth / radioMargin;
        int with = (int) (screenWidth / radioWith);

        ViewGroup.LayoutParams layoutParams = rg_group.getLayoutParams();
        layoutParams.width = UITools.convertDpToPixel(with);
        rg_group.setLayoutParams(layoutParams);

        ViewGroup.LayoutParams params = rl_viewgroup.getLayoutParams();
        params.height = UITools.convertDpToPixel(layoutParams.height);
        rl_viewgroup.setLayoutParams(params);

        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) iv_open_door.getLayoutParams();
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams1.setMargins(0, 0, UITools.convertDpToPixel(margin), 0);
        iv_open_door.setLayoutParams(layoutParams1);

        ViewGroup.LayoutParams layoutParams2 = vp.getLayoutParams();
        layoutParams2.height = UITools.convertDpToPixel((int) (screenHeight - screenWidth / radioHigth) - ScreenUtil.getInstance().getStatusHeight(this));
        vp.setLayoutParams(layoutParams2);

        ColpencilLogger.e("margin=" + margin + ",with=" + with + ",screenHeight=" + screenHeight + ",vp=" + (int) (screenWidth / radioHigth));*/

        vp.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        vp.setCurrentItem(0, false);
                        break;
                    case R.id.rb_my_course:
                        vp.setCurrentItem(1, false);
                        break;
                    case R.id.rb_messge:
                        vp.setCurrentItem(2, false);
                        break;
                }
            }
        });

//        //获取底部栏的高度
//        int height = rl_viewgroup.getLayoutParams().height;
//        ColpencilLogger.e("底部栏的高度：" + height);
//        RelativeLayout.LayoutParams layoutParamsVp = (RelativeLayout.LayoutParams) vp.getLayoutParams();
//        layoutParamsVp.setMargins(0, 0, 0, UITools.convertDpToPixel(height));
//        vp.setLayoutParams(layoutParamsVp);

        //注册一个监听连接状态的listener
        EMChatManager.getInstance().addConnectionListener(new MyConnectionListener());

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
                if (rxBusMsg.getType() == 3) {
                    // TODO: 2016/11/2 接收到长连接消息，进行界面数据的请求刷新
                    vp.setCurrentItem(2);
                } else if (rxBusMsg.getType() == 2) {
                    deviceId = SharedPreferencesUtil.getInstance(MainActivity.this).getString("deviceId");
                    name = rxBusMsg.getName();
                    loginOut();
                }else if (rxBusMsg.getType()==1){
                    String content = rxBusMsg.getContent();
                    String title = rxBusMsg.getTitle();
                    WarnDialog.showNotif(MainActivity.this,title,content);
                }
            }
        };
        message.subscribe(subscriber);

        iv_open_door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickUtils.isFastDoubleClick()){
                    return;
                }
                DialogTools.showNoCancelLoding(MainActivity.this,"温馨提示","开门中。。。");
                if (com.property.colpencil.colpencilandroidlibrary.Function.Tools.NetUtils.isConnected(MainActivity.this)){
                    getOpenKey();
                    openDoor();
                }else {
                    openDoor();
                }
            }
        });
    }

    /**
     * 开门操作
     */
    private void openDoor(){
        if (com.property.colpencil.colpencilandroidlibrary.Function.Tools.NetUtils.isConnected(this)){
            String[] keys = new String[keyList.size()];
            for (int i = 0; i < keyList.size(); i++) {
                keys[i] = keyList.get(i);
            }
            LLingOpenDoorConfig config = new LLingOpenDoorConfig(9, keys);
            LLingOpenDoorHandler handler = LLingOpenDoorHandler
                    .getSingle(MainActivity.this);
            handler.doOpenDoor(config, listener);
        }else {
            String keyJson = SharedPreferencesUtil.getInstance(this).getString("keys");
            if (!TextUtils.isEmpty(keyJson)){
                KeyList jsonKeyList = gson.fromJson(keyJson, KeyList.class);
                String[] keys = new String[jsonKeyList.key.size()];
                for (int i = 0; i < jsonKeyList.key.size(); i++) {
                    keys[i] = jsonKeyList.key.get(i);
                }
                LLingOpenDoorConfig config = new LLingOpenDoorConfig(9, keys);
                LLingOpenDoorHandler handler = LLingOpenDoorHandler
                        .getSingle(MainActivity.this);
                handler.doOpenDoor(config, listener);
            }else {
                ToastTools.showShort(this,false,"请先连接网络获取钥匙！");
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

        ;

        public void onOpenFaild(int errCode, int openType, String deviceKey,
                                String sn, String desc) {
            switch (errCode) {
                case RS_CONN_ERROR:
                    Log.i("BORTURN", "设备连接失败!");
                    ColpencilLogger.e("errCode="+errCode);
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
        map.put("ownerType", 0 + "");
        RetrofitManager.getInstance(1, MainActivity.this, Urlconfig.BASEURL)
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
                    }

                    @Override
                    public void onNext(final ResultListInfo<String> stringResultListInfo) {
                        String message = stringResultListInfo.message;
                        int code = stringResultListInfo.code;
                        switch (code) {
                            case 0:
                                keyList.clear();
                                keyList.addAll(stringResultListInfo.data);
                                KeyList kList  = new KeyList();
                                for (String s: stringResultListInfo.data){
                                    kList.key.add(s);
                                }
                                String json = gson.toJson(kList);
                                ColpencilLogger.e("keyJson："+json);
                                SharedPreferencesUtil.getInstance(MainActivity.this).setString("keys",json);
                                break;
                            case 1:
                                ToastTools.showShort(MainActivity.this, false, message);
                                break;
                            case 2:
//                                ToastTools.showShort(MainActivity.this, "您暂无钥匙！");
                                break;
                            case 3:
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
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
        map.put("ownerType", 0 + "");
        map.put("sn", sn);
        RetrofitManager.getInstance(1, MainActivity.this, Urlconfig.BASEURL)
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
                                ToastTools.showShort(MainActivity.this, false, message);
                                break;
                            case 3:
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
            //已连接到服务器
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                        ToastTools.showShort(MainActivity.this, false, "帐号已经被移除");
                    } else if (error == EMError.CONNECTION_CONFLICT) {
                        // 显示帐号在其他设备登录
                        ToastTools.showShort(MainActivity.this, false, "帐号在其他设备登录");
//                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                        ColpencilFrame.getInstance().finishAllActivity();
                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this)) {
                            //连接不到聊天服务器
//                            ToastTools.showShort(MainActivity.this,false,"连接不到聊天服务器");
                        } else {
                            //当前网络不可用，请检查网络设置
                            ToastTools.showShort(MainActivity.this, false, "当前网络不可用，请检查网络设置");
                        }
                    }
                }
            });
            ColpencilLogger.e("error=" + error);
        }
    }


    //请求二维码打开相机权限
    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQrcodePermissions() {
        String[] perms = {Manifest.permission.CAMERA};
        if (!EasyPermissions.hasPermissions(MainActivity.this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // 每个条目返回的fragment
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return FragmentFactory.creatFragment(0, MainActivity.this);
            } else if (position == 1) {
                return FragmentFactory.creatFragment(1, MainActivity.this);
            } else {
                return FragmentFactory.creatFragment(2, MainActivity.this);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("message", message);
    }

    public void checkVersion() {
        HashMap<String, String> params = new HashMap<>();
        params.put("devType",  "0");
        RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .checkVer(params)
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

                    }

                    @Override
                    public void onNext(ResultInfo<VersionInfo> stringResultInfo) {
                        if (stringResultInfo.code == 0) {
                            int versionCode = VersionUtils.getVersionCode(MainActivity.this);
                            if (versionCode<stringResultInfo.data.getVerCode()){
                                WarnDialog.showUpdateDialog(MainActivity.this,stringResultInfo.data.getUrl(), stringResultInfo.data.getDetail(),stringResultInfo.data.isForceUpdate());
                            }
                        }
                    }
                });
    }

    /**
     * 退出登录
     */
    private void loginOut() {
        HashMap<String, String> map = new HashMap<>();
        Observable<EntityResult<String>> resultObservable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .loginOut(map)
                .map(new Func1<EntityResult<String>, EntityResult<String>>() {
                    @Override
                    public EntityResult<String> call(EntityResult<String> result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Subscriber<EntityResult<String>> subscriber = new Subscriber<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                int code = result.getCode();
                String message = result.getMessage();
                switch (code) {
                    case 0:
                        ColpencilLogger.e("boolean=" + name.equals(deviceId) + "deviceId=" + deviceId);
                        if (!name.equals(deviceId)) {
                            ToastTools.showShort(MainActivity.this, false, "帐号在其他设备登录");
                            ColpencilFrame.getInstance().finishAllActivity();
                            SharedPreferencesUtil.getInstance(MainActivity.this).setString("passWord", "");
                            SharedPreferencesUtil.getInstance(MainActivity.this).setString("talkname", "");
                            // 把之前设置的 别名置空
                            JPushInterface.setAlias(MainActivity.this, "", new TagAliasCallback() {
                                @Override
                                public void gotResult(int i, String s, Set<String> set) {

                                }
                            });
                            EMChatManager.getInstance().logout();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }

                        break;
                    case 1:
                        ToastTools.showShort(MainActivity.this, false, message);
                        break;
                }
            }
        };
        resultObservable.subscribe(subscriber);

    }

    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
               ToastTools.showShort(this, "再按一次退出程序");
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

   /* *//**
     * 更新补丁
     *//*
    private void update() {
        HashMap<String, String> map = new HashMap<>();
        Observable<EntityResult<AnFixBean>> observable = RetrofitManager.getInstance(1, TenementApplication.getInstance(), Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .update(map)
                .map(new Func1<EntityResult<AnFixBean>, EntityResult<AnFixBean>>() {
                    @Override
                    public EntityResult<AnFixBean> call(EntityResult<AnFixBean> anFixBeanEntityResult) {
                        return anFixBeanEntityResult;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscriber<EntityResult<AnFixBean>> subscriber = new Subscriber<EntityResult<AnFixBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<AnFixBean> anFixBeanEntityResult) {
                int code = anFixBeanEntityResult.getCode();
                AnFixBean data = anFixBeanEntityResult.getData();
                switch (code) {
                    case 0:
                        if (TextUtils.isEmpty(data.md5)) {
                            ColpencilLogger.e("当前暂无更新！");
                            DialogTools.dissmiss();
                        } else {
                            download(data.path);
                            ColpencilLogger.e("下载地址：" + data.path);
                        }
                        break;
                    case 1:
                        DialogTools.dissmiss();
                        ColpencilLogger.e("请求失败！");
                        break;

                }
            }
        };
        observable.subscribe(subscriber);
    }

    //下载具体操作
    private void download(String downloadUrl) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/android_patch2.apatch");
        ColpencilLogger.e("路径："+file.exists());
        if (file.exists()==true){
            file.delete();
            ColpencilLogger.e("路径："+file.exists());
        }
        OkHttpUtils
                .get()
                .url(downloadUrl)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "android_patch2.apatch")//
                {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(File response, int id) {
                        DialogTools.dissmiss();
                        ColpencilLogger.e("更新成功，重新进入app，获得更好的体验！");
                        if (new File(response.getAbsolutePath()).exists()) {
                            ColpencilLogger.e("have some patch");
                            try {
                                TenementApplication.getInstance().getPatchManager().addPatch(response.getAbsolutePath());
                            } catch (Exception e) {
                                ColpencilLogger.e("Test=" + e);
                            }
                        } else {
                            ColpencilLogger.e("have no patch");
                        }
                    }
                });
    }*/
}
