package com.colpencil.tenement.View.Activitys.Workbeach;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.Present.Home.SetingPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Tools.VersionUtils;
import com.colpencil.tenement.View.Activitys.Welcome.ForgetPwdActivity;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Imples.SetView;
import com.easemob.chat.EMChatManager;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DataCleanManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;
import java.util.Set;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * @Description: 设置界面
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/29
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_setting
)
public class SettingActivity extends ColpencilActivity implements View.OnClickListener,SetView{

    @Bind(R.id.ll_changePwd)
    LinearLayout ll_changePwd;

    @Bind(R.id.ll_loginOut)
    LinearLayout ll_loginOut;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.ll_yao)
    LinearLayout ll_yao;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.layout_clear)
    RelativeLayout layout_clear;

    @Bind(R.id.text_cache)
    TextView tv_cache;

    @Bind(R.id.text_version)
    TextView tv_version;

    private SetingPresent present;

    private String communityId;
    private Intent intent;

    @Override
    protected void initViews(View view) {
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        communityId = SharedPreferencesUtil.getInstance(this).getString(StringConfig.PLOTID);
        tv_title.setText("设置");
        ll_loginOut.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        ll_changePwd.setOnClickListener(this);
        ll_yao.setOnClickListener(this);
        layout_clear.setOnClickListener(this);
        tv_version.setText("V"+VersionUtils.getVersionName(this));
        try {
            tv_cache.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new SetingPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_loginOut:
                present.loginOut(communityId);
                break;
            case R.id.ll_changePwd:
                intent = new Intent(this, ForgetPwdActivity.class);
                intent.putExtra("flag",2);
                startActivity(intent);
                break;
            case R.id.ll_yao:
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", Urlconfig.BASEURL_PIC+"/wuye/property/opendoor/key_management.do");
                intent.putExtra("title","钥匙管理");
                startActivity(intent);
                break;
            case R.id.layout_clear:
                DataCleanManager.clearAllCache(this);
                try {
                    tv_cache.setText(DataCleanManager.getTotalCacheSize(this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void loginOut(EntityResult<String> result) {
        int code = result.getCode();
        String message = result.getMessage();
        switch (code){
            case 0:
                mAm.finishAllActivity();
                SharedPreferencesUtil.getInstance(this).setString("passWord","");
                SharedPreferencesUtil.getInstance(this).setString("talkname","");
                // 把之前设置的 别名置空
                JPushInterface.setAlias(this, "", new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {

                    }
                });
                EMChatManager.getInstance().logout();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case 1:
                ToastTools.showShort(this,false,message);
                break;
        }
    }

    @Override
    public void loadError(String msg) {
        ToastTools.showShort(this,false,"请求失败！");
    }

}
