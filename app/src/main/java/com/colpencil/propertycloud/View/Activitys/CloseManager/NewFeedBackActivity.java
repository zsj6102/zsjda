package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.Feed;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Present.Home.FeedBackPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Imples.FeedbackView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.yinghe.whiteboardlib.bean.EventBean;

import butterknife.Bind;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_new_feedback
)
public class NewFeedBackActivity extends ColpencilActivity implements View.OnClickListener, FeedbackView {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.ll_like)
    LinearLayout ll_like;
    @Bind(R.id.ll_normal)
    LinearLayout ll_normal;
    @Bind(R.id.ll_angren)
    LinearLayout ll_angren;
    @Bind(R.id.et_content)
    EditText et_content;
    @Bind(R.id.iv_like)
    ImageView iv_like;
    @Bind(R.id.iv_normal)
    ImageView iv_normal;
    @Bind(R.id.iv_angree)
    ImageView iv_angree;
    @Bind(R.id.tv_like)
    TextView tv_like;
    @Bind(R.id.tv_normal)
    TextView tv_normal;
    @Bind(R.id.tv_angree)
    TextView tv_angree;
    @Bind(R.id.btn_ok)
    Button btn_ok;

    private FeedBackPresent present;
    private String feedScore = "2"; //默认是好评

    @Override
    protected void initViews(View view) {
        tv_title.setText("意见反馈");
        ll_left.setOnClickListener(this);
        ll_like.setOnClickListener(this);
        ll_normal.setOnClickListener(this);
        ll_angren.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new FeedBackPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_left) {
            finish();
        } else if (id == R.id.ll_like) {
            iv_like.setImageDrawable(getResources().getDrawable(R.mipmap.satisfaction_press));
            iv_normal.setImageDrawable(getResources().getDrawable(R.mipmap.normal));
            iv_angree.setImageDrawable(getResources().getDrawable(R.mipmap.agre));
            tv_like.setTextColor(getResources().getColor(R.color.main_blue));
            tv_normal.setTextColor(getResources().getColor(R.color.main_gre));
            tv_angree.setTextColor(getResources().getColor(R.color.main_gre));
            feedScore = "2";
        } else if (id == R.id.ll_normal) {
            iv_like.setImageDrawable(getResources().getDrawable(R.mipmap.satisfaction));
            iv_normal.setImageDrawable(getResources().getDrawable(R.mipmap.general));
            iv_angree.setImageDrawable(getResources().getDrawable(R.mipmap.agre));
            tv_like.setTextColor(getResources().getColor(R.color.main_gre));
            tv_normal.setTextColor(getResources().getColor(R.color.text_orage));
            tv_angree.setTextColor(getResources().getColor(R.color.main_gre));
            feedScore = "1";
        } else if (id == R.id.ll_angren) {
            iv_like.setImageDrawable(getResources().getDrawable(R.mipmap.satisfaction));
            iv_normal.setImageDrawable(getResources().getDrawable(R.mipmap.normal));
            iv_angree.setImageDrawable(getResources().getDrawable(R.mipmap.agre_press));
            tv_like.setTextColor(getResources().getColor(R.color.main_gre));
            tv_normal.setTextColor(getResources().getColor(R.color.main_gre));
            tv_angree.setTextColor(getResources().getColor(R.color.main_red));
            feedScore = "0";
        } else if (id == R.id.btn_ok) {
            submit();
        }
    }

    private void submit() {
        DialogTools.showLoding(this, "温馨提示", "正在提交中。。。");
        present.submitFeed(getIntent().getStringExtra("orderid"),
                getIntent().getStringExtra("type"),
                feedScore,
                et_content.getText().toString());
    }

    @Override
    public void feed(ResultInfo resultInfo) {
        DialogTools.dissmiss();
        int code = resultInfo.code;
        if (code == 0) {
            EventBean eventBean = new EventBean();
            eventBean.setFlag(0);
            RxBus.get().post("feed", eventBean);
            finish();
            ToastTools.showShort(this, "提交成功！");
        } else if (code == 1) {
            ToastTools.showShort(this, resultInfo.message);
        } else if (code == 3) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public void getFeed(ResultListInfo<Feed> resultInfo) {
    }

}
