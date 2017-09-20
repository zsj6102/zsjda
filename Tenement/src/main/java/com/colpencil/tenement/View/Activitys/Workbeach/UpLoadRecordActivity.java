package com.colpencil.tenement.View.Activitys.Workbeach;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.ImagePreview;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.Present.Home.UploadRecordPresenter;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.CheckPermissionsActivity;
import com.colpencil.tenement.View.Activitys.Common.ImagePreviewActivity;
import com.colpencil.tenement.View.Activitys.ToayTask.SignInActivity;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Adpaters.ImagePickerAdapter;
import com.colpencil.tenement.View.Adpaters.PicImageAdapter;
import com.colpencil.tenement.View.Imples.UploadRecordView;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.PhotoBitmapUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilGridView;
import com.property.colpencil.colpencilandroidlibrary.Ui.RecylerView.FullyGridLayoutManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @author 陈宝
 * @Description: 上传巡逻记录和保洁记录
 * @Email DramaScript@outlook.com
 * @date 2016/8/24
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_upload_record)
public class UpLoadRecordActivity extends CheckPermissionsActivity implements UploadRecordView {

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_time)
    TextView tv_time;

    @Bind(R.id.btn_get_time)
    TextView btn_get_time;

    @Bind(R.id.tv_loaction)
    TextView tv_loaction;

    @Bind(R.id.btn_get_loaction)
    TextView btn_get_loaction;

    @Bind(R.id.et_work_task)
    EditText et_work_task;

    @Bind(R.id.gl_add)
    ColpencilGridView gl_add;

    @Bind(R.id.btn_upload)
    Button btn_upload;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    private int type;

    private UploadRecordPresenter presenter;

    public static final int IMAGE_ITEM_ADD = -1;
    public final int IMAGE_ITEM_DELL = 100;

    private String communityId;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private SweetAlertDialog pDialog;

    private int i = -1;

    private String path = Environment.getExternalStorageDirectory() + "/aImage/";
    private String fileName;
    private String imagePath;


    private PicImageAdapter adapter;

    private List<String> imageList = new ArrayList<>();
    private List<File> lists = new ArrayList<>();
    private Gson gson;
    private int error = 0;
    private double latitude;
    private double longitude;

    @Override
    protected void initViews(View view) {
        tv_rigth.setVisibility(View.INVISIBLE);
        tv_rigth.setText("重新获取");
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        communityId = getIntent().getStringExtra("communityId");
        type = getIntent().getIntExtra(StringConfig.TYPE, 0);
        if (type == 0) {
            tv_title.setText("保洁工作汇报");
        } else {
            tv_title.setText("巡逻工作汇报");
        }
        initLocation();
        tv_time.setText(getCurrentTime());
        btn_get_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_time.setText(getCurrentTime());
            }
        });
      /*  new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_time.setText(getCurrentTime());
                    }
                });
            }
        }.start();*/
        btn_get_loaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
        adapter = new PicImageAdapter(imageList, this);
        gl_add.setAdapter(adapter);
        gl_add.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gl_add.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == imageList.size()) {
                    if (imageList.size() == 3) {
                        ToastTools.showShort(UpLoadRecordActivity.this, false, "最多上传3张图片！");
                        return;
                    }
                    getImage();
                } else {
                    ImagePreview imagePreview = new ImagePreview();
                    imagePreview.imageList.addAll(imageList);
                    gson = new Gson();
                    String json = gson.toJson(imagePreview);
                    Intent intent = new Intent(UpLoadRecordActivity.this, ImagePreviewActivity.class);
                    intent.putExtra("imageList", json);
                    intent.putExtra("position", position);
                    startActivityForResult(intent, IMAGE_ITEM_DELL);
                }
            }
        });

    }

    /**
     * 提交
     */
    private void commit() {
        String time = tv_time.getText().toString();
        String loaction = tv_loaction.getText().toString();
        String content = et_work_task.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastTools.showShort(this, false, "工作描述不能为空！");
            return;
        }
        if (TextUtils.isEmpty(loaction)) {
            ToastTools.showShort(this, false, "正在获取位置中，请稍后重试！");
            return;
        }
        if (imageList.size() == 0) {
            ToastTools.showShort(this, false, "您至少选择一张图片！");
            return;
        }
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
        pDialog = new SweetAlertDialog(UpLoadRecordActivity.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("正在提交中");
        pDialog.show();
        pDialog.setCancelable(true);
        new CountDownTimer(800 * 7, 800) {
            public void onTick(long millisUntilFinished) {
                // you can change the progress bar color by ProgressHelper every 800 millis
                i++;
                switch (i) {
                    case 0:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                        break;
                    case 1:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                        break;
                    case 2:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                        break;
                    case 3:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
                        break;
                    case 4:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
                        break;
                    case 5:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
                        break;
                    case 6:
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.mian_blue));
                        break;
                }
            }

            @Override
            public void onFinish() {

            }

        }.start();
        presenter.upload(type + 1, time, TimeUtil.getTimeStrFromMillis(System.currentTimeMillis()), loaction,
                content, lists, communityId,longitude+"",latitude+"");
        ColpencilLogger.e("------------------fileSize=" + lists.size());
    }

    /**
     * 获取当前的时间
     *
     * @return
     */
    private String getCurrentTime() {
        return TimeUtil.getTimeStrFromMillis(System.currentTimeMillis());
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
                        //获取定位信息
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
//                        Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                        if (!TextUtils.isEmpty(buffer.toString())) {
                            tv_loaction.setText(buffer.toString());
                        }

                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());

                        if (error==0){

                            switch (amapLocation.getErrorCode()) {
                                case 12:
//                                    ToastTools.showShort(UpLoadRecordActivity.this, false, "请在设备的设置中开启app的定位权限。");
                                    break;
                                case 13:
//                                    ToastTools.showShort(UpLoadRecordActivity.this, false, "GPS当前不可用。");
                                    break;
                                case 4:
//                                    ToastTools.showShort(UpLoadRecordActivity.this, false, "请检查设备网络是否通畅。");
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


    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new UploadRecordPresenter();
        return presenter;
    }


    public void getImage() {

        //先验证手机是否有sdcard
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path + fileName)));
            startActivityForResult(intent, Activity.DEFAULT_KEYS_DIALER);
        } else {
            ToastTools.showShort(UpLoadRecordActivity.this, false, "没有储存卡。");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Activity.DEFAULT_KEYS_DIALER:
                ColpencilLogger.e("resultCode=" + resultCode);
                if (resultCode == -1) {
                    imagePath = path + fileName;
                    File file = new File(path + fileName);
                    imageList.add(imagePath);
                    if (imageList.size() != 0) {  //  开启压缩
                        DialogTools.showLoding(this, "温馨提示", "获取中。。。");
                        for (int i = 0; i < imageList.size(); i++) {
                            final int finalI = i;
                            if (lists.size() != 0) {
                                lists.clear();
                            }
                            Luban.get(UpLoadRecordActivity.this)
                                    .load(new File(imageList.get(i)))
                                    .putGear(Luban.THIRD_GEAR)
                                    .setCompressListener(new OnCompressListener() {
                                        @Override
                                        public void onStart() {
                                            ColpencilLogger.e("开始压缩中。。。");
                                        }

                                        @Override
                                        public void onSuccess(File file) {
                                            lists.add(file);
                                            ColpencilLogger.e("压缩完毕。。。");
                                            if (finalI == imageList.size() - 1) {
                                                DialogTools.dissmiss();
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            ColpencilLogger.e("压缩失败。。。" + e.getMessage());
                                        }
                                    }).launch();
                        }
                    }
                }
                adapter.setImageList(imageList);
                adapter.notifyDataSetChanged();
                break;

            case IMAGE_ITEM_DELL:
                if (resultCode == 101) {
                    String dataStringExtra = data.getStringExtra("change");
                    ImagePreview imagePreview = gson.fromJson(dataStringExtra, ImagePreview.class);
                    imageList.clear();
                    imageList.addAll(imagePreview.imageList);
                    adapter.setImageList(imageList);
                    adapter.notifyDataSetChanged();
                    if (imageList.size() != 0) {  //  开启压缩
                        DialogTools.showLoding(this, "温馨提示", "获取中。。。");
                        for (int i = 0; i < imageList.size(); i++) {
                            final int finalI = i;
                            if (lists.size() != 0) {
                                lists.clear();
                            }
                            Luban.get(UpLoadRecordActivity.this)
                                    .load(new File(imageList.get(i)))
                                    .putGear(Luban.THIRD_GEAR)
                                    .setCompressListener(new OnCompressListener() {
                                        @Override
                                        public void onStart() {
                                            ColpencilLogger.e("开始压缩中。。。");
                                        }

                                        @Override
                                        public void onSuccess(File file) {
                                            lists.add(file);
                                            ColpencilLogger.e("压缩完毕。。。");
                                            if (finalI == imageList.size() - 1) {
                                                DialogTools.dissmiss();
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            ColpencilLogger.e("压缩失败。。。" + e.getMessage());
                                        }
                                    }).launch();
                        }
                    }
                }

                break;
        }

    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }


    /**
     * 隐藏键盘操作
     */
    public void key() {
        // 虚拟键盘隐藏 判断view是否为空
        if (getWindow().peekDecorView() != null) {
            //隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(getWindow().peekDecorView().getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pDialog != null) {
            pDialog.dismiss();
        }
    }


    @Override
    public void uploadResult(EntityResult result) {
        int code = result.getCode();
        String message = result.getMessage();
        switch (code) {
            case 0:  // 成功
                pDialog.setTitleText("提交成功哦！")
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                Intent intent = new Intent(UpLoadRecordActivity.this, CleanRecordActivity.class);
                intent.putExtra(StringConfig.TYPE,type);
                startActivity(intent);
                finish();
                mAm.finishActivity(GreenCleanActivity.class);
                ToastTools.showShort(UpLoadRecordActivity.this, true, "提交成功哦。");
                break;
            case 1:  // 失败
                pDialog.setTitleText("提交失败，请检查网络是否通畅。")
                        .setConfirmText("重新提交")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                commit();
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                pDialog.dismiss();
                            }
                        })
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                break;
            case 3: // 未登录
                pDialog.dismissWithAnimation();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case 4: // 验证失败
                pDialog.dismissWithAnimation();
                ToastTools.showShort(UpLoadRecordActivity.this, true, message);
                break;
            default:
                pDialog.dismissWithAnimation();
                ToastTools.showShort(this, false, message);
                break;
        }

        i = -1;
    }

    @Override
    public void loadError(String msg) {
        if (pDialog!=null){
            pDialog.dismiss();
        }
        ToastTools.showShort(this,false,"请求失败！");
    }

    @OnClick(R.id.ll_left)
    void backOnClick() {
        finish();
    }

}
