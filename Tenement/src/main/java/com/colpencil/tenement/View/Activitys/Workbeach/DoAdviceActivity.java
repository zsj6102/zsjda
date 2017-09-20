package com.colpencil.tenement.View.Activitys.Workbeach;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.Result;
import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
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
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 处理意见
 * @Email DramaScript@outlook.com
 * @date 2016/10/25
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_do_advice
)
public class DoAdviceActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.et_advice)
    EditText et_advice;

    @Bind(R.id.btn_save)
    Button btn_save;
    private String orderId;
    private int type;
    private String repairDesc;
    private int flag; // 0 表示物业报修   1 表示投诉建议

    @Override
    protected void initViews(View view) {
        orderId = getIntent().getStringExtra("orderId");
        repairDesc = getIntent().getStringExtra("repairDesc");
        type = getIntent().getIntExtra("type", 0);
        flag = getIntent().getIntExtra("flag", 0);
        tv_title.setText("处理意见");
        ll_left.setOnClickListener(this);

        if (type == 3) {
            btn_save.setOnClickListener(this);
            btn_save.setVisibility(View.VISIBLE);
        } else {
            if (TextUtils.isEmpty(repairDesc)) {
                et_advice.setText("暂无处理意见");
            } else {
                et_advice.setText(repairDesc);
            }
            et_advice.setEnabled(false);
            btn_save.setVisibility(View.INVISIBLE);
        }

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
            case R.id.btn_save:
                String repairDesc = et_advice.getText().toString();
                if (TextUtils.isEmpty(repairDesc)) {
                    ToastTools.showShort(DoAdviceActivity.this, false, "您还未填写处理意见！");
                    return;
                }
                if (flag == 0) {
                    stateChange(type + "", orderId, repairDesc);
                } else {
                    adviceChange(type + "", orderId, repairDesc);
                }

                break;
        }
    }

    /**
     * 物业报修完成
     *
     * @param type
     * @param orderId
     * @param repairDesc
     */
    private void stateChange(String type, String orderId, String repairDesc) {
        DialogTools.showLoding(this, "加载", "正在提交中。。。");
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("orderId", orderId);
        params.put("repairDesc", repairDesc);
        Observable<Result> resultObservable = RetrofitManager.getInstance(1, this, Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .changeState(params)
                .map(new Func1<Result, Result>() {
                    @Override
                    public Result call(Result result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscriber<Result> subscriber = new Subscriber<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误：" + e.getMessage());
                DialogTools.dissmiss();
                ToastTools.showShort(DoAdviceActivity.this,false,"请求失败！");
            }

            @Override
            public void onNext(Result result) {
                int code = result.code;
                String message = result.message;
                switch (code) {
                    case 0:
                        RxBusMsg rxBusMsg = new RxBusMsg();
                        rxBusMsg.setType(1);
                        RxBus.get().post("stateChange", rxBusMsg);
                        finish();
                        ToastTools.showShort(DoAdviceActivity.this, true, message);
                        break;
                    case 1:
                        ToastTools.showShort(DoAdviceActivity.this, false, message);
                        break;
                    case 3:
                        startActivity(new Intent(DoAdviceActivity.this, LoginActivity.class));
                        break;
                }
                DialogTools.dissmiss();
            }
        };
        resultObservable.subscribe(subscriber);
    }


    /**
     * 投诉建议完成
     *
     * @param type
     * @param orderId
     */
    private void adviceChange(String type, String orderId, String repairDesc) {
        DialogTools.showLoding(this, "加载", "正在提交中。。。");
        HashMap<String, String> params = new HashMap<>();
        params.put("status", type + "");
        params.put("orderId", orderId);
        params.put("serviceDesc", repairDesc);
        Observable<Result> resultObservable = RetrofitManager.getInstance(1, this, Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .changeComplaintState(params)
                .map(new Func1<Result, Result>() {
                    @Override
                    public Result call(Result result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscriber<Result> subscriber = new Subscriber<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误：" + e.getMessage());
                DialogTools.dissmiss();
                ToastTools.showShort(DoAdviceActivity.this,false,"请求失败！");
            }

            @Override
            public void onNext(Result result) {
                int code = result.code;
                String message = result.message;
                switch (code) {
                    case 0:
                        RxBusMsg rxBusMsg = new RxBusMsg();
                        rxBusMsg.setType(1);
                        RxBus.get().post("stateChange", rxBusMsg);
                        ToastTools.showShort(DoAdviceActivity.this, true, message);
                        finish();
                        break;
                    case 1:
                        ToastTools.showShort(DoAdviceActivity.this, false, message);
                        break;
                    case 3:
                        startActivity(new Intent(DoAdviceActivity.this, LoginActivity.class));
                        break;
                }
                DialogTools.dissmiss();
            }
        };
        resultObservable.subscribe(subscriber);
    }
}
