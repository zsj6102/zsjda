package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ApplaInfo;
import com.colpencil.propertycloud.Bean.Approveid;
import com.colpencil.propertycloud.Bean.CurProgress;
import com.colpencil.propertycloud.Bean.FitmentInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.google.gson.Gson;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.OkhttpUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import org.feezu.liuli.timeselector.TimeSelector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 装修申请表
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/15
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_decorate_apply
)
public class DecorateApplyActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.tv_ok)
    TextView tv_ok;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_name)
    TextView tv_name;

    @Bind(R.id.tv_dong)
    TextView tv_dong;

    @Bind(R.id.tv_phone)
    TextView tv_phone;

    @Bind(R.id.tv_ari)
    TextView tv_ari;

    @Bind(R.id.tv_time1)
    TextView tv_time1;

    @Bind(R.id.tv_time2)
    TextView tv_time2;

    @Bind(R.id.et_company)
    EditText et_company;

    @Bind(R.id.et_person)
    EditText et_person;

    @Bind(R.id.et_acount)
    EditText et_acount;

    @Bind(R.id.et_zi)
    EditText et_zi;

    @Bind(R.id.et_com_tel)
    EditText et_com_tel;

    @Bind(R.id.ll_zi)
    LinearLayout ll_zi;

    @Bind(R.id.iv_zi)
    ImageView iv_zi;

    @Bind(R.id.et_content)
    EditText et_content;

    private boolean isCheck = false;
    private String startTime = "";
    private String endTime = "";
    private String company = "";
    private String person = "";
    private String acount = "";
    private String ziZhi = "";
    private String comTel = "";
    private String content = "";
    private Gson gson;
    private int type;
    private int aprovid;
    private int houseid;
    private CurProgress curProgress;

    @Override
    protected void initViews(View view) {
        String name = getIntent().getStringExtra("name");
        String tel = getIntent().getStringExtra("tel");
        String hsearea = getIntent().getStringExtra("hsearea");
        String room = getIntent().getStringExtra("room");
        type = getIntent().getIntExtra("type", 0);
        aprovid = getIntent().getIntExtra("aprovid", 0);
        houseid = getIntent().getIntExtra("houseid", 0);
        if (type==0){ // 新装修进来
            tv_name.setText(name);
            tv_dong.setText(room);
            tv_phone.setText(tel);
            tv_ari.setText(hsearea+"㎡");
        }else if (type==1){//  装修进度 未填写进来
            // TODO: 2016/11/23 获取房间信息
            getFitmentInfo();
        }else {// 进度中提交了，要修改进来
            getFitmentInfo();
        }
        ColpencilLogger.e("------------type="+type);
        tv_title.setText("装修申请表");
        ll_left.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        tv_time1.setOnClickListener(this);
        tv_time2.setOnClickListener(this);
        ll_zi.setOnClickListener(this);
    }

    /**
     * 获取待装修信息
     */
    private void getFitmentInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("approveid", aprovid + "");
        map.put("houseid", houseid + "");
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
                                String Sname = fitmentInfoResultInfo.data.name; // 业主姓名
                                String tel = fitmentInfoResultInfo.data.tel; // 业主电话
                                String budnm = fitmentInfoResultInfo.data.hselst.get(0).budnm;  // 楼宇名称
                                String comnm = fitmentInfoResultInfo.data.hselst.get(0).comnm;  // 小区名称
                                String unitnm = fitmentInfoResultInfo.data.hselst.get(0).unitnm; // 单元名称
                                String hsearea = fitmentInfoResultInfo.data.hselst.get(0).hsearea; // 房间面积
                                String hsecd = fitmentInfoResultInfo.data.hselst.get(0).hsecd; // 房间编号
                                tv_dong.setText(comnm + " " + budnm + "栋" + unitnm + "单元" + hsecd);
                                tv_name.setText(Sname);
                                tv_phone.setText(tel);
                                tv_ari.setText(hsearea+"㎡");
                                if (type==2){
                                    et_company.setText(fitmentInfoResultInfo.data.decortCoNm);
                                    et_person.setText(fitmentInfoResultInfo.data.decortHead);
                                    et_acount.setText(fitmentInfoResultInfo.data.peopleNum+"");
                                    et_zi.setText(fitmentInfoResultInfo.data.qualifiNo);
                                    et_com_tel.setText(fitmentInfoResultInfo.data.decortCoTel);
                                    tv_time1.setText(fitmentInfoResultInfo.data.decortbeginTm);
                                    tv_time2.setText(fitmentInfoResultInfo.data.decortEndTm);
                                    et_content.setText(fitmentInfoResultInfo.data.decortDesc);
                                }
                                break;
                            case 1:
                                ToastTools.showShort(DecorateApplyActivity.this, false, message);
                                break;
                            case 3:
                                startActivity(new Intent(DecorateApplyActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
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
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_zi:
                if (isCheck){
                    et_company.setText("");
                    iv_zi.setImageDrawable(getResources().getDrawable(R.mipmap.fit_uncheck));
                    isCheck = false;
                }else {
                    et_company.setText("业主自建工程队");
                    iv_zi.setImageDrawable(getResources().getDrawable(R.mipmap.fit_check));
                    isCheck = true;
                }
                break;
            case R.id.tv_time1:
                TimeSelector timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {

                    @Override
                    public void handle(String time) {
                        startTime = time.substring(0, 10);
                        tv_time1.setText(startTime);
                    }
                },TimeUtil.getTimeStrFromMillis(System.currentTimeMillis()), "2020-11-29 21:54");
                timeSelector.setMode(TimeSelector.MODE.YMD);
                timeSelector.show();
                break;
            case R.id.tv_time2:
                TimeSelector timeSelector2 = new TimeSelector(this, new TimeSelector.ResultHandler() {

                    @Override
                    public void handle(String time) {
                        endTime = time.substring(0, 10);
                        tv_time2.setText(endTime);
                    }
                }, TimeUtil.getTimeStrFromMillis(System.currentTimeMillis()), "2020-11-29 21:54");
                timeSelector2.setMode(TimeSelector.MODE.YMD);
                timeSelector2.show();
                break;
            case R.id.tv_ok:

                company = et_company.getText().toString();
                person = et_person.getText().toString();
                acount = et_acount.getText().toString();
                ziZhi = et_zi.getText().toString();
                comTel = et_com_tel.getText().toString();
                content = et_content.getText().toString();
                if (TextUtils.isEmpty(company)){
                    ToastTools.showShort(this,"装修公司不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(person)){
                    ToastTools.showShort(this,"装修负责人不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(acount)){
                    ToastTools.showShort(this,"装修人数不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(ziZhi)){
                    ToastTools.showShort(this,"资质证号不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(comTel)){
                    ToastTools.showShort(this,"装修公司电话不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(content)){
                    ToastTools.showShort(this,"申请装修范围和内容不能为空！");
                    return;
                }
                if (!TextStringUtils.isMobileNO(comTel)){
                    ToastTools.showShort(this,"您输入的手机号码不合法！");
                    return;
                }
                Calendar c = Calendar.getInstance();
                Calendar c2 = Calendar.getInstance();
                try {
                    c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(startTime));
                    c2.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(endTime));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long startTimeInMillis = c.getTimeInMillis();
                long endTimeInMillis = c2.getTimeInMillis();
                if (endTimeInMillis<startTimeInMillis){
                    NotificationTools.show(this,"申请日期不正确！");
                    return;
                }
                if (type==0){
                    ApplaInfo applaInfo = new ApplaInfo();
                    applaInfo.decortCoNm = company;
                    applaInfo.decortHead = person;
                    applaInfo.peopleNum = acount;
                    applaInfo.qualifiNo = ziZhi;
                    applaInfo.decortCoTel = comTel;
                    applaInfo.decortDesc = content;
                    applaInfo.decortbeginTm = startTime;
                    applaInfo.decortEndTm = endTime;
                    gson = new Gson();
                    String appl = gson.toJson(applaInfo);
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setMsg(appl);
                    rxBusMsg.setType(2);
                    RxBus.get().post("MaterialManagement",rxBusMsg);
                    finish();
                }else {
                    submit(houseid+"",aprovid+"",company,person,acount,ziZhi,comTel,startTime,endTime,content);
                }

                break;
        }
    }

    private void submit(String houseId, String approveid,String decortCoNm,String decortHead,
                        String peopleNum,String qualifiNo,String decortCoTel,String decortbeginTm,
                        String decortEndTm,String decortDesc){
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
                        switch (code) {
                            case 0:
                                if (resultInfo.data.approveid==-1){
                                    ToastTools.showShort(DecorateApplyActivity.this,"改房间已提交申请。");
                                    return;
                                }
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("mater", rxBusMsg);
                                finish();
                                ToastTools.showShort(DecorateApplyActivity.this, true, "提交成功！");
                                break;
                            case 1:
                                ToastTools.showShort(DecorateApplyActivity.this, false, message);
                                break;
                            case 2:
                                startActivity(new Intent(DecorateApplyActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }
}
