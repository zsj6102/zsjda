package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.yinghe.whiteboardlib.bean.EventBean;

import butterknife.Bind;

/**
 * @Description:  性别选择
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/11/30
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_sex
)
public class SelectSexActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.rl_mam)
    RelativeLayout rl_mam;

    @Bind(R.id.rl_women)
    RelativeLayout rl_women;

    @Bind(R.id.iv_man)
    ImageView iv_man;

    @Bind(R.id.iv_women)
    ImageView iv_women;
    private String sex;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Override
    protected void initViews(View view) {
        tv_title.setText("性别");
        sex = getIntent().getStringExtra("sex");
        rl_mam.setOnClickListener(this);
        rl_women.setOnClickListener(this);
        if ("男".equals(sex)){
            iv_man.setImageDrawable(getResources().getDrawable(R.mipmap.fit_check));
            iv_women.setImageDrawable(getResources().getDrawable(R.mipmap.fit_uncheck));
        }else {
            iv_man.setImageDrawable(getResources().getDrawable(R.mipmap.fit_uncheck));
            iv_women.setImageDrawable(getResources().getDrawable(R.mipmap.fit_check));
        }

        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
            case R.id.rl_mam:
                iv_man.setImageDrawable(getResources().getDrawable(R.mipmap.fit_check));
                iv_women.setImageDrawable(getResources().getDrawable(R.mipmap.fit_uncheck));
                EventBean eventBean = new EventBean();
                eventBean.setFlag(1);
                RxBus.get().post("change",eventBean);
                finish();
                break;
            case R.id.rl_women:
                iv_man.setImageDrawable(getResources().getDrawable(R.mipmap.fit_uncheck));
                iv_women.setImageDrawable(getResources().getDrawable(R.mipmap.fit_check));
                EventBean eventBean2 = new EventBean();
                eventBean2.setFlag(2);
                RxBus.get().post("change",eventBean2);
                finish();
                break;
        }
    }
}
