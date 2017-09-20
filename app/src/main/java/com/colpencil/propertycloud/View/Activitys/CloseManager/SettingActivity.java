package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.VersionUtils;
import com.colpencil.propertycloud.View.Activitys.Welcome.ChangePwdActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DataCleanManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 设置界面
 * @Email DramaScript@outlook.com
 * @date 2016/11/18
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_setting
)
public class SettingActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.rl_change_pwd)
    RelativeLayout rl_change_pwd;

    @Bind(R.id.rl_clear)
    RelativeLayout rl_clear;

    @Bind(R.id.tv_cache)
    TextView tv_cache;

    @Bind(R.id.rl_version)
    RelativeLayout rl_version;

    @Bind(R.id.tv_check)
    TextView tv_check;

    @Bind(R.id.tv_version)
    TextView tv_version;

    @Bind(R.id.btn_login_out)
    Button btn_login_out;
    private Intent intent;

    @Override
    protected void initViews(View view) {
        tv_version.setText("当前版本："+VersionUtils.getVersionName(this));
        tv_title.setText("设置");
        ll_left.setOnClickListener(this);
        rl_change_pwd.setOnClickListener(this);
        rl_clear.setOnClickListener(this);
        rl_version.setOnClickListener(this);
        btn_login_out.setOnClickListener(this);
        try {
            tv_cache.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
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
            case R.id.rl_change_pwd:
                intent = new Intent(this, ChangePwdActivity.class);
                intent.putExtra("flag", 2);
                startActivity(intent);
                break;
            case R.id.rl_clear:
                DataCleanManager.clearAllCache(this);
                try {
                    tv_cache.setText(DataCleanManager.getTotalCacheSize(this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_version:
                checkVersion();
                break;
            case R.id.btn_login_out:
                loginOut();
                break;
        }
    }

    /**
     * 退出登录
     */
    private void loginOut() {
        String comuId = SharedPreferencesUtil.getInstance(this).getString("comuid");
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .loginOut(comuId)
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
                        ColpencilLogger.e("服务器错误信息" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo resultInfo) {
                        int code = resultInfo.code;
                        String message = resultInfo.message;
                        if (code == 0) {
                            // TODO: 2016/9/13 退出成功
                            MobclickAgent.onProfileSignOff();
                            SharedPreferencesUtil.getInstance(SettingActivity.this).setString("mobile", "");
                            SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).setString("passWord", "");
                            SharedPreferencesUtil.getInstance(SettingActivity.this).setBoolean("isLogin", false);
                            SharedPreferencesUtil.getInstance(SettingActivity.this).setBoolean("isProprietor", false);
                            ColpencilFrame.getInstance().finishAllActivity();
                            startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                        } else {
                            ToastTools.showShort(SettingActivity.this, false, message);
                        }
                    }
                });
    }

    /**
     * 检查更新
     */
    private void checkVersion() {

    }


}
