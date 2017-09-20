package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.JsonUtils;
import com.colpencil.propertycloud.View.Activitys.ApiCloudActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import org.json.JSONObject;

import butterknife.Bind;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by xiaohuihui on 2017/1/5.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_scancode
)
public class ScanpayActivity extends ColpencilActivity implements QRCodeView.Delegate, View.OnClickListener {

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
            Log.e("result------>", result);
            //停止扫描二维码
            scancodeView.startSpot();
            if (JsonUtils.isBadJson(result)) {
                ToastTools.showShort(this, "该二维码不符合规范！");
                return;
            } else {
//                Intent intent = new Intent(this, PayActivity.class);
//                intent.putExtra("datas", result);
//                startActivity(intent);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String action = jsonObject.optString("action");
                    if (action.equals("startActivity")) {
                        Intent intent = new Intent(this, PayActivity.class);
                        intent.putExtra("datas", result);
                        startActivity(intent);
                    } else if (action.equals("url")) {
                        Intent intent = new Intent(this, ApiCloudActivity.class);
                        intent.putExtra("startUrl", jsonObject.optString("url"));
                        startActivity(intent);
                    } else if (action.equals("startActivity2Hp")) {
                        Intent intent = new Intent(this, OrderPayActivity2.class);
                        intent.putExtra("data", jsonObject.optString("param"));
                        startActivity(intent);
                    }
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

}
