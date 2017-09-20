package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.AuthCode;
import com.colpencil.propertycloud.Present.CloseManager.CodePresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Imples.AuthCodeView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.StringUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.yinghe.whiteboardlib.bean.EventBean;

import butterknife.Bind;

/**
 * @Description: 修改联系电话
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/9
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_phone
)
public class ChangePhoneActivity extends ColpencilActivity implements View.OnClickListener,AuthCodeView {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.et_phone)
    EditText et_phone;

    @Bind(R.id.et_yan)
    EditText et_yan;

    @Bind(R.id.tv_get)
    TextView tv_get;

    @Bind(R.id.tv_ok)
    TextView tv_ok;
    private String phone;
    private CodePresent present;

    @Override
    protected void initViews(View view) {
        tv_title.setText("修改联系电话");
        ll_left.setOnClickListener(this);
        tv_get.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new CodePresent();
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
            case R.id.tv_get:
                // TODO: 2016/9/9 获取验证码
                phone = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    NotificationTools.show(this,"您未填写手机号码");
                    return;
                }else {
                    if (TextStringUtils.isMobileNO(phone)==false){
                        NotificationTools.show(this,"您未填写手机号码不合法");
                        return;
                    }else {
                        present.getCode(phone);
                    }

                }
                break;
            case R.id.tv_ok:
                phone = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    ToastTools.showLong(this,"您未填写手机号码");
                    return;
                }else {
                    EventBean eventBean = new EventBean();
                    eventBean.setPhone(phone);
                    eventBean.setFlag(1);
                    RxBus.get().post("event",eventBean);
                    finish();
                }

                break;
        }
    }

    @Override
    public void getCode(AuthCode authCode) {

    }
}
