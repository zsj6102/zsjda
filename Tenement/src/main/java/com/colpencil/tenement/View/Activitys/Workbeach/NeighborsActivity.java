package com.colpencil.tenement.View.Activitys.Workbeach;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.nearby.NearbyInfo;
import com.amap.api.services.nearby.NearbySearch;
import com.amap.api.services.nearby.NearbySearchFunctionType;
import com.amap.api.services.nearby.NearbySearchResult;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @Description: 绿化保洁
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/14
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_neighors
)
public class NeighborsActivity extends ColpencilActivity implements LocationSource, AMapLocationListener, NearbySearch.NearbyListener {

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.map)
    MapView mMapView;

    private AMap aMap;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明定位回调监听器
    public OnLocationChangedListener mListener = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    private int error = 0;
    private double latitude;
    private double longitude;
    private PolylineOptions polylineOptions;

    private String acount;

    private NearbySearch mNearbySearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews(View view) {
        tv_title.setText("附近的员工");
        acount = SharedPreferencesUtil.getInstance(this).getString("acount");
        //获取附近实例（单例模式）
        mNearbySearch = NearbySearch.getInstance(getApplicationContext());
        //设置附近监听
        NearbySearch.getInstance(getApplicationContext()).addNearbyListener(this);

        setStatecolor(getResources().getColor(R.color.colorPrimary));

        if (aMap == null) {
            aMap = mMapView.getMap();
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
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.ll_left)
    void backOnClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清除附近人数据
        //获取附近实例，并设置要清楚用户的id
        NearbySearch.getInstance(getApplicationContext()).setUserID(acount);
        //调用异步清除用户接口
        NearbySearch.getInstance(getApplicationContext())
                .clearUserInfoAsyn();
        //调用销毁功能，在应用的合适生命周期需要销毁附近功能
        NearbySearch.destroy();
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
                //设置搜索条件
                NearbySearch.NearbyQuery query = new NearbySearch.NearbyQuery();
                //设置搜索的中心点
                query.setCenterPoint(new LatLonPoint(latitude, longitude));
                //设置搜索的坐标体系
                query.setCoordType(NearbySearch.AMAP);
                //设置搜索半径
                query.setRadius(10000);
                //设置查询的时间
                query.setTimeRange(10000);
                //设置查询的方式驾车还是距离
                query.setType(NearbySearchFunctionType.DRIVING_DISTANCE_SEARCH);
                //调用异步查询接口
                mNearbySearch.searchNearbyInfoAsyn(query);

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
    private void addMarkerToMap(double latitude,double longitude,String acount) {
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.draggable(true);
        markerOption.icon(BitmapDescriptorFactory.fromResource(R.mipmap.nerb));
        markerOption.title(acount).icon(BitmapDescriptorFactory.fromResource(R.drawable.rect_line_whrite));
        Marker marker = aMap.addMarker(markerOption);
        marker.setRotateAngle(30);
        marker.showInfoWindow();

    }

    @Override
    public void onUserInfoCleared(int i) {

    }

    @Override
    public void onNearbyInfoSearched(NearbySearchResult nearbySearchResult, int resultCode) {
        //搜索周边附近用户回调处理
        if (resultCode == 1000) {
            if (nearbySearchResult != null
                    && nearbySearchResult.getNearbyInfoList() != null
                    && nearbySearchResult.getNearbyInfoList().size() > 0) {
                NearbyInfo nearbyInfo = nearbySearchResult.getNearbyInfoList().get(0);
                ColpencilLogger.e("周边搜索结果为size "+ nearbySearchResult.getNearbyInfoList().size() + "first："+ nearbyInfo.getUserID() + "  " + nearbyInfo.getDistance()+ "  "
                                + nearbyInfo.getDrivingDistance() + "  "+ nearbyInfo.getTimeStamp() + "  "+
                                nearbyInfo.getPoint().toString());
                for (int i=0;i<nearbySearchResult.getNearbyInfoList().size();i++){  // 打点
                    addMarkerToMap(nearbySearchResult.getNearbyInfoList().get(i).getPoint().getLatitude(),nearbySearchResult.getNearbyInfoList().get(i).getPoint().getLongitude(),nearbyInfo.getUserID());
                }
            } else {
                ColpencilLogger.e("周边搜索结果为空");
            }
        } else {
            ColpencilLogger.e("周边搜索出现异常，异常码为："+resultCode);
        }
    }

    @Override
    public void onNearbyInfoUploaded(int i) {

    }
}
