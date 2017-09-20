package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.DiviceInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.JsonUtils;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 扫描二维码
 * @Email DramaScript@outlook.com
 * @date 2016/10/26
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_scancode
)
public class ScanCodeActivity extends ColpencilActivity implements QRCodeView.Delegate, View.OnClickListener {

    @Bind(R.id.scancodeView)
    ZXingView scancodeView;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_scanLightOpen)
    TextView tv_scanLightOpen;

    @Bind(R.id.tv_scanLightClose)
    TextView tv_scanLightClose;

    @Override
    protected void initViews(View view) {
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        initData();
    }

    //数据初始化
    private void initData() {
        tv_title.setText("二维码/条码");
        scancodeView.setDelegate(this);
        ll_left.setOnClickListener(this);
        tv_scanLightOpen.setOnClickListener(this);
        tv_scanLightClose.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        scancodeView.startCamera();//打开照相机
        scancodeView.startSpot();//开始进行扫描操作
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void onScanQRCodeSuccess(String result) {//扫描成功返回值
        //震动API
        if (!TextUtils.isEmpty(result)) {
            //停止扫描二维码
            scancodeView.startSpot();
            if (JsonUtils.isBadJson(result)) {
                ToastTools.showShort(this, "该二维码不符合规范！");
                return;
            } else {
                try {
                    JSONObject object = new JSONObject(result);
                    int diviceId = object.optInt("devId");
                    String eqType = object.getString("eqType");
                    loadDivice(diviceId, eqType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            //停止扫描二维码
            scancodeView.startSpot();
            ToastTools.showShort(this, "扫描数据异常");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        scancodeView.stopCamera();//打开照相机
//        scancodeView.stopCamera();//停止扫描二维码  防止消耗手机电量，造成发热
    }

    @Override
    public void onScanQRCodeOpenCameraError() {//扫描失败
        Log.e("返回值", "onScanQRCodeOpenCameraError:");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scancodeView.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_scanLightOpen://打开扫描灯
                scancodeView.openFlashlight();
                tv_scanLightOpen.setVisibility(View.GONE);
                tv_scanLightClose.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_scanLightClose://关掉扫描灯
                scancodeView.closeFlashlight();
                tv_scanLightClose.setVisibility(View.GONE);
                tv_scanLightOpen.setVisibility(View.VISIBLE);
                break;
        }
    }


    public void loadDivice(int devId, String eqType) {
        DialogTools.showLoding(this, "温馨提示", "正在获取设备信息...");
        HashMap<String, String> params = new HashMap<>();
        params.put("devId", devId + "");
        params.put("eqType", eqType);
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getDivice(params)
                .map(new Func1<ResultInfo<DiviceInfo>, ResultInfo<DiviceInfo>>() {
                    @Override
                    public ResultInfo<DiviceInfo> call(ResultInfo<DiviceInfo> diviceInfoResultInfo) {
                        return diviceInfoResultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<DiviceInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogTools.dissmiss();
                    }

                    @Override
                    public void onNext(ResultInfo<DiviceInfo> info) {
                        DialogTools.dissmiss();
                        Intent intent = new Intent();
                        intent.putExtra("data", info.data);
                        setResult(1, intent);
                        finish();
                    }
                });
    }
}
