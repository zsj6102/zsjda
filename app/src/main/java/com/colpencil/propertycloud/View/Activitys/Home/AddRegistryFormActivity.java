package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.AddRegsit;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.BaseActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.google.gson.Gson;
import com.jph.takephoto.model.TResult;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.OkhttpUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 装修登记表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/14
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_addform
)
public class AddRegistryFormActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.et_name)
    EditText et_name;

    @Bind(R.id.et_tel)
    EditText et_tel;

    @Bind(R.id.et_id)
    EditText et_id;

    @Bind(R.id.tv_add)
    TextView tv_add;

    @Bind(R.id.iv_pic)
    ImageView iv_pic;

    @Bind(R.id.tv_ok)
    TextView tv_ok;

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private Intent intentPreview;
    private ArrayList<ImageItem> selImageList = new ArrayList<>(); //当前选择的所有图片
    private int maxImgCount = 1;               //允许选择图片最大数
    private ImagePicker imagePicker;
    private String comuid;
    private List<File> lists;
    private List<File> fileList;
    private Gson gson;
    private int approveid;
    private String name;
    private String phone;
    private String identy;
    private Intent intent;

    private File path;
    private int houseId;

    @Override
    protected void initViews(View view) {
        approveid = getIntent().getIntExtra("approveid", 0);
        houseId = getIntent().getIntExtra("houseId", 0);

        tv_title.setText("装修人员登记");
        ll_left.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        iv_pic.setOnClickListener(this);

        imagePicker = CluodApplaction.getInstance().getImagePicker();
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制

        lists = new ArrayList<File>();  // 压缩后的图
        fileList = new ArrayList<>();  //  压缩前的图
    }




    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (!TextUtils.isEmpty(result.getImage().getCompressPath())){
            iv_pic.setVisibility(View.VISIBLE);
            tv_add.setVisibility(View.GONE);
            path = new File(result.getImages().get(0).getCompressPath());
            Glide.with(this)
                    .load(result.getImage().getCompressPath())
                    .into(iv_pic);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case R.id.ll_left:
               finish();
                break;
            case R.id.tv_add:
                openSelect(false,1);
                break;
            case R.id.iv_pic:
                openSelect(false,1);
                break;
            case R.id.tv_ok:
                name = et_name.getText().toString();
                phone = et_tel.getText().toString();
                identy = et_id.getText().toString();
                if (TextUtils.isEmpty(name)){
                    ToastTools.showShort(this,"装修员工姓名不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    ToastTools.showShort(this,"装修员工电话不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(identy)){
                    ToastTools.showShort(this,"装修员工身份证不能为空！");
                    return;
                }
                if (TextStringUtils.isMobileNO(phone) == false) {
                    ToastTools.showShort(this, "您输入的手机号不合法！");
                    return;
                }
                if (path==null){
                    ToastTools.showShort(this,"装修员工照片不能为空！");
                    return;
                }
//                if (selImageList.size() != 0) {
//                    for (int i = 0; i < selImageList.size(); i++) {
//                        fileList.add(new File(selImageList.get(i).path));
//                    }
//
//                    //  开启压缩
//                    Luban.get(this)
//                            .putGear(Luban.CUSTOM_GEAR)
//                            .load(fileList)                     // 加载所有图片
//                            .launch(new OnMultiCompressListener() {
//                                @Override
//                                public void onStart() {
//                                    ColpencilLogger.e("开始压缩中。。。");
//                                }
//
//                                @Override
//                                public void onSuccess(List<File> fileList) {
//                                    ColpencilLogger.e("压缩完毕。。。");
//                                    lists.addAll(fileList);
//
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//                                    ColpencilLogger.e("压缩失败。。。" + e.getMessage());
//                                }
//                            });     // 传入一个 OnMultiCompressListener
//                }
                submit();
                break;
        }
    }

    /**
     * 添加装修人员
     */
    private void submit(){
        ColpencilLogger.e("path:"+path);
        DialogTools.showLoding(this,"温馨提示","正在提交数据中。。。");
        Map<String, RequestBody> map = new HashMap<>();
        map.put("approveid", OkhttpUtils.toRequestBody(approveid+""));
        map.put("name", OkhttpUtils.toRequestBody(name));
        map.put("tel", OkhttpUtils.toRequestBody(phone));
        map.put("idNum", OkhttpUtils.toRequestBody(identy));
        map.put("houseId", OkhttpUtils.toRequestBody(houseId+""));
        if (path !=null){
            map.put("photo\";filename=\""+"imageList1"+1+".jpg",RequestBody.create(MediaType.parse("image/png"), path));
        }
        RetrofitManager.getInstance(1,this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .savePersonInfo(map)
                .map(new Func1<ResultInfo<AddRegsit>, ResultInfo<AddRegsit>>() {
                    @Override
                    public ResultInfo call(ResultInfo<AddRegsit> resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<AddRegsit>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息："+e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo<AddRegsit> resultInfo) {
                        int code = resultInfo.code;
                        String message = resultInfo.message;
                        DialogTools.dissmiss();
                        switch (code){
                            case 0:
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                rxBusMsg.setId(resultInfo.data.approveId);
                                RxBus.get().post("del",rxBusMsg);
                                finish();
                                break;
                            case 1:
                                ToastTools.showShort(AddRegistryFormActivity.this,false,message);
                                break;
                            case 3:
                                startActivity(new Intent(AddRegistryFormActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
