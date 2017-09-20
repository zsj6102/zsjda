package com.colpencil.tenement.View.Activitys.ToayTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Present.TodayTask.AddTaskPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Imples.AddTaskView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import org.feezu.liuli.timeselector.TimeSelector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author 汪 亮
 * @Description: 添加任务
 * @Email DramaScript@outlook.com
 * @date 2016/9/26
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_add_task
)
public class AddTaskActivity extends ColpencilActivity implements View.OnClickListener, AddTaskView {

    @Bind(R.id.rl_select_time)
    RelativeLayout rl_select_time;

    @Bind(R.id.tv_time)
    TextView tv_time;

    @Bind(R.id.et_content)
    EditText et_content;

    @Bind(R.id.iv_haf)
    ImageView iv_haf;

    @Bind(R.id.iv_ten)
    ImageView iv_ten;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.btn_ok)
    Button btn_ok;

    private boolean flag = false;
    private boolean flag2 = false;

    private SweetAlertDialog pDialog;

    private int i = -1;
    private AddTaskPresent present;

    private String communityId;

    private String remindTime;
    private String time;
    private String content;
    private long formatDate;
    private long tenMini;

    @Override
    protected void initViews(View view) {
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        communityId = SharedPreferencesUtil.getInstance(this).getString(StringConfig.PLOTID);
        tv_title.setText("添加任务");
        rl_select_time.setOnClickListener(this);
        iv_haf.setOnClickListener(this);
        iv_ten.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        ll_left.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new AddTaskPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_select_time:
                TimeSelector timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        tv_time.setText(time);
                    }
                }, TimeUtil.getTimeStrFromMillis(System.currentTimeMillis()), "2019-11-29 21:54");
                timeSelector.show();

                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.iv_haf:
                if (flag == false) {
                    iv_haf.setImageDrawable(getResources().getDrawable(R.mipmap.open));
                    flag = true;
                } else {
                    iv_haf.setImageDrawable(getResources().getDrawable(R.mipmap.close));
                    flag = false;
                }
                break;
            case R.id.iv_ten:
                if (flag2 == false) {
                    iv_ten.setImageDrawable(getResources().getDrawable(R.mipmap.open));
                    flag2 = true;
                } else {
                    iv_ten.setImageDrawable(getResources().getDrawable(R.mipmap.close));
                    flag2 = false;
                }
                break;
            case R.id.btn_ok:
                time = tv_time.getText().toString();
                content = et_content.getText().toString();
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(time));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                formatDate = c.getTimeInMillis();
                if (flag==true){
                    if (flag2==true){
                        remindTime = "10";
                        tenMini = formatDate - 600000;
                    }else {
                        remindTime = "30";
                        tenMini = formatDate - 1800000;
                    }
                }else {
                    if (flag2==true){
                        remindTime = "10";
                        tenMini = formatDate - 600000;
                    }else {
                        tenMini = formatDate;
                    }
                }
                if (TextUtils.isEmpty(content)){
                    ToastTools.showShort(this,false,"您还未填写任务描述");
                    return;
                }
                if (TextUtils.isEmpty(time)){
                    ToastTools.showShort(this,false,"您还未选择时间哦");
                    return;
                }
                if (tenMini<System.currentTimeMillis()){
                    ToastTools.showShort(this,false,"您提醒的时间不能小于当前时间!");
                    return;
                }
                present.addTask(content, time,TimeUtil.getTimeStrFromMillis(tenMini),communityId);
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                pDialog = new SweetAlertDialog(AddTaskActivity.this, SweetAlertDialog.PROGRESS_TYPE)
                        .setTitleText("正在提交中");
                pDialog.setCancelable(true);
                pDialog.show();
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
                break;
        }
    }

    @Override
    public void result(EntityResult entityResult) {
        int code = entityResult.getCode();
        String message = entityResult.getMessage();
        switch (code) {
            case 0:  // 成功
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(0);
                RxBus.get().post("ata",rxBusMsg);
                pDialog.setTitleText("添加成功哦！")
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                finish();
                ToastTools.showShort(this,true,"添加成功");
                break;
            case 1:  // 失败
                pDialog.setTitleText("添加失败，请检查网络是否通畅。")
                        .setConfirmText("重新提交")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                present.addTask(content,time,remindTime,communityId);
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                pDialog.dismissWithAnimation();
                            }
                        })
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                break;
            case 3: // 未登录
                pDialog.dismiss();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case 4: // 验证失败
                pDialog.dismiss();
                ToastTools.showShort(this,false,message);
                break;
        }

        i = -1;
    }

    @Override
    public void loadError(String msg) {
        pDialog.dismiss();
        ToastTools.showShort(this,false,"请求失败！");
    }
}
