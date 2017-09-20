package com.colpencil.propertycloud.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.CloseManager.MineActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.MyOrderActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.TenementRepairsActivity;
import com.colpencil.propertycloud.View.Activitys.Home.ComplaintActivity;
import com.colpencil.propertycloud.View.Activitys.Home.MyFitmentActivity;
import com.colpencil.propertycloud.View.Activitys.Home.PayFeesActivity;
import com.colpencil.propertycloud.View.Activitys.Home.VilageSelectActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.ChangePwdActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import butterknife.Bind;


/**
 * @Description:
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/7/25
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_4
)
public class Fragment4 extends ColpencilFragment implements View.OnClickListener {


    @Bind(R.id.button1)
    Button button1;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.button3)
    Button button3;
    @Bind(R.id.button4)
    Button button4;
    @Bind(R.id.button5)
    Button button5;
    @Bind(R.id.button6)
    Button button6;
    @Bind(R.id.button7)
    Button button7;
    @Bind(R.id.button8)
    Button button8;
    @Bind(R.id.button9)
    Button button9;
    @Bind(R.id.button11)
    Button button11;
    private Intent intent;

    @Override
    protected void initViews(View view) {
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button11.setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.button1:  // 选择小区
                intent = new Intent(getActivity(), VilageSelectActivity.class);
                intent.putExtra("flag",2);
                startActivity(intent);
                break;
            case R.id.button2: //  物业投诉
                intent = new Intent(getActivity(), ComplaintActivity.class);
                intent.putExtra("flag",0);
                startActivity(intent);
                break;
            case R.id.button3: //  我要装修
                intent = new Intent(getActivity(), MyFitmentActivity.class);
                startActivity(intent);
                break;
            case R.id.button4: // 物业新闻

                break;
            case R.id.button5: // 物业缴费
                intent = new Intent(getActivity(), PayFeesActivity.class);
                startActivity(intent);
                break;
            case R.id.button6: // 我的订单
                intent = new Intent(getActivity(), MyOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.button7:// 物业报修
                intent = new Intent(getActivity(), TenementRepairsActivity.class);
                startActivity(intent);
                break;
            case R.id.button8:// 我的资料
                intent = new Intent(getActivity(), MineActivity.class);
                startActivity(intent);
                break;
            case R.id.button9: // 物业表扬
                intent = new Intent(getActivity(), ComplaintActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
                break;
            case R.id.button11:// 修改密码
                intent = new Intent(getActivity(), ChangePwdActivity.class);
                intent.putExtra("flag",2);
                startActivity(intent);
                break;
        }
    }

}
