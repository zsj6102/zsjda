package com.colpencil.propertycloud.Service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.Bean.CityBean;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.Overall.LocateState;
import com.colpencil.propertycloud.View.Activitys.Home.VilageSelectActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.HashMap;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.R.attr.type;

/**
 * @author 陈宝
 * @Description:这个服务用于定位
 * @Email DramaScript@outlook.com
 * @date 2017/2/4
 */

public class LocationService extends Service {

    private AMapLocationClient mLocationClient;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getIntExtra("type", 0) == 1) {
            initLocation(1);
        } else {
            initLocation(0);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void initLocation(final int type) {
        mLocationClient = CluodApplaction.getInstance().getmLocationClient();
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        String city = aMapLocation.getCity();
                        mLocationClient.stopLocation();
                        if (type == 0) {
                            loadCellByName(city);
                            loadCityId(city);
                        } else {
                            RxBusMsg msg = new RxBusMsg();
                            msg.setType(6);
                            msg.setId(LocateState.SUCCESS);
                            msg.setMsg(city);
                            RxBus.get().post(VilageSelectActivity.class.getSimpleName(), msg);
                        }
                    } else {
                        mLocationClient.stopLocation();
                        if (type == 0) {
                            loadCellByName("桂林");
                            loadCityId("桂林");
                            ToastTools.showShort(LocationService.this, "当前定位失败，已为您自动选择桂林");
                        } else {
                            RxBusMsg msg = new RxBusMsg();
                            msg.setType(6);
                            msg.setId(LocateState.FAILED);
                            msg.setMsg("定位失败");
                            RxBus.get().post(VilageSelectActivity.class.getSimpleName(), msg);
                        }
                    }
                } else {
                    mLocationClient.stopLocation();
                    if (type == 0) {
                        loadCellByName("桂林");
                        loadCityId("桂林");
                        ToastTools.showShort(LocationService.this, "当前定位失败，已为您自动选择桂林");
                    } else {
                        RxBusMsg msg = new RxBusMsg();
                        msg.setType(6);
                        msg.setId(LocateState.FAILED);
                        msg.setMsg("定位失败");
                        RxBus.get().post(VilageSelectActivity.class.getSimpleName(), msg);
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }

    private void loadCellByName(final String city) {
        SharedPreferencesUtil.getInstance(this).setString("cityName", city);
        HashMap<String, String> params = new HashMap<>();
        params.put("regionName", city);
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadCellByName(params)
                .map(new Func1<ResultListInfo<CellInfo>, ResultListInfo<CellInfo>>() {
                    @Override
                    public ResultListInfo<CellInfo> call(ResultListInfo<CellInfo> resultListInfo) {
                        return resultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<CellInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultListInfo<CellInfo> result) {
                        if (result.code == 0) {
                            if (result.data != null && result.data.size() > 0) {
                                CellInfo info = result.data.get(0);
                                SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("propertytel", info.getPropertytel());
                                SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("servicetel", info.getServicetel());
                                SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("shortnm", info.getShortname());
                                SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("comuid", info.getCommunity_id() + "");
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(5);
                                RxBus.get().post(VilageSelectActivity.class.getSimpleName(), rxBusMsg);
                            }
                        }
                    }
                });
    }

    private void loadCityId(String city) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cityName", city);
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadCityId(params)
                .map(new Func1<ResultInfo<CityBean>, ResultInfo<CityBean>>() {
                    @Override
                    public ResultInfo call(ResultInfo<CityBean> loginInfoResultInfo) {
                        return loginInfoResultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<CityBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultInfo<CityBean> result) {
                        if (result.code == 0) {
                            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setInt("cityId", result.data.cityId);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
    }
}
