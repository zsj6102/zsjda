package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ApplaInfo;
import com.colpencil.propertycloud.Bean.Approveid;
import com.colpencil.propertycloud.Bean.FitmentInfo;
import com.colpencil.propertycloud.Bean.HouseInfo;
import com.colpencil.propertycloud.Bean.Material;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.Present.Home.MaterialListPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Ui.BottomDialog;
import com.colpencil.propertycloud.View.Activitys.ApiCloudActivity;
import com.colpencil.propertycloud.View.Activitys.BaseActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.FitmentPicAdapter;
import com.colpencil.propertycloud.View.Adapter.FitmentPicAdapter2;
import com.colpencil.propertycloud.View.Adapter.MaterailListAdapter;
import com.colpencil.propertycloud.View.Imples.MaterialManagerView;
import com.google.gson.Gson;
import com.jph.takephoto.model.TResult;
import com.lzy.imagepicker.ImagePicker;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.OkhttpUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilGridView;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.colpencil.propertycloud.Overall.HostUrl.BASE_HOST_PATH;

/**
 * @author 汪 亮
 * @Description: 材料管理
 * @Email DramaScript@outlook.com
 * @date 2016/9/7
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_material_manager
)
public class MaterialManagementActivity extends BaseActivity implements MaterialManagerView, View.OnClickListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.iv_check1)
    ImageView iv_check1;

    @Bind(R.id.tv_do1)
    TextView tv_do1;

    @Bind(R.id.iv_check2)
    ImageView iv_check2;

    @Bind(R.id.tv_do2)
    TextView tv_do2;

    @Bind(R.id.iv_check3)
    ImageView iv_check3;
    @Bind(R.id.tv_do3)
    TextView tv_do3;

    @Bind(R.id.iv_check4)
    ImageView iv_check4;
    @Bind(R.id.tv_do4)
    TextView tv_do4;

    @Bind(R.id.iv_check5)
    ImageView iv_check5;
    @Bind(R.id.tv_do5)
    TextView tv_do5;

    @Bind(R.id.iv_check6)
    ImageView iv_check6;
    @Bind(R.id.tv_do6)
    TextView tv_do6;

    @Bind(R.id.tv_ok)
    TextView tv_ok;

    @Bind(R.id.tv_comiuty)
    TextView tv_comiuty;

    @Bind(R.id.ll_select)
    RelativeLayout ll_select;

    @Bind(R.id.lv_select)
    ColpencilListView lv_select;

    @Bind(R.id.cl_mater1)
    ColpencilGridView cl_mater1;

    @Bind(R.id.cl_mater2)
    ColpencilGridView cl_mater2;

    @Bind(R.id.tv_cun)
    TextView tv_cun;

    @Bind(R.id.ll_read)
    LinearLayout ll_read;

    @Bind(R.id.iv_agree)
    ImageView iv_agree;

    @Bind(R.id.tv_xie)
    TextView tv_xie;

    private boolean isAgree = true;

    private MaterailListAdapter adapter;
    private MaterialListPresent present;
    private Intent intent;

    private List<Material> list = new ArrayList<>();
    private Observable<RxBusMsg> materialManagement;
    private int approveId;

    private ImagePicker imagePicker;

    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ArrayList<String> selImageList = new ArrayList<>(); //当前选择的所有图片
    private ArrayList<String> selImageList2 = new ArrayList<>(); //当前选择的所有图片
    private Intent intentPreview;
    private boolean flag = false;
    private String pic1;
    private String pic2;
    private String pic3;
    private File file1;
    private File file2;
    private File file3;
    private int hseid = 0;
    private String budnm;
    private String comnm;
    private String unitnm;
    private String hsearea;
    private String name;
    private String tel;
    private String hsecd;
    private Gson gson;
    private ApplaInfo applaInfo;

    private List<File> files1 = new ArrayList<>();
    private List<File> files2 = new ArrayList<>();
    private String decortCoNm = "";
    private String decortHead = "";
    private String peopleNum = "";
    private String qualifiNo = "";
    private String decortCoTel = "";
    private String decortbeginTm = "";
    private String decortEndTm = "";
    private String decortDesc = "";
    private String community_id;

    //下面是主动添加的
    @Bind(R.id.et_jian)
    EditText et_jian;

    @Bind(R.id.et_zu)
    EditText et_zu;

    @Bind(R.id.button)
    Button button;

    @Bind(R.id.button1)
    Button button1;

    @Bind(R.id.ll_guan)
    LinearLayout ll_guan;

    @Bind(R.id.ll_fit)
    LinearLayout ll_fit;

    @Bind(R.id.ll_jian)
    LinearLayout ll_jian;
    private int type; // 0 : 表示新增   1 ： 关联
    private FitmentPicAdapter2 adapter1;
    private FitmentPicAdapter2 adapter2;

    private boolean chen = false;

    @Override
    protected void initViews(View view) {
        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            ll_fit.setVisibility(View.VISIBLE);
            gson = new Gson();
            applaInfo = new ApplaInfo();
            tv_title.setText("新装修申请");
            ll_left.setOnClickListener(this);
            tv_do1.setOnClickListener(this);
            tv_do2.setOnClickListener(this);
            tv_do3.setOnClickListener(this);
            tv_do4.setOnClickListener(this);
            tv_do5.setOnClickListener(this);
            tv_do6.setOnClickListener(this);
            tv_ok.setOnClickListener(this);
            tv_cun.setOnClickListener(this);
            ll_select.setOnClickListener(this);
            materialManagement = RxBus.get().register("MaterialManagement", RxBusMsg.class);
            materialManagement.subscribe(subscriber);
            getFitmentInfo();
        } else {
            ll_fit.setVisibility(View.GONE);
            ll_guan.setVisibility(View.VISIBLE);
            ll_jian.setVisibility(View.VISIBLE);
        }
        cl_mater1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selImageList.remove(position);
                adapter1.notifyDataSetChanged();
            }
        });
        cl_mater2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selImageList2.remove(position);
                adapter2.notifyDataSetChanged();
            }
        });
        ll_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAgree) {
                    iv_agree.setImageDrawable(getResources().getDrawable(R.mipmap.fit_uncheck));
                    isAgree = false;
                } else {
                    iv_agree.setImageDrawable(getResources().getDrawable(R.mipmap.fit_check));
                    isAgree = true;
                }
            }
        });
        tv_xie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MaterialManagementActivity.this, LoadUriActivity.class);
                intent.putExtra("url", BASE_HOST_PATH + "/property/agreement/user-agreement.do?flag=decorate");
                intent.putExtra("title", "用户协议");
                startActivity(intent);
            }
        });
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (flag == false) {
            if (result.getImages().size() != 0) {
                iv_check4.setImageDrawable(getResources().getDrawable(R.mipmap.fit_check));
            } else {
                iv_check4.setImageDrawable(getResources().getDrawable(R.mipmap.fit_uncheck));
            }
            for (int i = 0; i < result.getImages().size(); i++) {
                selImageList.add(result.getImages().get(i).getCompressPath());
                files1.add(new File(result.getImages().get(i).getCompressPath()));
            }
            adapter1 = new FitmentPicAdapter2(this, selImageList);
            cl_mater1.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();
            ColpencilLogger.e("-------------------1");
        } else {
            if (result.getImages().size() != 0) {
                iv_check5.setImageDrawable(getResources().getDrawable(R.mipmap.fit_check));
            } else {
                iv_check5.setImageDrawable(getResources().getDrawable(R.mipmap.fit_uncheck));
            }
            for (int i = 0; i < result.getImages().size(); i++) {
                selImageList2.add(result.getImages().get(i).getCompressPath());
                files2.add(new File(result.getImages().get(i).getCompressPath()));
            }
            adapter2 = new FitmentPicAdapter2(this, selImageList2);
            cl_mater2.setAdapter(adapter2);
            adapter2.notifyDataSetChanged();
            ColpencilLogger.e("-------------------2");
        }
    }

    Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(RxBusMsg rxBusMsg) {
            if (rxBusMsg.getMsg().equals("add")) {// 添加了装修人员
                iv_check6.setImageDrawable(getResources().getDrawable(R.mipmap.fit_check));
                tv_do6.setText("修改");
                approveId = rxBusMsg.getId();
            }
            switch (rxBusMsg.getType()) {
                case 0:
                    pic1 = rxBusMsg.getMsg();
                    ColpencilLogger.e("装修规定的图片：" + pic1);
                    iv_check1.setImageDrawable(getResources().getDrawable(R.mipmap.fit_check));
                    tv_do1.setText("修改");
                    break;
                case 1:
                    chen = true;
                    pic2 = SharedPreferencesUtil.getInstance(MaterialManagementActivity.this).getString("imagePath");
                    pic3 = rxBusMsg.getMsg();
                    ColpencilLogger.e("装修承诺书1：" + pic2);
                    ColpencilLogger.e("装修承诺书2：" + pic3);
                    iv_check2.setImageDrawable(getResources().getDrawable(R.mipmap.fit_check));
                    tv_do2.setText("修改");
                    break;
                case 2:// 装修申请表信息
                    applaInfo = gson.fromJson(rxBusMsg.getMsg(), ApplaInfo.class);
                    iv_check3.setImageDrawable(getResources().getDrawable(R.mipmap.fit_check));
                    ColpencilLogger.e("装修信息：" + applaInfo.toString());
                    tv_do3.setText("修改");
                    decortCoNm = applaInfo.decortCoNm;
                    decortHead = applaInfo.decortHead;
                    peopleNum = applaInfo.peopleNum;
                    qualifiNo = applaInfo.qualifiNo;
                    decortCoTel = applaInfo.decortCoTel;
                    decortbeginTm = applaInfo.decortbeginTm;
                    decortEndTm = applaInfo.decortEndTm;
                    decortDesc = applaInfo.decortDesc;
                    break;
            }
        }
    };

    @Override
    public ColpencilPresenter getPresenter() {
        present = new MaterialListPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void materialList(List<Material> list) {

    }

    @Override
    public void submit(ResultInfo resultInfo) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("MaterialManagement", materialManagement);
        System.gc();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_cun:
                ColpencilLogger.e("chen=" + chen);
                if (!isAgree) {
                    ToastTools.showShort(this, "请先阅读上方协议");
                    return;
                }
                if (chen) {
                    if (!TextUtils.isEmpty(pic1) && !TextUtils.isEmpty(pic2)) {
                        if (!TextUtils.isEmpty(pic1)) {
                            file1 = new File(pic1);
                        }
                        if (!TextUtils.isEmpty(pic2)) {
                            file2 = new File(pic2);
                        }
                        if (!TextUtils.isEmpty(pic3)) {
                            file3 = new File(pic3);
                        }
                        ToastTools.showShort(this, true, "保存成功！");
                        submit(hseid + "", approveId + "", decortCoNm, decortHead, peopleNum,
                                qualifiNo, decortCoTel, decortbeginTm, decortEndTm,
                                decortDesc, files2, file1, file2, file3, files1, "", "");
                        finish();
                    } else {
                        ToastTools.showShort(this, true, "装修承诺书缺少签名文件！");
                        return;
                    }
                } else {
                    submit(hseid + "", approveId + "", decortCoNm, decortHead, peopleNum,
                            qualifiNo, decortCoTel, decortbeginTm, decortEndTm,
                            decortDesc, files2, file1, file2, file3, files1, "", "");
                }
                break;
            case R.id.tv_do1://装修管理规定
                intent = new Intent(MaterialManagementActivity.this, ReadActivity.class);
                intent.putExtra("flag", 1);
                intent.putExtra("type", 0);
                intent.putExtra("name", "装修管理规定");
                intent.putExtra("community_id", community_id);
                startActivity(intent);
                break;
            case R.id.tv_do2://装修承诺书
                intent = new Intent(MaterialManagementActivity.this, ReadActivity.class);
                intent.putExtra("flag", 2);
                intent.putExtra("type", 0);
                intent.putExtra("name", "装修承诺书");
                intent.putExtra("community_id", community_id);
                startActivity(intent);
                break;
            case R.id.tv_do3://装修申请表
                intent = new Intent(MaterialManagementActivity.this, DecorateApplyActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("tel", tel);
                intent.putExtra("type", 0);
                intent.putExtra("hsearea", hsearea);
                intent.putExtra("room", tv_comiuty.getText().toString());
                startActivity(intent);
                break;
            case R.id.tv_do4://物业公司资质资料
                files1.clear();
                selImageList.clear();
                openSelect(false, 3);
                flag = false;
                break;
            case R.id.tv_do5://装修设计资料扫描件
                files2.clear();
                selImageList2.clear();
                openSelect(false, 12);
                flag = true;
                break;
            case R.id.tv_do6://装修人员登记表
                intent = new Intent(MaterialManagementActivity.this, RegistryFormListActivity.class);
                intent.putExtra("approveid", approveId);
                intent.putExtra("houseId", hseid);
                startActivity(intent);
                break;
            case R.id.tv_ok:
                if (!isAgree) {
                    ToastTools.showShort(this, "请先阅读上方协议");
                    return;
                }
                if (!TextUtils.isEmpty(pic1)) {
                    file1 = new File(pic1);
                }
                if (!TextUtils.isEmpty(pic2)) {
                    file2 = new File(pic2);
                }
                if (!TextUtils.isEmpty(pic3)) {
                    file3 = new File(pic3);
                }
                if (hseid == 0) {
                    ToastTools.showShort(this, "请先选择我的房产");
                }
                if (files2.size() != 0 && files1.size() != 0) {
                    submit(hseid + "", approveId + "", decortCoNm, decortHead, peopleNum,
                            qualifiNo, decortCoTel, decortbeginTm, decortEndTm,
                            decortDesc, files2, file1, file2, file3, files1, "", "");
                } else {
                    if (files1.size() != 0) {
                        submit(hseid + "", approveId + "", decortCoNm, decortHead, peopleNum,
                                qualifiNo, decortCoTel, decortbeginTm, decortEndTm,
                                decortDesc, null, file1, file2, file3, files1, "", "");
                    }
                    if (files2.size() != 0) {
                        submit(hseid + "", approveId + "", decortCoNm, decortHead, peopleNum,
                                qualifiNo, decortCoTel, decortbeginTm, decortEndTm,
                                decortDesc, files2, file1, file2, file3, null, "", "");
                    }
                    if (files1.size() == 0 && files2.size() == 0) {
                        submit(hseid + "", approveId + "", decortCoNm, decortHead, peopleNum,
                                qualifiNo, decortCoTel, decortbeginTm, decortEndTm,
                                decortDesc, null, file1, file2, file3, null, "", "");
                    }
                }
                break;
            case R.id.ll_select:
                // TODO: 2016/11/21 选择我的房产
                getRoom();
                break;
        }
    }

    /**
     * 获取我的房产
     */
    private void getRoom() {
        HashMap<String, String> map = new HashMap<>();
        map.put("flag", "1");
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadEstate(map)
                .map(new Func1<ResultListInfo<HouseInfo>, ResultListInfo<HouseInfo>>() {
                    @Override
                    public ResultListInfo<HouseInfo> call(ResultListInfo<HouseInfo> houseInfoResultListInfo) {
                        return houseInfoResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<HouseInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息：" + e.getMessage());
                    }

                    @Override
                    public void onNext(final ResultListInfo<HouseInfo> houseInfoResultListInfo) {
                        int code = houseInfoResultListInfo.code;
                        String message = houseInfoResultListInfo.message;
                        switch (code) {
                            case 0:
                                final List<String> stringList = new ArrayList<String>();
                                for (int i = 0; i < houseInfoResultListInfo.data.size(); i++) {
                                    stringList.add(houseInfoResultListInfo.data.get(i).getCommunity_name() + "" +
                                            houseInfoResultListInfo.data.get(i).getBuilding_name() + "栋" +
                                            houseInfoResultListInfo.data.get(i).getUnit_name() + "单元" +
                                            houseInfoResultListInfo.data.get(i).getHouse_name());
                                }
                                new BottomDialog.Builder(MaterialManagementActivity.this)
                                        .setTitle("选择房间")
                                        .setDataList(stringList)
                                        .setPositiveText("确认")
                                        .setNegativeText("取消")
                                        .onPositive(new BottomDialog.ButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull BottomDialog dialog) {
                                                tv_comiuty.setText(stringList.get(dialog.position));
                                                hseid = houseInfoResultListInfo.data.get(dialog.position).getHouse_id();
                                                hsearea = houseInfoResultListInfo.data.get(dialog.position).getHsearea();
                                                community_id = houseInfoResultListInfo.data.get(dialog.position).getCommunity_id() + "";
                                            }
                                        })
                                        .onNegative(new BottomDialog.ButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull BottomDialog dialog) {

                                            }
                                        }).show();
                                break;
                            case 1:
                                ToastTools.showShort(MaterialManagementActivity.this, false, message);
                                break;
                            case 2:
                                ToastTools.showShort(MaterialManagementActivity.this, "您暂无房产！");
                                break;
                            case 3:
                                startActivity(new Intent(MaterialManagementActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    /**
     * 装修申请
     */
    private void submit(String houseId, final String approveid, String decortCoNm, String decortHead, String peopleNum,
                        String qualifiNo, String decortCoTel, String decortbeginTm, String decortEndTm,
                        String decortDesc, List<File> decortDsnInfo, File decortManReguSign, File decortCommitBookSign, File decortCommitBookSign2,
                        List<File> decortCoQua, String isrentcam, String camnum) {
        DialogTools.showLoding(this, "温馨提示", "正在上传中。。。");
        Map<String, RequestBody> map = new HashMap<>();
        map.put("houseId", OkhttpUtils.toRequestBody(houseId));
        map.put("approveid", OkhttpUtils.toRequestBody(approveid));
        map.put("decortCoNm", OkhttpUtils.toRequestBody(decortCoNm));
        map.put("decortHead", OkhttpUtils.toRequestBody(decortHead));
        map.put("peopleNum", OkhttpUtils.toRequestBody(peopleNum + ""));
        map.put("qualifiNo", OkhttpUtils.toRequestBody(qualifiNo));
        map.put("decortCoTel", OkhttpUtils.toRequestBody(decortCoTel));
        map.put("decortbeginTm", OkhttpUtils.toRequestBody(decortbeginTm));
        map.put("decortEndTm", OkhttpUtils.toRequestBody(decortEndTm));
        map.put("decortDesc", OkhttpUtils.toRequestBody(decortDesc));
        map.put("isrentcam", OkhttpUtils.toRequestBody(isrentcam));
        map.put("camnum", OkhttpUtils.toRequestBody(camnum));
        if (decortDsnInfo != null) {
            if (decortDsnInfo.size() != 0) {
                for (int i = 0; i < decortDsnInfo.size(); i++) {
                    map.put("decortDsnInfo\";filename=\"" + "imageList1" + i + ".jpg", RequestBody.create(MediaType.parse("image/png"), decortDsnInfo.get(i)));
                }
            }
        }
        if (decortManReguSign != null) {
            map.put("decortManReguSign\";filename=\"" + "imageList" + 2 + ".jpg", RequestBody.create(MediaType.parse("image/png"), decortManReguSign));
        }
        if (decortCommitBookSign != null) {
            map.put("decortCommitBookSign1\";filename=\"" + "imageList" + 3 + ".jpg", RequestBody.create(MediaType.parse("image/png"), decortCommitBookSign));
        }
        if (decortCommitBookSign2 != null) {
            map.put("decortCommitBookSign2\";filename=\"" + "imageList" + 4 + ".jpg", RequestBody.create(MediaType.parse("image/png"), decortCommitBookSign2));
        }
        if (decortCoQua != null) {
            if (decortCoQua.size() != 0) {
                for (int i = 0; i < decortCoQua.size(); i++) {
                    map.put("decortCoQua\";filename=\"" + "imageList5" + i + ".jpg", RequestBody.create(MediaType.parse("image/png"), decortCoQua.get(i)));
                }
            }
        }
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .decortApprove(map)
                .map(new Func1<ResultInfo<Approveid>, ResultInfo<Approveid>>() {
                    @Override
                    public ResultInfo call(ResultInfo<Approveid> resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<Approveid>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("请求错误日志：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo<Approveid> resultInfo) {
                        int code = resultInfo.code;
                        String message = resultInfo.message;
                        DialogTools.dissmiss();
                        switch (code) {
                            case 0:
                                if (resultInfo.data.approveid == -1) {
                                    ToastTools.showShort(MaterialManagementActivity.this, "该房间已提交申请。");
                                    return;
                                }
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("cg", rxBusMsg);
                                ToastTools.showShort(MaterialManagementActivity.this, true, "提交成功！");
                                finish();
                                break;
                            case 1:
                                ToastTools.showShort(MaterialManagementActivity.this, false, message);
                                break;
                            case 2:
                                startActivity(new Intent(MaterialManagementActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    /**
     * 获取待装修信息
     */
    private void getFitmentInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("approveid", approveId + "");
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .houseInfo(map)
                .map(new Func1<ResultInfo<FitmentInfo>, ResultInfo<FitmentInfo>>() {
                    @Override
                    public ResultInfo<FitmentInfo> call(ResultInfo<FitmentInfo> fitmentInfoResultInfo) {
                        return fitmentInfoResultInfo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<FitmentInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("请求错误日志：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo<FitmentInfo> fitmentInfoResultInfo) {
                        int code = fitmentInfoResultInfo.code;
                        String message = fitmentInfoResultInfo.message;
                        switch (code) {
                            case 0:
                                name = fitmentInfoResultInfo.data.name; // 业主姓名
                                tel = fitmentInfoResultInfo.data.tel; // 业主电话
                                hseid = fitmentInfoResultInfo.data.hselst.get(0).hseid;
                                budnm = fitmentInfoResultInfo.data.hselst.get(0).budnm;  // 楼宇名称
                                comnm = fitmentInfoResultInfo.data.hselst.get(0).comnm;  // 小区名称
                                unitnm = fitmentInfoResultInfo.data.hselst.get(0).unitnm; // 单元名称
                                hsearea = fitmentInfoResultInfo.data.hselst.get(0).hsearea; // 房间面积
                                hsecd = fitmentInfoResultInfo.data.hselst.get(0).hsecd; // 房间编号
                                community_id = fitmentInfoResultInfo.data.hselst.get(0).communityid;
                                tv_comiuty.setText(comnm + " " + budnm + "栋" + unitnm + "单元" + hsecd);
                                break;
                            case 1:
                                ToastTools.showShort(MaterialManagementActivity.this, false, message);
                                break;
                            case 3:
                                startActivity(new Intent(MaterialManagementActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }


}
