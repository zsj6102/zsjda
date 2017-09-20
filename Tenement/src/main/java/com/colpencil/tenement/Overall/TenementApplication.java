package com.colpencil.tenement.Overall;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.bumptech.glide.Glide;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Receiver.CallReceiver;
import com.colpencil.tenement.Tools.GlideImageLoader;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.lifeofcoding.cacheutlislibrary.CacheUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.jpush.android.api.JPushInterface;

/**
 * @author 汪 亮
 * @Description: 自定义Application
 * @Email DramaScript@outlook.com
 * @date 2016/7/27
 */
public class TenementApplication extends Application {

    static TenementApplication instance;

    private ExecutorService es;

    private Glide glide;

    private ImagePicker imagePicker;

    private CallReceiver callReceiver;

//    PatchManager patchManager;

    // 记录是否已经初始化
    private boolean isInit = false;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption;

    public AMapLocationClient getmLocationClient() {
        return mLocationClient;
    }

    public AMapLocationClientOption getmLocationOption() {
        return mLocationOption;
    }

    public ImagePicker getImagePicker() {
        return imagePicker;
    }

    public Glide getGlide() {
        return glide;
    }

    public static TenementApplication getInstance() {
        return instance;
    }

    public void exit() {
//        XmppTools.getInstance().disConnectServer();
    }

    public void execRunnable(Runnable r) {
        es.execute(r);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 初始化环信SDK
        EMChat.getInstance().setAutoLogin(true);
        EMChat.getInstance().init(this);
        EMChat.getInstance().setDebugMode(false);
        IntentFilter callFilter = new IntentFilter(EMChatManager.getInstance().getIncomingCallBroadcastAction());
        if(callReceiver == null){
            callReceiver = new CallReceiver();
        }
        //注册通话广播接收者
        this.registerReceiver(callReceiver, callFilter);

        // 极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //初始化热修复
//        patchManager = new PatchManager(this);
//        patchManager.init(BuildConfig.VERSION_NAME);
//        patchManager.loadPatch();

        //初始化内存泄漏工具
//        LeakCanary.install(this);
        //必须初始化框架操作
        ColpencilFrame.init(this);
        //初始化日志工具
        ColpencilLogger.init();
        //初始化缓存工具
        CacheUtils.configureCache(this);
        es = Executors.newFixedThreadPool(17);

        //设置imagepicker
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
//        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素

        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

        initImageLoader();
    }


//    public PatchManager getPatchManager() {
//        return patchManager;
//    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    private void initImageLoader() {


        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.mipmap.default_image) // resource or
                // drawable
                .showImageForEmptyUri(R.mipmap.default_image) // resource or
                // drawable
                .showImageOnFail(R.mipmap.default_image).build();

        File imageCacheDir = StorageUtils.getCacheDirectory(this, true);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCache(new UnlimitedDiskCache(imageCacheDir))
                // 自定义缓存路径
                .defaultDisplayImageOptions(options)
                .imageDownloader(
                        new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // 超时时间
                .build();// 开始构建
        // 全局初始化此配置
        ImageLoader.getInstance().init(config);
    }
}
