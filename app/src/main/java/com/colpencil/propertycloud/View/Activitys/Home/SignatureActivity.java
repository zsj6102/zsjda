package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.yinghe.whiteboardlib.bean.EventBean;
import com.yinghe.whiteboardlib.fragment.WhiteBoardFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;

/**
 * @Description: 签字界面
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/8
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_signature
)
public class SignatureActivity extends ColpencilActivity implements View.OnClickListener {

    WhiteBoardFragment whiteBoardFragment;

    @Bind(R.id.tv_clearAll)
    TextView tv_clearAll;

    @Bind(R.id.tv_che)
    TextView tv_che;

    @Bind(R.id.tv_save)
    TextView tv_save;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;
    private static int type;
    private int flag;

    @Override
    protected void initViews(View view) {
        type = getIntent().getIntExtra("type", 0);
        flag = getIntent().getIntExtra("flag", 0);
        EventBus.getDefault().register(this);
        tv_title.setText("签字板");
        tv_rigth.setVisibility(View.VISIBLE);
        tv_rigth.setText("完成");
        FragmentTransaction ts = getSupportFragmentManager().beginTransaction();
        whiteBoardFragment = WhiteBoardFragment.newInstance();
        ts.add(R.id.fl_main,whiteBoardFragment, "wb").commit();
        ll_left.setOnClickListener(this);
        tv_clearAll.setOnClickListener(this);
        tv_che.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        ll_rigth.setOnClickListener(this);

        ColpencilLogger.e("type="+type+"flag="+flag);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
//        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }
    }

    @Subscribe
    public void onEvent(EventBean event) {
        if (event.getFlag()==1){
            if (type==0){
                RxBusMsg msg = new RxBusMsg();
                msg.setType(0);
                msg.setMsg(event.getImagePath());
                RxBus.get().post("MaterialManagement",msg);
                if (flag==0){
                    mAm.finishActivity(ReadActivity.class);
                }
                finish();
                ColpencilLogger.e("-------------------------1");
            }else {

                if (type==1){
                    SharedPreferencesUtil.getInstance(this).setBoolean("once",true);
                    SharedPreferencesUtil.getInstance(this).setString("imagePath",event.getImagePath());
                    finish();
                    ColpencilLogger.e("-------------------------2");
                }else {
                    RxBusMsg msg = new RxBusMsg();
                    msg.setType(1);
                    msg.setMsg(event.getImagePath());
                    RxBus.get().post("MaterialManagement",msg);
                    if (flag==0){
                        mAm.finishActivity(ReadActivity.class);
                    }
                    finish();
                    ColpencilLogger.e("-------------------------3");
                }
            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.tv_clearAll:
                //清空
                whiteBoardFragment.clearAll();
                break;
            case R.id.tv_che:
                //撤销
                whiteBoardFragment.clear();
                break;
            case R.id.ll_rigth:
                //完成
                whiteBoardFragment.save();
                break;
        }
    }
}
