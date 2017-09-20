package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.Transfer;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.KeyBoard.KeyboardUtil;
import com.colpencil.propertycloud.Tools.KeyBoard.MyKeyBoardView;
import com.colpencil.propertycloud.Ui.PasswordView;
import com.colpencil.propertycloud.View.Activitys.ApiCloudActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.HashMap;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xiaohuihui on 2017/1/9.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_transfer
)
public class TransferActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_rigth)
    TextView tv_rigth;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.ll_rigth)
    RelativeLayout ll_right;
    @Bind(R.id.tv_tran)
    TextView tv_tran;
    @Bind(R.id.iv_user)
    CircleImageView iv_header;

    private DialogPlus dialogPlus;
    private String amount;

    @Override
    protected void initViews(View view) {
        tv_title.setText("转到大爱齐家账号");
        tv_rigth.setText("转让规则");
        tv_rigth.setVisibility(View.VISIBLE);
        ll_left.setOnClickListener(this);
        ll_right.setOnClickListener(this);
        tv_tran.setOnClickListener(this);
        amount = getIntent().getStringExtra("amount");
        loadInfo();
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
        } else if (id == R.id.tv_tran) {
            showDialog();
        }
    }

    private void showDialog() {
        if (dialogPlus == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.popup_pay, null);
            dialogPlus = DialogPlus.newDialog(this)
                    .setContentHolder(new ViewHolder(view))
                    .setCancelable(false)
                    .setGravity(Gravity.BOTTOM)
                    .setExpanded(false)
                    .create();
            view.findViewById(R.id.rl_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogPlus.dismiss();
                }
            });
            TextView tv_forget = (TextView) view.findViewById(R.id.tv_forget);
            tv_forget.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            final PasswordView passwordView = (PasswordView) view.findViewById(R.id.password_view);
            final MyKeyBoardView keyBoardView = (MyKeyBoardView) view.findViewById(R.id.keyboard_view);
            final KeyboardUtil util = new KeyboardUtil(this, true, keyBoardView);
            passwordView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    passwordView.setFocusable(true);
                    passwordView.requestFocus();
                    util.attachTo(passwordView);
                }
            });
            util.setOnOkClick(new KeyboardUtil.OnOkClick() {
                @Override
                public void onOkClick() {
                    if (passwordView.mText.toString().length() < 6) {
                        ToastTools.showShort(TransferActivity.this, "请输入完成的支付密码");
                    } else {
                        transfer(passwordView.mText.toString());
                    }
                }
            });
            tv_forget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TransferActivity.this, ForgetPayActivity.class);
                    startActivity(intent);
                }
            });
        }
        dialogPlus.show();
    }

    private void transfer(String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("recipient", amount);
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .transfer(params)
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

                        }
                    }
                });
    }

    private void loadInfo() {
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", amount);
        params.put("id", getIntent().getIntExtra("id", 0) + "");
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loadOtherInfo(params)
                .map(new Func1<ResultInfo<Transfer>, ResultInfo<Transfer>>() {
                    @Override
                    public ResultInfo call(ResultInfo<Transfer> resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<Transfer>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultInfo<Transfer> resultInfo) {
                        if (resultInfo.code == 0) {
                            ImageLoader.getInstance().displayImage(resultInfo.data.memberInfo.face, iv_header);

                        }
                    }
                });
    }
}
