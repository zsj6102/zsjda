package com.colpencil.tenement.View.Activitys.ToayTask;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ClickUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DateUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 汪 亮
 * @Description: 签到
 * @Email DramaScript@outlook.com
 * @date 2016/9/21
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_signin
)
public class SignInActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.iv_sign)
    ImageView iv_sign;

    @Bind(R.id.tv_time)
    TextView tv_time;

    @Bind(R.id.tv_hour)
    TextView tv_hour;

    @Bind(R.id.tv_loaction)
    TextView tv_loaction;

    private Intent intent;

    private String path = Environment.getExternalStorageDirectory() + "/aImage/";
    private String fileName;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private String signStatus; // 0 :未签到  1 ：已签到  2：已签退

    private int error = 0;

    @Override
    protected void initViews(View view) {
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        signStatus = getIntent().getStringExtra("signStatus");
        switch (signStatus){
            case "0": // 未签到
                tv_title.setText("签到");
                iv_sign.setImageDrawable(getResources().getDrawable(R.mipmap.sinin));
                break;
            case "1": //  已签到
                tv_title.setText("签退");
                iv_sign.setImageDrawable(getResources().getDrawable(R.mipmap.sinout));
                break;
            case "2":  //  已签退
                tv_title.setText("已签退");
                iv_sign.setImageDrawable(getResources().getDrawable(R.mipmap.has_signout));
                break;
        }
        tv_rigth.setText("签到记录");
        tv_rigth.setVisibility(View.INVISIBLE);
        ll_rigth.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        iv_sign.setOnClickListener(this);
        initLocation();
        long timeMillis = System.currentTimeMillis();
        tv_time.setText(TimeUtil.getTimeStrFromMillis(timeMillis));

        Calendar mCalendar=Calendar.getInstance();
        mCalendar.setTimeInMillis(timeMillis);
        int hour = mCalendar.get(Calendar.HOUR);
        int minute = mCalendar.get(Calendar.MINUTE);
        tv_hour.setText(hour+":"+minute);


        Observable<RxBusMsg> sign = RxBus.get().register("sign", RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                switch (rxBusMsg.getType()){
                    case 1:
                        signStatus = "1";
                        iv_sign.setImageDrawable(getResources().getDrawable(R.mipmap.has_signin));
                        break;
                    case 2:
                        signStatus = "2";
                        iv_sign.setImageDrawable(getResources().getDrawable(R.mipmap.has_signout));
                        break;
                }
            }
        };
        sign.subscribe(subscriber);
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
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_rigth: // 我的签到记录
                finish();
                break;
            case R.id.iv_sign: // 签到
                if (!ClickUtils.isFastDoubleClick()){
                    switch (signStatus){
                        case "0": // 未签到
                            sigin();
                            break;
                        case "1": //  已签到  这个时候应该是可以签退了
                            sigin();
                            break;
                        case "2":  //  已签退

                            break;
                    }
                }
                break;
        }
    }

    /**
     * 签到/签退
     */
    private void sigin(){
        MaterialDialog show = new MaterialDialog.Builder(SignInActivity.this)
                .title("打开相机")
                .content("您是否需要打开相机拍照,进行签到？")
                .positiveText("打开")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        getImage();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    }
                })
                .show();
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        //初始化定位
        mLocationClient = TenementApplication.getInstance().getmLocationClient();
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                error = 0;
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                        amapLocation.getLatitude();//获取纬度
                        amapLocation.getLongitude();//获取经度
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
                        tv_loaction.setText(buffer.toString());

                    }else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                        if (error==0){
                            switch (amapLocation.getErrorCode()){
                                case 12:
//                                    ToastTools.showShort(SignInActivity.this,false,"请在设备的设置中开启app的定位权限。");
                                    break;
                                case 13:
//                                    ToastTools.showShort(SignInActivity.this,false,"GPS当前不可用。");
                                    break;
                                case 4:
//                                    ToastTools.showShort(SignInActivity.this,false,"请检查设备网络是否通畅。");
                                    break;
                            }
                            error = 1;
                        }


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


    private void getImage() {

        //先验证手机是否有sdcard
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            fileName = String.valueOf(System.currentTimeMillis())+".jpg";
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path + fileName)));
            startActivityForResult(intent, Activity.DEFAULT_KEYS_DIALER);
        } else {
            ToastTools.showShort(this,false,"没有储存卡");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Activity.DEFAULT_KEYS_DIALER: {
                if (resultCode==-1){
                    String loaction = tv_loaction.getText().toString();
                    File file = new File(path + fileName);
                    if (TextUtils.isEmpty(loaction)){
                        ToastTools.showShort(this,false,"正在获取地址，请重试！");
                        return;
                    }

                    intent = new Intent(SignInActivity.this, SignDetailActivity.class);
                    intent.putExtra("imagePath", path + fileName);
                    intent.putExtra("signStatus",signStatus);
                    intent.putExtra("loaction", loaction);
                    startActivity(intent);
                }

                break;
            }
        }
    }


}
