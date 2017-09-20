package com.colpencil.tenement.View.Activitys.Workbeach;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Event;
import com.colpencil.tenement.Bean.LastRecord;
import com.colpencil.tenement.Bean.Poling;
import com.colpencil.tenement.Bean.Result;
import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Present.Home.StartPolingPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Imples.StartPolingView;
import com.google.gson.Gson;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author 汪 亮
 * @Description: 开始保养/巡检/维修
 * @Email DramaScript@outlook.com
 * @date 2016/8/23
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_startpoling
)
public class StartPolingActivity extends ColpencilActivity implements StartPolingView {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_id)
    TextView tv_id;

    @Bind(R.id.tv_name)
    TextView tv_name;

    @Bind(R.id.tv_date_name)
    TextView tv_date_name;

    @Bind(R.id.tv_date)
    TextView tv_date;

    @Bind(R.id.tv_bei)
    TextView tv_bei;

    @Bind(R.id.tv_loaction)
    TextView tv_loaction;

    @Bind(R.id.tv_record)
    TextView tv_record;

    @Bind(R.id.tv_post)
    TextView tv_post;

    @Bind(R.id.et_bei)
    EditText et_bei;

    private StartPolingPresent polingPresent;


    private Gson gson;
    private String eq_name;
    private String date;

    private int isCode; // 判断是0修改 1重新添加
    private String result;
    private Poling poling;
    private String maintainId = "";
    private String record = "";

    private int flag;
    private String equipmentId = "";
    private String communityId = "";
    private String eqType = "";
    private String content = "";
    private String lastTime = "";
    private int lastEmp;

    private SweetAlertDialog pDialog;
    private int i = -1;
    private String eq_code;
    private String eq_location;
    private int orderId;
    private int user_id;

    private boolean canPost = true;


    @Override
    protected void initViews(View view) {
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        flag = getIntent().getIntExtra("flag", 0);
        isCode = getIntent().getIntExtra("isCode", 0);
        if (flag == 0) {// 查看设备
            equipmentId = getIntent().getStringExtra("equipmentId");
            eqType = getIntent().getStringExtra("eqType");
            ColpencilLogger.e("equipmentId=" + equipmentId);
            DialogTools.showLoding(this, "温馨提示", "正在获取设备信息。。。");
            polingPresent.getLastRecord(equipmentId + "", 2, eqType);
        } else if (flag == -1) {//  关联设备
            orderId = getIntent().getIntExtra("orderId", 0);
            gson = new Gson();
            result = getIntent().getStringExtra("result");
            eqType = getIntent().getStringExtra("eqType");
            poling = gson.fromJson(result, Poling.class);
            DialogTools.showLoding(this, "温馨提示", "正在获取设备信息。。。");
            equipmentId = poling.devId + "";
            eqType = poling.eqType + "";
            polingPresent.getLastRecord(poling.devId + "", 2, eqType);
            ColpencilLogger.e("扫码后设备信息：" + poling.toString());
        } else {//  按正常的走
            if (isCode == 0) {  // 修改
                eq_name = getIntent().getStringExtra("eq_name");
                lastTime = getIntent().getStringExtra("date");
                eq_code = getIntent().getStringExtra("eq_code");
                record = getIntent().getStringExtra("record");
                lastEmp = getIntent().getIntExtra("last_emp", 0);
                maintainId = getIntent().getStringExtra("maintainId");
                eq_location = getIntent().getStringExtra("eq_location");
                equipmentId = getIntent().getStringExtra("equipmentId");
                communityId = getIntent().getStringExtra("communityId");
                eqType = getIntent().getStringExtra("eqtype");
                ColpencilLogger.e("eqType=" + eqType);
                tv_name.setText(eq_name);
                tv_id.setText(eq_code);
                tv_date.setText(lastTime);
                if (TextUtils.isEmpty(record)) {
                    et_bei.setText("暂无！");
                } else {
                    et_bei.setText(record);
                }
                tv_loaction.setText(eq_location);
                DialogTools.showLoding(this, "温馨提示", "正在获取设备信息。。。");
                polingPresent.getLastRecord(equipmentId + "", flag, eqType);
            } else {  // 从二维码跳转过来
                gson = new Gson();
                result = getIntent().getStringExtra("result");
                poling = gson.fromJson(result, Poling.class);
                DialogTools.showLoding(this, "温馨提示", "正在获取设备信息。。。");
                equipmentId = poling.devId + "";
                eqType = poling.eqType + "";
                polingPresent.getLastRecord(poling.devId + "", flag, eqType);
                ColpencilLogger.e("扫码后设备信息：" + poling.toString());
            }
        }


        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        switch (flag) {
            case -1:
                tv_title.setText("关联设备");
                tv_date_name.setText("日期：");
                tv_bei.setText("设备备注：");
                tv_record.setText("查看设备记录");
                et_bei.setHint("请输入设备备注");
                tv_record.setVisibility(View.INVISIBLE);
                et_bei.setEnabled(true);
                break;
            case 0:
                tv_title.setText("查看设备");
                tv_date_name.setText("日期：");
                tv_bei.setText("设备备注：");
                tv_record.setText("查看设备记录");
                et_bei.setHint("请输入设备备注");
                et_bei.setEnabled(true);
                tv_record.setVisibility(View.INVISIBLE);
                break;
            case 1:  // 巡检
                if (isCode == 0) {
                    tv_title.setText("修改巡检记录");
                } else {
                    tv_title.setText("开始巡检");
                }
                tv_date_name.setText("上次巡检日期：");
                tv_bei.setText("巡检备注：");
                tv_record.setText("查看巡检记录");
                et_bei.setHint("请输入巡检备注");
                et_bei.setEnabled(true);
                break;
            case 2:  // 维修
                if (isCode == 0) {
                    tv_title.setText("修改维修记录");
                } else {
                    tv_title.setText("开始维修");
                }
                tv_date_name.setText("上次维修日期：");
                tv_bei.setText("维修备注：");
                tv_record.setText("查看维修记录");
                et_bei.setHint("请输入维修备注");
                et_bei.setEnabled(true);
                break;
            case 3:  // 保养
                if (isCode == 0) {
                    tv_title.setText("修改保养记录");
                } else {
                    tv_title.setText("开始保养");
                }
                tv_date_name.setText("上次保养日期：");
                tv_bei.setText("保养备注：");
                tv_record.setText("查看保养记录");
                et_bei.setHint("请输入保养备注");
                et_bei.setEnabled(true);
                break;
        }
        final Event event = new Event();
        tv_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到设备管理下面的三个中的一个
                switch (flag) {
                    case 1:
                        event.setFlag(1);
                        event.setDevCode(eq_code);
                        RxBus.get().post("start", event);
                        startActivity(new Intent(StartPolingActivity.this, EquipmentControlActivity.class));
                        break;
                    case 2:
                        event.setFlag(2);
                        event.setDevCode(eq_code);
                        RxBus.get().post("start", event);
                        startActivity(new Intent(StartPolingActivity.this, EquipmentControlActivity.class));
                        break;
                    case 3:
                        event.setFlag(3);
                        event.setDevCode(eq_code);
                        RxBus.get().post("start", event);
                        startActivity(new Intent(StartPolingActivity.this, EquipmentControlActivity.class));
                        break;
                }
            }
        });

        tv_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canPost == true) {
                    submit();
                }
            }
        });
    }

    private void submit() {
        // 备注
        content = et_bei.getText().toString();
        String currTime = TimeUtil.getTimeStrFromMillis(System.currentTimeMillis()); // 维护时间
        if (TextUtils.isEmpty(content)) {
            ToastTools.showShort(this, false, "填写相关备注为空！");
            return;
        }
        pDialog = new SweetAlertDialog(StartPolingActivity.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("正在提交中")
        ;
        pDialog.show();
        pDialog.setCancelable(true);
        new CountDownTimer(800 * 7, 800) {
            public void onTick(long millisUntilFinished) {
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
                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                        break;
                }
            }

            @Override
            public void onFinish() {

            }

        }.start();
        if (flag == -1) {
            polingPresent.linkDev(equipmentId, orderId + "", content, eqType);
        } else {
            if (flag == 0) {
                polingPresent.post(1, equipmentId, communityId, currTime, content, lastTime, lastEmp + "", maintainId, eqType);
            } else {
                polingPresent.post(flag, equipmentId, communityId, currTime, content, lastTime, lastEmp + "", maintainId, eqType);
            }
        }

    }

    @Override
    public ColpencilPresenter getPresenter() {
        polingPresent = new StartPolingPresent();
        return polingPresent;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void post(Result result) {
        int code = result.code;
        String message = result.message;
        switch (code) {
            case 0:
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(0);
                RxBus.get().post("spa", rxBusMsg);
                pDialog.setTitleText(message)
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                finish();
                mAm.finishActivity(ScanCodeActivity.class);
                ToastTools.showShort(this, true, message);
                break;
            case 1:
                ToastTools.showShort(this, false, message);
                pDialog.setTitleText(message)
                        .setConfirmText("重新提交")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                submit();
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
            case 3:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            default:
                pDialog.dismissWithAnimation();
                ToastTools.showShort(this, false, message);
                break;
        }
        i = -1;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void getLast(EntityResult<LastRecord> result) {
        DialogTools.dissmiss();
        int code = result.getCode();
        String message = result.getMessage();
        ColpencilLogger.e("设备信息：" + result.getData().toString());
        switch (code) {
            case 0:
                user_id = SharedPreferencesUtil.getInstance(this).getInt("user_id", 0);
                lastTime = result.getData().lastTime;
                eq_location = result.getData().eqLocation;
                eq_code = result.getData().eqCode;
                eq_name = result.getData().eqName;
                if (isCode != 0 || flag == 0) {  // 只有扫描请求回来的数据赋值
                    tv_loaction.setText(eq_location);
                    et_bei.setText(result.getData().currContent);
                    lastEmp = result.getData().lastEmp;
                    if (TextUtils.isEmpty(lastTime)) {
                        tv_date.setText("暂无");
                    } else {
                        tv_date.setText(lastTime);
                    }
                    tv_id.setText(eq_code);
                    tv_name.setText(eq_name);
                    if (result.getData().currEmp != -1) {
                        if (result.getData().currEmp != user_id) {
                            // 说明有人修改  不是自己修改的  所以不能修改或者提交了
                            tv_post.setBackground(getResources().getDrawable(R.drawable.rect_gre));
                            canPost = false;
                            ToastTools.showShort(this, false, "该设备他人已维护！");
                        } else {
                            if (flag != 0) {
                                MaterialDialog show = new MaterialDialog.Builder(StartPolingActivity.this)
                                        .title("温馨提示")
                                        .content("改设备已维修，是否进行修改？")
                                        .positiveText(R.string.online_yes)
                                        .negativeText(R.string.online_no)
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                canPost = true;
                                                tv_post.setBackground(getResources().getDrawable(R.drawable.rect_blue));
                                            }
                                        })
                                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                finish();
                                            }
                                        })
                                        .show();
                            }
                        }
                    } else {

                    }
                }

                if (result.getData().currEmp != -1) {
                    if (user_id != result.getData().currEmp) {
                        // 说明有人修改  不是自己修改的  所以不能修改或者提交了
                        tv_post.setBackground(getResources().getDrawable(R.drawable.rect_gre));
                        canPost = false;
                        ToastTools.showShort(this, false, "该设备他人已维护！");
                    } else {
                        maintainId = result.getData().maintainId + "";
                    }
                }
                break;
            case 1:
                ToastTools.showShort(this, false, message);
                break;
            case 3:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case 5:
                ToastTools.showShort(this, false, message);
                finish();
                break;
            default:
                ToastTools.showShort(this, false, message);
                break;
        }
    }

    @Override
    public void linkDev(Result result) {
        switch (result.code) {
            case 0:
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(1);
                RxBus.get().post("stateChange",rxBusMsg);
                finish();
                mAm.finishActivity(ScanCodeActivity.class);
                ToastTools.showShort(this, true, "关联设备成功！");
                break;
            case 1:
                ToastTools.showShort(this, result.message);
                break;
            case 3:
                startActivity(new Intent(this, LoginActivity.class));
                break;

        }
    }

    @Override
    public void loadError(String msg) {
        if (pDialog != null) {
            pDialog.dismiss();
        }
        DialogTools.dissmiss();
        ToastTools.showShort(this, false, "请求失败！");
    }
}
