package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.HashMap;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


@ActivityFragmentInject(
        contentViewId = R.layout.activity_check_statue
)
public class ChecKStatueActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_reason)
    TextView tv_reason;

    @Bind(R.id.tv_cun)
    TextView tv_cun;

    @Bind(R.id.tv_comit)
    TextView tv_comit;
    private String aprovid;

    @Override
    protected void initViews(View view) {
        String reason = getIntent().getStringExtra("reason");
        aprovid = getIntent().getStringExtra("aprovid");
        tv_reason.setText(reason);
        tv_title.setText("审核状态");
        ll_left.setOnClickListener(this);
        tv_cun.setOnClickListener(this);
        tv_comit.setOnClickListener(this);
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
            case R.id.tv_cun:
                disFitment(aprovid);
                break;
            case R.id.tv_comit:
                confirmDecortApprove(aprovid);
                break;
        }
    }

    /**
     * 确认提交申请
     *
     * @param aprovid
     */
    private void confirmDecortApprove(String aprovid) {
        DialogTools.showLoding(this,"温馨提示","正在加载中。。。");
        HashMap<String, String> map = new HashMap<>();
        map.put("approveid", aprovid + "");
        RetrofitManager.getInstance(1, ChecKStatueActivity.this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .confirmDecortApprove(map)
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
                        String message = resultInfo.message;
                        DialogTools.dissmiss();
                        switch (resultInfo.code) {
                            case 0:
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("update", rxBusMsg);
                                finish();
                                ToastTools.showShort(ChecKStatueActivity.this, true, "提交成功！");
                                break;
                            case 1:
                                ToastTools.showShort(ChecKStatueActivity.this, false, message);
                                break;
                            case 3:
                                startActivity(new Intent(ChecKStatueActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    /**
     * 取消装修
     *
     * @param aprovid
     */
    private void disFitment(String aprovid) {
        DialogTools.showLoding(this,"温馨提示","正在加载中。。。");
        HashMap<String, String> map = new HashMap<>();
        map.put("approveid", aprovid + "");
        RetrofitManager.getInstance(1, ChecKStatueActivity.this, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .cancelDecortApprove(map)
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
                        String message = resultInfo.message;
                        DialogTools.dissmiss();
                        switch (resultInfo.code) {
                            case 0:
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("cg", rxBusMsg);
                                finish();
                                ToastTools.showShort(ChecKStatueActivity.this, true, "取消成功！");
                                break;
                            case 1:
                                ToastTools.showShort(ChecKStatueActivity.this, false, message);
                                break;
                            case 3:
                                startActivity(new Intent(ChecKStatueActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }
}
