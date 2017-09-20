package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.HomeData;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.StringUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeCount;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈 宝
 * @Description:修改联系电话
 * @Email 1041121352@qq.com
 * @date 2016/11/18
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_update_mobile
)
public class ChangeMobileActivity extends ColpencilActivity {

    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.et_constant)
    EditText et_constant;
    @Bind(R.id.et_checknum)
    EditText et_checknum;
    @Bind(R.id.tv_get)
    TextView tv_get;
    private String mobile;

    @Override
    protected void initViews(View view) {
        tv_title.setText("修改联系电话");
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.tv_get)
    void getClick() {
        mobile = et_constant.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            NotificationTools.show(this, "手机号码不能为空");
            return;
//        } else if (TextStringUtils.isMobileNO(mobile)) {
//            NotificationTools.show(this, "请输入正确格式的手机号码");
//            return;
        } else {
            HashMap<String, String> params = new HashMap<>();
            params.put("mobile", mobile);
            params.put("flag", 5 + "");
            params.put("terminal", "0");
            RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
                    .createApi(HostApi.class)
                    .getCode(mobile, 5, 0)
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

                        }

                        @Override
                        public void onNext(ResultInfo result) {
                            if (result.code == 0) {
                                TimeCount time = new TimeCount(60000, 1000, tv_get);
                                time.start();//开启倒计时
                                ToastTools.showShort(ChangeMobileActivity.this, true, "验证码已成功发送，注意查收！");
                            } else {
                                ToastTools.showShort(ChangeMobileActivity.this, result.message);
                            }
                        }
                    });
        }
    }

    @OnClick(R.id.btn_reserve)
    void submit() {
        String checknum = et_checknum.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            NotificationTools.show(this, "手机号码不能为空");
            return;
        } else if (TextUtils.isEmpty(checknum)) {
            NotificationTools.show(this, "验证码不能为空");
            return;
        } else {
            Intent intent = new Intent();
            intent.putExtra("mobile", et_constant.getText().toString());
            setResult(0, intent);
            finish();
        }
    }
}
