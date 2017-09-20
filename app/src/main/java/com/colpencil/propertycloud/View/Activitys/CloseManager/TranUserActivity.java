package com.colpencil.propertycloud.View.Activitys.CloseManager;

/**
 * Created by xiaohuihui on 2017/1/9.
 */

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.ApiCloudActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.HashMap;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:
 * @Email DramaScript@outlook.com
 * @date 2017/1/9
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_transfer_username
)
public class TranUserActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_rigth)
    TextView tv_rigth;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.ll_rigth)
    RelativeLayout ll_right;
    @Bind(R.id.tv_next)
    TextView tv_next;
    @Bind(R.id.et_acount)
    EditText et_acount;

    @Override
    protected void initViews(View view) {
        tv_title.setText("转到大爱齐家账号");
        tv_rigth.setText("使用规则");
        tv_rigth.setVisibility(View.VISIBLE);
        ll_left.setOnClickListener(this);
        ll_right.setOnClickListener(this);
        tv_next.setOnClickListener(this);
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
        int id = v.getId();
        if (id == R.id.ll_left) {
            finish();
        } else if (id == R.id.ll_rigth) {
            Intent intent = new Intent(this, ApiCloudActivity.class);
            intent.putExtra("startUrl", getIntent().getStringExtra("url"));
            startActivity(intent);
        } else if (id == R.id.tv_next) {
            if (TextUtils.isEmpty(et_acount.getText().toString())) {
                ToastTools.showShort(this, "要转让的账号不能为空");
            } else {
                checkUser();
            }
        }
    }

    private void checkUser() {
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", et_acount.getText().toString());
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .checkUserExist(params)
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
                    public void onNext(ResultInfo resultInfo) {
                        if (resultInfo.code == 0) {
                            Intent intent = new Intent(TranUserActivity.this, TransferActivity.class);
                            intent.putExtra("amount", et_acount.getText().toString());
                            intent.putExtra("url", getIntent().getStringExtra("url"));
                            intent.putExtra("id", getIntent().getIntExtra("id", 0));
                            startActivity(intent);
                        } else {
                            ToastTools.showShort(TranUserActivity.this, resultInfo.message);
                        }
                    }
                });
    }
}
