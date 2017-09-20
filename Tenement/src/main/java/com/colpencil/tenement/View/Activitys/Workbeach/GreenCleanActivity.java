package com.colpencil.tenement.View.Activitys.Workbeach;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.Record;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Ui.BottomDialog;
import com.colpencil.tenement.View.Activitys.VillageSelectActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @Description: 绿化保洁
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/14
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_common_map
)
public class GreenCleanActivity extends VillageSelectActivity implements LocationSource, AMapLocationListener {

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_rigth)
    TextView tv_right;

    @Bind(R.id.ll_title)
    LinearLayout ll_title;

    @Bind(R.id.iv_down)
    ImageView iv_down;

    @Bind(R.id.submit_record)
    Button btn_submit;

    @Bind(R.id.map)
    MapView mMapView;

    private int type;

    private AMap aMap;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明定位回调监听器
    public OnLocationChangedListener mListener = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    private String communityId;
    private int error = 0;
    private double latitude;
    private double longitude;
    private PolylineOptions polylineOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews(View view) {
        setHasAll(1);
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        type = getIntent().getIntExtra(StringConfig.TYPE, 0);
        String plot = SharedPreferencesUtil.getInstance(this).getString("shortName");
        communityId = SharedPreferencesUtil.getInstance(this).getString("plotId");
        if (TextUtils.isEmpty(plot)){
            tv_title.setText("暂无小区");
        }else {
            tv_title.setText(plot);
        }

        setFlag(1);
        iv_down.setVisibility(View.VISIBLE);
        ll_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVilageSelectBottom();
            }
        });
        // 获取到所选择的小区
        setButtonCallback(new BottomDialog.ButtonCallback() {
            @Override
            public void onClick(@NonNull BottomDialog dialog) {
                tv_title.setText(lists.get(dialog.position).plot);
                communityId = lists.get(dialog.position).plotId;
            }
        });
        if (type == 0) {
            tv_right.setText(getString(R.string.cleaning_record));
            btn_submit.setText(getString(R.string.upload_clean));
        }else {
            tv_right.setText(getString(R.string.patrol_record));
            btn_submit.setText(getString(R.string.upload_patrol));
        }
        tv_right.setVisibility(View.VISIBLE);

        if (aMap == null) {
            aMap = mMapView.getMap();
//            MyLocationStyle locationStyle = new MyLocationStyle();
//            locationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
//            locationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
//            aMap.setMyLocationStyle(locationStyle);
        }

        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        //设置定位监听
        aMap.setLocationSource(this);
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(true);
        settings.setScaleControlsEnabled(true);//显示比例尺控件
        // 是否可触发定位并显示定位层
        aMap.setMyLocationEnabled(true);

        initLoc();

        present.getMarker(communityId);

//        addMarkerToMap(24.483282+1,118.18417+1);
//        addMarkerToMap(24.483282+2,118.18417+2);
//        addMarkerToMap(24.483282-1,118.18417+1);

    }

    /**
     * 初始化定位
     */
    private void initLoc() {
        //初始化定位
        mLocationClient = TenementApplication.getInstance().getmLocationClient();
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = TenementApplication.getInstance().getmLocationOption();
        mLocationClient.startLocation();
    }


    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.ll_rigth)
    void rightOnClick() {
        Intent intent = new Intent(this, CleanRecordActivity.class);
        intent.putExtra(StringConfig.TYPE, type);
        startActivity(intent);
    }

    @OnClick(R.id.submit_record)
    void submitOnClick() {
        if (TextUtils.isEmpty(communityId)){
            ToastTools.showShort(this,false,"请先选择小区！" );
            return;
        }
        Intent intent = new Intent(this, UpLoadRecordActivity.class);
        intent.putExtra(StringConfig.TYPE, type);
        intent.putExtra("communityId",communityId);
        startActivity(intent);
    }

    @OnClick(R.id.ll_left)
    void backOnClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            error = 0;
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
                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(latitude, longitude)));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(amapLocation);
                    //添加图钉
                    aMap.addMarker(getMarkerOptions(amapLocation));
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
//                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                    isFirstLoc = false;
                }


            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());

                if (error==0){
                    switch (amapLocation.getErrorCode()){
                        case 12:
//                            ToastTools.showShort(this,false,"请在设备的设置中开启app的定位权限。");
                            break;
                        case 13:
//                            ToastTools.showShort(this,false,"GPS当前不可用。");
                            break;
                        case 4:
//                            ToastTools.showShort(this,false,"请检查设备网络是否通畅。");
                            break;
                    }
                }
                error = 1;
            }
        }
    }


    //自定义一个图钉，并且设置图标，当我们点击图钉时，显示设置的信息
    private MarkerOptions getMarkerOptions(AMapLocation amapLocation) {
        //设置图钉选项
        MarkerOptions options = new MarkerOptions();
        //图标
        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.din));
        //位置
        options.position(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
        StringBuffer buffer = new StringBuffer();
        buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
//        //标题
//        options.title(buffer.toString());
//        //子标题
//        options.snippet("这里好火");
        //设置多少帧刷新一次图片资源
        options.period(60);

        return options;

    }


    /**
     * 在地图上标记点
     * @param latitude
     * @param longitude
     */
    private void addMarkerToMap(double latitude,double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.draggable(true);
        markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.din));

//        markerOption.title("天安门").snippet("北京市东城区东长安街");

        Marker marker = aMap.addMarker(markerOption);
        marker.setRotateAngle(30);

        marker.showInfoWindow();

    }

    @Override
    public void loadMarker(ListCommonResult<Record> result) {
        super.loadMarker(result);
        ColpencilLogger.e("打点轨迹="+result.toString());
        List<Record> data = result.getData();
        for (int i=0;i<data.size();i++){  // 打点
            addMarkerToMap(data.get(i).latitude,data.get(i).longitude);
        }
        polylineOptions = new PolylineOptions();
        for (int i=0;i<data.size();i++){  // 打点
            polylineOptions.add(new LatLng(data.get(i).latitude,data.get(i).longitude));
        }
        polylineOptions.width(5);
        polylineOptions.color(getResources().getColor(R.color.mian_blue));
        polylineOptions.geodesic(true);
        aMap.addPolyline(polylineOptions);
    }
}
