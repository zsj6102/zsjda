package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeCount;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilListView;

import java.util.HashMap;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 添加未注册过的新成员
 * @Email DramaScript@outlook.com
 * @date 2016/11/15
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_newmember
)
public class AddNewMemberActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.rl_lei)
    RelativeLayout rl_lei;

    @Bind(R.id.tv_lei)
    TextView tv_lei;

    @Bind(R.id.et_phone)
    EditText et_phone;

    @Bind(R.id.et_yan)
    EditText et_yan;

    @Bind(R.id.tv_get)
    TextView tv_get;

    @Bind(R.id.et_true_name)
    TextView et_true_name;

    @Bind(R.id.ll_man)
    LinearLayout ll_man;

    @Bind(R.id.iv_man)
    ImageView iv_man;

    @Bind(R.id.ll_women)
    LinearLayout ll_women;

    @Bind(R.id.iv_women)
    ImageView iv_women;

    @Bind(R.id.lv_select)
    ColpencilListView lv_select;

    @Bind(R.id.tv_ok)
    TextView tv_ok;
    private String mobile;

    private int sex = 1;
    private int usrTpCd = 0;
    private String validCd;
    private String usrNm;
    private int hseid;

    @Override
    protected void initViews(View view) {
        hseid = getIntent().getIntExtra("hseid", 0);
        tv_title.setText("添加成员");
        rl_lei.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        tv_get.setOnClickListener(this);
        ll_man.setOnClickListener(this);
        ll_women.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.rl_lei:
                // TODO: 2016/11/15 获取类型

                break;
            case R.id.tv_get:
                mobile = et_phone.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    NotificationTools.show(this, "输入的手机号码为空！");
                    return;
                }
                if (!TextStringUtils.isMobileNO(mobile)) {
                    NotificationTools.show(this, "输入的手机号码不合法！");
                    return;
                }
                // TODO: 2016/11/15 获取验证码操作
                getCode();
                break;
            case R.id.ll_man:
                iv_man.setImageDrawable(getResources().getDrawable(R.mipmap.check));
                iv_women.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                break;
            case R.id.ll_women:
                iv_women.setImageDrawable(getResources().getDrawable(R.mipmap.check));
                iv_man.setImageDrawable(getResources().getDrawable(R.mipmap.un_check));
                break;
            case R.id.tv_ok:
                if (usrTpCd == 0) {
                    NotificationTools.show(this, "您还未选择添加类型！");
                    return;
                }
                mobile = et_phone.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    NotificationTools.show(this, "输入的手机号码为空！");
                    return;
                }
                if (!TextStringUtils.isMobileNO(mobile)) {
                    NotificationTools.show(this, "输入的手机号码不合法！");
                    return;
                }
                validCd = et_yan.getText().toString();
                if (TextUtils.isEmpty(validCd)) {
                    NotificationTools.show(this, "输入的验证码为空！");
                    return;
                }
                usrNm = et_true_name.getText().toString();
                // TODO: 2016/11/15 提交操作
                addFamilyMember();
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .getCode(mobile, 6, 0)
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
                        switch (resultInfo.code) {
                            case 0:
                                TimeCount time = new TimeCount(60000, 1000, tv_get);
                                time.start();//开启倒计时
                                ToastTools.showShort(AddNewMemberActivity.this, true, "验证码已成功发送，注意查收！");
                                break;
                            case 1:
                                ToastTools.showShort(AddNewMemberActivity.this, false, resultInfo.message);
                                break;
                        }
                    }
                });
    }

    /**
     * 添加家庭成员
     */
    private void addFamilyMember() {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("hseId", hseid + "");
        map.put("usrTpCd", usrTpCd + "");
        map.put("usrNm", usrNm);
        map.put("validCd", validCd);
        map.put("sex", sex + "");
        map.put("isRegisted", "0");
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
                                finish();
                                ToastTools.showShort(AddNewMemberActivity.this, true, "提交成功！");
                                break;
                            case 1:
                                ToastTools.showShort(AddNewMemberActivity.this, false, message);
                                break;
                            case 3:
                                startActivity(new Intent(AddNewMemberActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }
}
