package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.IsExists;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.Present.CloseManager.AddBeforePresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Imples.AddBeforeView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeCount;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.HashMap;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 添加成员之前
 * @Email DramaScript@outlook.com
 * @date 2016/9/13
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_add_before
)
public class AddMemberBeforeActivity extends ColpencilActivity implements View.OnClickListener, AddBeforeView {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_next)
    TextView tv_next;


    @Bind(R.id.et_phone)
    EditText et_phone;

    private AddBeforePresent present;
    private String mobile;

    private int hseid;
    private String info;

    @Override
    protected void initViews(View view) {
        hseid = getIntent().getIntExtra("hseid", 0);
        info = getIntent().getStringExtra("info");
        tv_title.setText("添加成员");
        ll_left.setOnClickListener(this);
        tv_next.setOnClickListener(this);


    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new AddBeforePresent();
        return present;
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
            case R.id.tv_next:
                mobile = et_phone.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    ToastTools.showShort(this, "输入的手机号码为空！");
                    return;
                }
                if (!TextStringUtils.isMobileNO(mobile)) {
                    ToastTools.showShort(this, "输入的手机号码不合法！");
                    return;
                }
                checkPhone(mobile);
                break;
        }
    }

    /**
     * 检查电话是否存在
     */
    private void checkPhone(String phone) {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .checkPhone(map)
                .map(new Func1<ResultInfo<IsExists>, ResultInfo<IsExists>>() {
                    @Override
                    public ResultInfo<IsExists> call(ResultInfo<IsExists> resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<IsExists>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo<IsExists> resultInfo) {
                        switch (resultInfo.code) {
                            case 0:
                                // TODO: 2016/11/18 跳转到下一个界面
                                Intent intent = new Intent(AddMemberBeforeActivity.this, AddMemberActivity.class);
                                intent.putExtra("isExists", resultInfo.data.isExists);
                                intent.putExtra("mobile",mobile);
                                intent.putExtra("hseid",hseid);
                                intent.putExtra("info",info);
                                startActivity(intent);
                                break;
                            case 1:
                                ToastTools.showShort(AddMemberBeforeActivity.this, false, resultInfo.message);
                                break;
                            case 3:
                                startActivity(new Intent(AddMemberBeforeActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    /**
     * 校验验证码
     *
     * @param mobile
     * @param flag
     * @param validCd
     */
    private void chkValidCode(String mobile, int flag, String validCd) {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("flag", flag + "");
        map.put("validCd", validCd);
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .chkValidCode(map)
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
                                Intent intent = new Intent(AddMemberBeforeActivity.this, AddMemberActivity.class);
                                startActivity(intent);
                                break;
                            case 1:
                                ToastTools.showShort(AddMemberBeforeActivity.this, false, resultInfo.message);
                                break;
                        }
                    }
                });
    }

    @Override
    public void codeInfo(ResultInfo code) {

    }
}
