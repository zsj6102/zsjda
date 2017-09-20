package com.colpencil.propertycloud.View.Activitys.OpenDoor;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.KeyList;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.ShakeListener;
import com.colpencil.propertycloud.Tools.WarnDialog;
import com.colpencil.propertycloud.View.Activitys.Home.LoadUriActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.google.gson.Gson;
import com.izhihuicheng.api.lling.LLingOpenDoorConfig;
import com.izhihuicheng.api.lling.LLingOpenDoorHandler;
import com.izhihuicheng.api.lling.LLingOpenDoorStateListener;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NetUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.skyfishjy.library.RippleBackground;
import com.uzmap.pkg.uzmodules.uzAlipay.Keys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 开门
 * @Email DramaScript@outlook.com
 * @date 2016/11/23
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_open_door
)
public class OpenDoorActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.rl_key)
    RelativeLayout rl_key;

    @Bind(R.id.iv_open)
    ImageView iv_open;

    @Bind(R.id.content)
    RippleBackground content;

    @Bind(R.id.ll_back)
    LinearLayout ll_back;

    private Intent intent;
    private boolean isProprietor;

    public static final int RS_OD_SUCCESS = 1;
    public static final int RS_OD_FAILD = 2;
    public static final int RS_OD_ERROR = 3;
    public static final int RS_CONN_ERROR = 4;
    public static final int RS_CONN_NOFOUND = 5;

    private SoundPool soundPool;
    private SoundPool soundPool2;

    private String snn = "";

    private boolean isLogin;
    private String propertytel;
    private String servicetel;

    Vibrator vibrator = null;
    private ShakeListener mShakeListener;

    private List<String> keyList = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    content.stopRippleAnimation();
                    ToastTools.showShort(OpenDoorActivity.this, true, "开门成功！");
                    log(snn);
                    soundPool.load(OpenDoorActivity.this, R.raw.collide, 1);
                    soundPool.play(1, 1, 1, 0, 0, 1);
                    break;
                case 1:
                    content.stopRippleAnimation();
                    ToastTools.showShort(OpenDoorActivity.this, false, "设备连接失败！");
                    break;
                case 2:
                    content.stopRippleAnimation();
                    ToastTools.showShort(OpenDoorActivity.this, false, "设备未找到！");
                    break;
                case 3:
                    content.stopRippleAnimation();
                    ToastTools.showShort(OpenDoorActivity.this, false, "开门异常！");
                    break;
                case 4:
                    content.stopRippleAnimation();
                    ToastTools.showShort(OpenDoorActivity.this, false, "开门失败！");
                    break;
            }

        }
    };
    private Gson gson;


    @Override
    protected void initViews(View view) {
        gson = new Gson();
        getOpenKey();
        content.startRippleAnimation();
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool2 = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        isProprietor = SharedPreferencesUtil.getInstance(OpenDoorActivity.this).getBoolean("isProprietor", false);
        isLogin = SharedPreferencesUtil.getInstance(OpenDoorActivity.this).getBoolean("isLogin", false);
        propertytel = SharedPreferencesUtil.getInstance(OpenDoorActivity.this).getString("propertytel");
        servicetel = SharedPreferencesUtil.getInstance(OpenDoorActivity.this).getString("servicetel");
        rl_key.setOnClickListener(this);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);

    }

    @Override
    public void onResume() {
        mShakeListener = new ShakeListener(this);
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                mShakeListener.stop();
                //摇动手机后，再伴随震动提示~~
                vibrator.vibrate(500);
                soundPool2.load(OpenDoorActivity.this, R.raw.yao, 1);
                soundPool2.play(1, 1, 1, 0, 0, 1);
                content.startRippleAnimation();
                if (NetUtils.isConnected(OpenDoorActivity.this)){
                    getOpenKey();
                    openDoor();
                }else {
                    openDoor();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mShakeListener.start();
                    }
                }, 2000);
            }
        });
        iv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    if (isProprietor) {
                        content.startRippleAnimation();
                        if (NetUtils.isConnected(OpenDoorActivity.this)){
                            getOpenKey();
                            openDoor();
                        }else {
                            openDoor();
                        }

                    } else {
                        WarnDialog.show(OpenDoorActivity.this, propertytel, servicetel);
                    }
                } else {
                    startActivity(new Intent(OpenDoorActivity.this, LoginActivity.class));
                }
            }
        });
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mShakeListener != null)
            mShakeListener.des();
        ColpencilLogger.e("------------------熄灯了");
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    /**
     * 开门操作
     */
    private void openDoor(){
        if (NetUtils.isConnected(this)){
            String[] keys = new String[keyList.size()];
            for (int i = 0; i < keyList.size(); i++) {
                keys[i] = keyList.get(i);
            }
            LLingOpenDoorConfig config = new LLingOpenDoorConfig(2, keys);
            LLingOpenDoorHandler handler = LLingOpenDoorHandler
                    .getSingle(OpenDoorActivity.this);
            handler.doOpenDoor(config, listener);
        }else {
            String keyJson = SharedPreferencesUtil.getInstance(this).getString("keys");
            if (!TextUtils.isEmpty(keyJson)){
                KeyList jsonKeyList = gson.fromJson(keyJson, KeyList.class);
                String[] keys = new String[jsonKeyList.key.size()];
                for (int i = 0; i < jsonKeyList.key.size(); i++) {
                    keys[i] = jsonKeyList.key.get(i);
                }
                LLingOpenDoorConfig config = new LLingOpenDoorConfig(2, keys);
                LLingOpenDoorHandler handler = LLingOpenDoorHandler
                        .getSingle(OpenDoorActivity.this);
                handler.doOpenDoor(config, listener);
            }else {
                ToastTools.showShort(this,false,"请先连接网络获取钥匙！");
            }

        }

    }

    /**
     * 获取开门钥匙
     */
    private void getOpenKey() {
        HashMap<String, String> map = new HashMap<>();
        map.put("ownerType", 1 + "");
        RetrofitManager.getInstance(1, OpenDoorActivity.this, HostUrl.BASE_URL)
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
                                KeyList kList  = new KeyList();
                                for (String s: stringResultListInfo.data){
                                   kList.key.add(s);
                                }
                                String json = gson.toJson(kList);
                                ColpencilLogger.e("keyJson："+json);
                                SharedPreferencesUtil.getInstance(OpenDoorActivity.this).setString("keys",json);
                                break;
                            case 1:
                                ToastTools.showShort(OpenDoorActivity.this, false, message);
                                break;
                            case 2:
                                WarnDialog.show(OpenDoorActivity.this, propertytel, servicetel, "您没有当前门禁的电子钥匙哦，请联系物业公司。");
                                break;
                            case 3:
                                startActivity(new Intent(OpenDoorActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    /**
     * 获取钥匙管理的界面
     */
    private void getUrl() {
        HashMap<String, String> map = new HashMap<>();
        map.put("ownerType", 1 + "");
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .keyManager(map)
                .map(new Func1<ResultInfo<String>, ResultInfo<String>>() {
                    @Override
                    public ResultInfo<String> call(ResultInfo<String> resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultInfo<String> resultInfo) {

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
        RetrofitManager.getInstance(1, OpenDoorActivity.this, HostUrl.BASE_URL)
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
                                ToastTools.showShort(OpenDoorActivity.this, false, message);
                                break;
                            case 3:
                                startActivity(new Intent(OpenDoorActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_key:  // 钥匙管理h5
                if (isLogin) {
                    if (isProprietor) {
                        intent = new Intent(OpenDoorActivity.this, LoadUriActivity.class);
                        intent.putExtra("url", "http://112.74.197.63/wuye/property/opendoor/key_management.do?ownerType=" + 1);
                        intent.putExtra("type", 2);
                        intent.putExtra("title", "钥匙管理");
                        startActivity(intent);
                    } else {
                        WarnDialog.show(OpenDoorActivity.this, propertytel, servicetel);
                    }
                } else {
                    startActivity(new Intent(OpenDoorActivity.this, LoginActivity.class));
                }
                break;
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

}
