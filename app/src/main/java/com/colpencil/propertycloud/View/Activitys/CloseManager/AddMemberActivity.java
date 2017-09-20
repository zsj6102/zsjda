package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.HouseInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.Room;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.Present.CloseManager.AddMemberPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.WarnDialog;
import com.colpencil.propertycloud.Ui.BottomDialog;
import com.colpencil.propertycloud.View.Activitys.ApiCloudActivity;
import com.colpencil.propertycloud.View.Activitys.Home.LoadUriActivity;
import com.colpencil.propertycloud.View.Activitys.Home.MaterialManagementActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.SingleAdapter;
import com.colpencil.propertycloud.View.Imples.AddMemberView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeCount;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilListView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.colpencil.propertycloud.Overall.HostUrl.BASE_HOST_PATH;

/**
 * @author 汪 亮
 * @Description: 添加成员
 * @Email DramaScript@outlook.com
 * @date 2016/9/12
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_add_member
)
public class AddMemberActivity extends ColpencilActivity implements AddMemberView, View.OnClickListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_ok)
    TextView tv_ok;

    @Bind(R.id.tv_warn)
    TextView tv_warn;

    @Bind(R.id.et_yan)
    EditText et_yan;

    @Bind(R.id.tv_get)
    TextView tv_get;

    @Bind(R.id.et_true_name)
    EditText et_true_name;

    @Bind(R.id.et_identy)
    EditText et_identy;

    @Bind(R.id.ll_man)
    LinearLayout ll_man;

    @Bind(R.id.iv_man)
    ImageView iv_man;

    @Bind(R.id.ll_women)
    LinearLayout ll_women;

    @Bind(R.id.iv_women)
    ImageView iv_women;

    @Bind(R.id.rl_jue)
    RelativeLayout rl_jue;

    @Bind(R.id.tv_quan)
    TextView tv_quan;

    @Bind(R.id.lv_select)
    ColpencilListView lv_select;

    @Bind(R.id.ll_ziliao)
    LinearLayout ll_ziliao;

    private AddMemberPresent present;

    private ImagePicker imagePicker;

    private SingleAdapter adapter;
    private List<HouseInfo> roomList = new ArrayList<>();
    private boolean isExists;
    private String mobile;

    private int sex = 1;
    private int usrTpCd = 0;
    private int hseId = 0;
    private String name = "";
    private String nicname = "";
    private String code;
    private int hseid;

    @Bind(R.id.tv_name)
    TextView tv_name;

    @Bind(R.id.checkbox)
    CheckBox checkbox;
    private String info;

    @Bind(R.id.ll_read)
    LinearLayout ll_read;

    @Bind(R.id.iv_agree)
    ImageView iv_agree;

    @Bind(R.id.tv_xie)
    TextView tv_xie;

    private boolean isAgree = true;
    private String mobile1;

    @Override
    protected void initViews(View view) {
        mobile1 = SharedPreferencesUtil.getInstance(this).getString("mobile");
        isExists = getIntent().getBooleanExtra("isExists", false);
        this.mobile = getIntent().getStringExtra("mobile");
        hseid = getIntent().getIntExtra("hseid", 0);
        info = getIntent().getStringExtra("info");
        ColpencilLogger.e("hseid=" + hseid);
        if (isExists) {
            ll_ziliao.setVisibility(View.GONE);
            tv_warn.setText("在大爱齐家系统中已有此人，请输入该手机号收到的验证码");
        } else {
            ll_ziliao.setVisibility(View.VISIBLE);
            tv_warn.setText("请输入该手机号收到的验证码");
        }
        tv_name.setText(info);
        checkbox.setChecked(true);
        tv_title.setText("添加成员");
        ll_left.setOnClickListener(this);
        ll_man.setOnClickListener(this);
        ll_women.setOnClickListener(this);
        tv_get.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        rl_jue.setOnClickListener(this);
        adapter = new SingleAdapter(roomList, AddMemberActivity.this);
        lv_select.setAdapter(adapter);
        loadJu();

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
                Intent intent = new Intent(AddMemberActivity.this, LoadUriActivity.class);
                intent.putExtra("url", BASE_HOST_PATH + "/property/agreement/user-agreement.do?flag=family");
                intent.putExtra("title","用户协议");
                startActivity(intent);
            }
        });


    }

    /**
     * 获取居住列表
     */
    private void loadJu() {
        HashMap<String, String> map = new HashMap<>();
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
                    public void onNext(ResultListInfo<HouseInfo> houseInfoResultListInfo) {
                        int code = houseInfoResultListInfo.code;
                        String message = houseInfoResultListInfo.message;
                        switch (code) {
                            case 0:
                                roomList.addAll(houseInfoResultListInfo.data);
                                adapter.notifyDataSetChanged();
                                break;
                            case 1:
                                ToastTools.showShort(AddMemberActivity.this, false, message);
                                break;
                            case 3:
                                startActivity(new Intent(AddMemberActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new AddMemberPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void isAdd(boolean isAdd) {

    }

    @Override
    public void roomList(List<Room> list) {

    }

    private void PickNum() {
        int position = lv_select.getCheckedItemPosition();
        if (ListView.INVALID_POSITION != position) {
            hseId = roomList.get(position).getHouse_id();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_get:// 获取验证码
                if (isExists) {
                    getCode(6);
                } else {
                    getCode(1);
                }
                break;
            case R.id.ll_man: //
                iv_man.setImageDrawable(getResources().getDrawable(R.mipmap.check));
                iv_women.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                sex = 1;
                break;
            case R.id.ll_women: //
                iv_man.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                iv_women.setImageDrawable(getResources().getDrawable(R.mipmap.check));
                sex = 0;
                break;
            case R.id.tv_ok: // 提交
                if (!isAgree){
                    ToastTools.showShort(this,"请先阅读上方协议");
                    return;
                }
                code = et_yan.getText().toString();
                String quan = tv_quan.getText().toString();
                if ("产权人".equals(quan)) {
                    usrTpCd = 1;
                } else if ("租户".equals(quan)) {
                    usrTpCd = 3;
                } else if ("家庭成员".equals(quan)) {
                    usrTpCd = 4;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastTools.showShort(this, "请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(quan)) {
                    ToastTools.showShort(this, "你还未添加角色");
                    return;
                }
                if (isExists) {
                    addFamilyMember();
                } else {
                    name = et_true_name.getText().toString();
                    nicname = et_identy.getText().toString();
                    if (TextUtils.isEmpty(name)) {
                        ToastTools.showShort(this, "你还未填写真实姓名");
                        return;
                    }
                    if (TextUtils.isEmpty(nicname)) {
                        ToastTools.showShort(this, "你还未填写昵称");
                        return;
                    }
                    addFamilyMember();
                }
                break;
            case R.id.rl_jue:
                final List<String> list = new ArrayList<>();
//                list.add("产权人");
                list.add("家庭成员");
                list.add("租户");
                new BottomDialog.Builder(this)
                        .setTitle("选择角色")
                        .setDataList(list)
                        .setPositiveText("确认")
                        .setNegativeText("取消")
                        .onPositive(new BottomDialog.ButtonCallback() {
                            @Override
                            public void onClick(@NonNull BottomDialog dialog) {
                                tv_quan.setText(list.get(dialog.position));
                            }
                        })
                        .onNegative(new BottomDialog.ButtonCallback() {
                            @Override
                            public void onClick(@NonNull BottomDialog dialog) {

                            }
                        }).show();
                break;
        }
    }

    private void getCode(int flag) {
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getCode(mobile, flag, 0)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo resultInfo) {
                        int code = resultInfo.code;
                        String message = resultInfo.message;
                        switch (code) {
                            case 0:
                                TimeCount time = new TimeCount(60000, 1000, tv_get);
                                time.start();//开启倒计时
                                ToastTools.showShort(AddMemberActivity.this, true, "验证码已成功发送！");
                                break;
                            case 1:
                                ToastTools.showShort(AddMemberActivity.this, false, message);
                                break;
                            case 3:
                                startActivity(new Intent(AddMemberActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    /**
     * 添加成员
     */
    private void addFamilyMember() {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("hseId", hseid + "");
        map.put("usrTpCd", usrTpCd + "");
        map.put("usrNm", name);
        map.put("validCd", code);
        map.put("nickName", nicname);
        map.put("sex", sex + "");
        map.put("isRegisted", isExists + "");
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .addFamilyMember(map)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo resultInfo) {
                        int code = resultInfo.code;
                        String message = resultInfo.message;
                        switch (code) {
                            case 0:
                                mobile1.substring(mobile1.length()-4,mobile1.length());
                                MobclickAgent.onEvent(AddMemberActivity.this, "addMenber");
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("add", rxBusMsg);
                                WarnDialog.showInfoAdd(AddMemberActivity.this, "提交成功！当前手机号可直接登录大爱齐家，默认密码为您的手机号前两位+后四位（若账号已注册，密码不变，可直接使用业主功能）。如需电子钥匙，请到物业客服中心领取电子钥匙。");
                                mAm.finishActivity(AddMemberBeforeActivity.class);
                                break;
                            case 1:
                                ToastTools.showShort(AddMemberActivity.this, false, message);
                                break;
                            case 3:
                                startActivity(new Intent(AddMemberActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }
}
