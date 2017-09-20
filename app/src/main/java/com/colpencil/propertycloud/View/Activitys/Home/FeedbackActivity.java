package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.yinghe.whiteboardlib.bean.EventBean;

import butterknife.Bind;

/**
 * @author 汪 亮
 * @Description: 意见反馈
 * @Email DramaScript@outlook.com
 * @date 2016/9/5
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_feedback
)
public class FeedbackActivity extends ColpencilActivity implements FeedbackView, View.OnClickListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_time)
    TextView tv_time;

    @Bind(R.id.tv_finishtime)
    TextView tv_finishtime;

    @Bind(R.id.tv_descrpition)
    TextView tv_descrpition;

    @Bind(R.id.ll_like)
    LinearLayout ll_like;

    @Bind(R.id.ll_normal)
    LinearLayout ll_normal;

    @Bind(R.id.ll_angren)
    LinearLayout ll_angren;

    @Bind(R.id.et_content)
    EditText et_content;

    @Bind(R.id.tv_ok)
    TextView tv_ok;

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

    @Bind(R.id.tv_employee)
    TextView tv_employee;

    @Bind(R.id.tv_property_opinion)
    TextView tv_property;

    private FeedBackPresent present;
    private int flag; // 0 投诉    1  报修
    private int isTouch; //  0 可以修改    1 不可以修改

    private int feedScore = -1;
    private String orderid;
    private String rateTime;

    @Override
    protected void initViews(View view) {
        flag = getIntent().getIntExtra("flag", 0);
        isTouch = getIntent().getIntExtra("isTouch", 0);
        tv_title.setText("意见反馈");
        ll_left.setOnClickListener(this);
        if (flag == 0) {
            String empnm = getIntent().getStringExtra("empnm");
            String booktime = getIntent().getStringExtra("booktime");
            String repitems = getIntent().getStringExtra("repitems");
            String description = getIntent().getStringExtra("description");
            String completm = getIntent().getStringExtra("completm");
            String property_opinion = getIntent().getStringExtra("property_opinion");
            orderid = getIntent().getStringExtra("orderid");
            rateTime = TimeUtil.getTimeStrFromMillis(System.currentTimeMillis());
            tv_finishtime.setText("完成时间：" + completm);
            tv_descrpition.setText("描述：" + description);
            tv_time.setText(booktime);
            tv_employee.setText("处理员工：" + empnm);
            tv_property.setText("物业意见：" + property_opinion);
            if (isTouch == 0) {
                ll_like.setOnClickListener(this);
                ll_normal.setOnClickListener(this);
                ll_angren.setOnClickListener(this);
                tv_ok.setOnClickListener(this);
                tv_ok.setVisibility(View.VISIBLE);
                et_content.setEnabled(true);
            } else {
                tv_ok.setVisibility(View.GONE);
                et_content.setEnabled(false);
                present.getFeed(orderid);
                DialogTools.showLoding(this, "温馨提示", "获取反馈意见中。。。");
            }
        } else {
            String empnm = getIntent().getStringExtra("empnm");
            String booktime = getIntent().getStringExtra("booktime");
            String repitems = getIntent().getStringExtra("repitems");
            String description = getIntent().getStringExtra("description");
            String completm = getIntent().getStringExtra("completm");
            String property_opinion = getIntent().getStringExtra("property_opinion");
            String detail_description = getIntent().getStringExtra("detail_description");
            orderid = getIntent().getStringExtra("orderid");
            rateTime = TimeUtil.getTimeStrFromMillis(System.currentTimeMillis());
            tv_finishtime.setText("完成时间：" + completm);
            tv_descrpition.setText("描述：" + description);
            tv_time.setText(booktime);
            tv_employee.setText("处理员工：" + empnm);
            tv_property.setText("物业意见：" + property_opinion);
            et_content.setText(detail_description);
//            tv_name.setText("处理员工：" + empnm);
//            tv_tpye.setText("报修项目：" + repitems);
//            tv_tou_time.setText("报修时间：" + booktime);
//            tv_descible.setText("描述：" + description);
            if (isTouch == 0) {
                ll_like.setOnClickListener(this);
                ll_normal.setOnClickListener(this);
                ll_angren.setOnClickListener(this);
                tv_ok.setOnClickListener(this);
                tv_ok.setVisibility(View.VISIBLE);
                et_content.setEnabled(true);
            } else {
                tv_ok.setVisibility(View.GONE);
                et_content.setEnabled(false);
                present.getFeed(orderid);
                DialogTools.showLoding(this, "温馨提示", "获取反馈意见中。。。");
            }
        }
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
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll_like:
                iv_like.setImageDrawable(getResources().getDrawable(R.mipmap.satisfaction_press));
                iv_normal.setImageDrawable(getResources().getDrawable(R.mipmap.normal));
                iv_angree.setImageDrawable(getResources().getDrawable(R.mipmap.agre));
                tv_like.setTextColor(getResources().getColor(R.color.main_blue));
                tv_normal.setTextColor(getResources().getColor(R.color.main_gre));
                tv_angree.setTextColor(getResources().getColor(R.color.main_gre));
                feedScore = 2;
                break;
            case R.id.ll_normal:
                iv_like.setImageDrawable(getResources().getDrawable(R.mipmap.satisfaction));
                iv_normal.setImageDrawable(getResources().getDrawable(R.mipmap.general));
                iv_angree.setImageDrawable(getResources().getDrawable(R.mipmap.agre));
                tv_like.setTextColor(getResources().getColor(R.color.main_gre));
                tv_normal.setTextColor(Color.parseColor("#ffaf40"));
                tv_angree.setTextColor(getResources().getColor(R.color.main_gre));
                feedScore = 1;
                break;
            case R.id.ll_angren:
                iv_like.setImageDrawable(getResources().getDrawable(R.mipmap.satisfaction));
                iv_normal.setImageDrawable(getResources().getDrawable(R.mipmap.normal));
                iv_angree.setImageDrawable(getResources().getDrawable(R.mipmap.agre_press));
                tv_like.setTextColor(getResources().getColor(R.color.main_gre));
                tv_normal.setTextColor(getResources().getColor(R.color.main_gre));
                tv_angree.setTextColor(getResources().getColor(R.color.main_red));
                feedScore = 0;
                break;
            case R.id.tv_ok:
                if (flag == 0) {
                    String detailDesc = et_content.getText().toString();
                    if (feedScore == -1) {
                        NotificationTools.show(this, "您还未评分");
                        return;
                    }
                    present.submitFeed(orderid, 1 + "", feedScore + "", detailDesc);
                    DialogTools.showLoding(this, "温馨提示", "正在提交中。。。");
                    ColpencilLogger.e("---------------------------orderid=" + orderid);
                } else {
                    String detailDesc = et_content.getText().toString();
                    if (feedScore == -1) {
                        NotificationTools.show(this, "您还未评分");
                        return;
                    }
                    present.submitFeed(orderid, 0 + "", feedScore + "", detailDesc);
                    DialogTools.showLoding(this, "温馨提示", "正在提交中。。。");
                }
                break;

        }
    }

    @Override
    public void feed(ResultInfo resultInfo) {
        DialogTools.dissmiss();
        int code = resultInfo.code;
        String message = resultInfo.message;
        switch (code) {
            case 0:
                EventBean eventBean = new EventBean();
                eventBean.setFlag(0);
                RxBus.get().post("feed", eventBean);
                finish();
                ToastTools.showShort(this, "提交成功！");
                break;
            case 1:
                NotificationTools.show(this, message);
                break;
            case 3:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    @Override
    public void getFeed(ResultListInfo<Feed> resultInfo) {
        DialogTools.dissmiss();
        int code = resultInfo.code;
        String message = resultInfo.message;
//        switch (code) {
//            case 0:
////                tv_name.setText("处理员工：" + resultInfo.data.empnm);
//                long feedscore = resultInfo.data.feedscore;
//                if (feedscore == 0) {
//                    iv_angree.setImageDrawable(getResources().getDrawable(R.mipmap.agre_press));
//                    tv_angree.setTextColor(getResources().getColor(R.color.main_red));
//                } else if (feedscore == 1) {
//                    iv_normal.setImageDrawable(getResources().getDrawable(R.mipmap.normal_press));
//                    tv_normal.setTextColor(getResources().getColor(R.color.main_red));
//                } else {
//                    iv_like.setImageDrawable(getResources().getDrawable(R.mipmap.smail_press));
//                    tv_like.setTextColor(getResources().getColor(R.color.main_red));
//                }
//                et_content.setText(resultInfo.data.detaildesc);
//                tv_time.setText("完成时间：" + resultInfo.data.completm);
//                break;
//            case 2:
//                NotificationTools.show(this, message);
//                break;
//            case 1:
//                NotificationTools.show(this, message);
//                break;
//            case 3:
//                startActivity(new Intent(this, LoginActivity.class));
//                break;
//        }
    }
}
