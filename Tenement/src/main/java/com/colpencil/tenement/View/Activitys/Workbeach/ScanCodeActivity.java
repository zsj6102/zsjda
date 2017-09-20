package com.colpencil.tenement.View.Activitys.Workbeach;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.tenement.R;
import com.colpencil.tenement.Tools.JsonUtils;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * @Description: 扫描二维码
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
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

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.tv_scanLightOpen)
    TextView tv_scanLightOpen;

    @Bind(R.id.tv_scanLightClose)
    TextView tv_scanLightClose;

    private int flag;  // 1：是从巡检跳转过来
    private Intent intent;
    private int where; // 0 ：是从抄水表    1：设备管理
    private int orderId;
    private String eqType;

    @Override
    protected void initViews(View view) {
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        flag = getIntent().getIntExtra("flag",0);
        where = getIntent().getIntExtra("where", 0);
        orderId = getIntent().getIntExtra("orderId", 0);
        eqType = getIntent().getStringExtra("eqType");
        ColpencilLogger.e("flag="+flag);
        initData();
    }

    //数据初始化
    private void initData() {
        tv_title.setText(R.string.title_scanCode);
        tv_rigth.setText(R.string.right_scanCode);
        if (where==1){ // 设备管理
            tv_rigth.setVisibility(View.INVISIBLE);
        }else {  // 抄水表
            tv_rigth.setVisibility(View.VISIBLE);
        }
        scancodeView.setDelegate(this);
        ll_left.setOnClickListener(this);
        ll_rigth.setOnClickListener(this);
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
        ColpencilLogger.e("二维码onScanQRCodeSuccess:" + result);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        if (!TextUtils.isEmpty(result)){
            if (JsonUtils.isBadJson(result)){
                ToastTools.showShort(this,"该二维码不符合物业规范！");
                return;
            }
            if (where==1||where==2){
                //设备管理
                intent = new Intent(ScanCodeActivity.this,StartPolingActivity.class);
                if (where==2){
                    intent.putExtra("flag",-1);
                    intent.putExtra("orderId",orderId);
                    intent.putExtra("eqType",eqType);
                }else {
                    intent.putExtra("flag",flag);
                }
                intent.putExtra("isCode",1);
                intent.putExtra("result",result);
                startActivity(intent);
            }else {
                // 跳转到提交水表界面
                intent = new Intent(ScanCodeActivity.this,WriteWatermeterActivity.class);
                intent.putExtra("isSao",1);
                intent.putExtra("result",result);
                ColpencilLogger.e("---------------------1");
                startActivity(intent);
            }

        }else {
            ToastTools.showShort(this,false,"扫描数据异常");
        }
        scancodeView.startSpot();//停止扫描二维码
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
            case R.id.ll_rigth://查看详情
                Log.e("返回值","跳转详情界面");
                Intent intent=new Intent(ScanCodeActivity.this,RecordDetailsActivity.class);
                startActivity(intent);
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
