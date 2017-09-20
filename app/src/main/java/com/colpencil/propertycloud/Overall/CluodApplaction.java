package com.colpencil.propertycloud.Overall;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.GlideImageLoader;
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
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.umeng.analytics.MobclickAgent;
import com.wholeally.qysdk.QYSDK;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.jpush.android.api.JPushInterface;

/**
 * @Description:  全局应用配置
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
public class CluodApplaction extends Application {

//    PatchManager patchManager;

    static CluodApplaction instance;

    private ExecutorService es;

    private ImagePicker imagePicker;

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

    public static CluodApplaction getInstance(){
        return instance;
    }


    public void execRunnable(Runnable r){
        es.execute(r);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        if("UzSimpleStorage".equals(name)){
            name= SharedPreferencesUtil.SHARED_NAME;
        }
        return super.getSharedPreferences(name, mode);
    }

    @Override
    public void onCreate() {
        super.onCreate();
       /* if (quickStart()) {
            return;
        }*/
        instance = this;

        //初始化qySDK
        QYSDK.InitSDK(4);
//        MobclickAgent.openActivityDurationTrack(false); //禁止默认的Activity页面统计方式。

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        //初始化内存泄漏工具
//        LeakCanary.install(this);
        //必须初始化框架操作
        ColpencilFrame.init(this);
        //初始化日志工具
        ColpencilLogger.init();
        // 秒级编译方案
//        FreelineCore.init(this);
        //下面是初始化热补丁框架
//        patchManager = new PatchManager(this);
//        patchManager.init(BuildConfig.VERSION_NAME);
//        patchManager.loadPatch();
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
        try{
            com.uzmap.pkg.openapi.APICloud.initialize(this);//初始化APICloud，SDK中所有的API均需要初始化后方可调用执行
        }catch (Throwable e){
            ColpencilLogger.e(e, "APICloud.initialize err:"+e.getMessage());
            throw e;
        }
    }

//    public PatchManager getPatchManager() {
//        return patchManager;
//    }

    private void initImageLoader() {


        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false) // default
                .cacheOnDisk(true) // default
//                .showImageOnLoading(R.mipmap.holder) // resource or
                // drawable
                .showImageForEmptyUri(R.mipmap.holder) // resource or
                // drawable
                .showImageOnFail(R.mipmap.holder).build();

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
