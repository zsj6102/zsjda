package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Present.CloseManager.ChangeInfoPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Imples.ChangeInfoView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.yinghe.whiteboardlib.bean.EventBean;

import butterknife.Bind;

/**
 * @Description: 修改信息界面
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/10/11
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_change_info
)
public class ChangeMineInfoActivity extends ColpencilActivity implements ChangeInfoView{

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.et_change)
    EditText et_change;

    private ChangeInfoPresent present;
    private int flag;
    private String content;


    @Override
    protected void initViews(View view) {
        flag = getIntent().getIntExtra("flag", 0);
        content = getIntent().getStringExtra("content");
        tv_rigth.setVisibility(View.VISIBLE);
        tv_rigth.setText("完成");
        switch (flag){
            case 0:  // 名称
                tv_title.setText("姓名");
                break;
            case 1: //  昵称
                tv_title.setText("昵称");
                break;
            case 2: //  年龄
                tv_title.setText("年龄");
                break;
            case 3://  性别
                tv_title.setText("性别");
                break;
            case 4://  爱好
                tv_title.setText("爱好");
                break;
            case 5://  工作单位
                tv_title.setText("工作单位");
                break;
            case 6://  民族
                tv_title.setText("民族");
                break;
            case 7://  紧急电话
                tv_title.setText("紧急电话");
                et_change.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case 8://  通讯地址
                tv_title.setText("通讯地址");
                break;
            case 9://  邮箱
                tv_title.setText("邮箱");
                break;
            case 10://  常住地址
                tv_title.setText("常住地址");
                break;
        }
        et_change.setText(content);
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ll_rigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String changeInfo = et_change.getText().toString();
                if (flag==7){  // 判断电话
                    if (!TextStringUtils.isMobileNO(changeInfo)){
                        ToastTools.showShort(ChangeMineInfoActivity.this,"您输入的手机号不合法！");
                        return;
                    }
                }else if (flag==9){ // 判断邮箱
                    if (!TextStringUtils.isEmail(changeInfo)){
                        ToastTools.showShort(ChangeMineInfoActivity.this,"您输入的邮箱不合法！");
                        return;
                    }
                }else if (flag==1){// 昵称
                    if (changeInfo.length()>12){
                        ToastTools.showShort(ChangeMineInfoActivity.this,"昵称不能大于12个字符！");
                        return;
                    }
                }else if (flag ==0){
                    if (changeInfo.length()>6){
                        ToastTools.showShort(ChangeMineInfoActivity.this,"名字不能大于6个字符！");
                        return;
                    }
                }
                present.change(flag,changeInfo);
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new ChangeInfoPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void isChange(ResultInfo resultInfo) {
        int code = resultInfo.code;
        String message = resultInfo.message;
        switch (code){
            case 0:
                EventBean eventBean = new EventBean();
                eventBean.setFlag(0);
                RxBus.get().post("change",eventBean);
                finish();
                ToastTools.showShort(this,"修改成功！");
                break;
            case 1:
                NotificationTools.show(this,message);
                break;
            case 3:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }
}
