package com.colpencil.tenement.View.Activitys.OnlineTalk;

import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.tenement.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.skyfishjy.library.RippleBackground;

import butterknife.Bind;

/**
 * @Description: 通话界面
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/28
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_online
)
public class OnlineTalkActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_time)
    Chronometer tv_time;

    @Bind(R.id.iv_unsay)
    ImageView iv_unsay;

    @Bind(R.id.iv_loudly)
    ImageView iv_loudly;

    @Bind(R.id.iv_uncall)
    ImageView iv_uncall;

    @Bind(R.id.iv_lisent)
    ImageView iv_lisent;

    @Bind(R.id.iv_statue)
    ImageView iv_statue;

    @Bind(R.id.tv_statue)
    TextView tv_statue;

    @Bind(R.id.content)
    RippleBackground content;

    private boolean isMuteState;
    private boolean isHandsfreeState;

    private String name;
    private int type; // 0 别人拨打进来  1 是打过去

    @Override
    protected void initViews(View view) {
        name = getIntent().getStringExtra("name");
        type = getIntent().getIntExtra("type", 0);
        tv_title.setText(name);
        content.startRippleAnimation();
        ll_left.setOnClickListener(this);
        iv_unsay.setOnClickListener(this);
        iv_loudly.setOnClickListener(this);
        iv_uncall.setOnClickListener(this);
        iv_lisent.setOnClickListener(this);
        setStatecolor(getResources().getColor(R.color.colorPrimary));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tv_time.stop();
        content.stopRippleAnimation();
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
            case R.id.ll_left:
                finish();
                break;
            case R.id.iv_unsay:  // 静音
                if (isMuteState) {
//                    iv_unsay.setImageResource(R.mipmap.ji);
                    isMuteState = false;
                } else {
//                    iv_unsay.setImageResource(R.mipmap.em_icon_mute_on);
                    isMuteState = true;
                }
                break;
            case R.id.iv_loudly://  免提

                break;
            case R.id.iv_lisent: //  接听

                break;
            case R.id.iv_uncall://  挂断
                tv_time.stop();
                content.stopRippleAnimation();
                break;

        }
    }
}
