package com.colpencil.tenement.View.Activitys.Welcome;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.colpencil.tenement.Overall.TenementApplication;
import com.colpencil.tenement.Present.Welcome.RegistPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Tools.XmppTools;
import com.colpencil.tenement.View.Activitys.HomeActivity;
import com.colpencil.tenement.View.Imples.RegistView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import butterknife.Bind;

/**
 * @Description: 注册界面
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/7/17
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_regist
)
public class RegistActivity extends ColpencilActivity implements RegistView {

    @Bind(R.id.et_acount)
    BiuEditText et_acount;

    @Bind(R.id.et_passWord)
    BiuEditText et_passWord;

    @Bind(R.id.btn_regist)
    Button btn_regist;

    private String acount;
    private String passWord;
    private RegistPresent registPresent;

    @Override
    protected void initViews(View view) {
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acount = et_acount.getText().toString().trim();
                passWord = et_passWord.getText().toString().trim();
                if (TextUtils.isEmpty(acount)&&TextUtils.isEmpty(passWord)){
                    ToastTools.showShort(RegistActivity.this,false,"用户名或者密码不能为空！");
                    return;
                }else {
                    ColpencilLogger.e("------------------------1");
                    registPresent.regist(acount,passWord);
                    ColpencilLogger.e("------------------------2");
                }
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        registPresent = new RegistPresent();
        return registPresent;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void regist(int isRegist) {
        ColpencilLogger.e("------------------------8="+isRegist);
        switch (isRegist){
            case 1:
                ToastTools.showShort(RegistActivity.this,true,"注册成功！");
             /*   TenementApplication.getInstance().execRunnable(new Runnable() {
                    @Override
                    public void run() {
                        final boolean login = XmppTools.getInstance().login(acount, passWord);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (login){
                                        ToastTools.showShort(RegistActivity.this,true,"登陆成功！");
                                    }else {
                                        ToastTools.showShort(RegistActivity.this,false,"登陆失败！");
                                    }
                                    startActivity(new Intent(RegistActivity.this, HomeActivity.class));
                                    finish();
                                }
                            });
                    }
                });*/
                break;
            case 2:
                Toast.makeText(this,"用户已存在",Toast.LENGTH_SHORT).show();
                break;
            case 0:
                Toast.makeText(this,"服务器无响应",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
