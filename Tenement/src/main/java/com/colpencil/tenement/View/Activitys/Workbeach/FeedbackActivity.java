package com.colpencil.tenement.View.Activitys.Workbeach;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Feedback;
import com.colpencil.tenement.Present.Home.FeedbackPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Imples.FeedbackView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import butterknife.Bind;

/**
 * @author 汪 亮
 * @Description: 查看反馈
 * @Email DramaScript@outlook.com
 * @date 2016/9/30
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_feedback
)
public class FeedbackActivity extends ColpencilActivity implements FeedbackView {

    @Bind(R.id.feedback_create_time)
    TextView tv_createTime;
    @Bind(R.id.feedback_description)
    TextView tv_description;
    @Bind(R.id.btn_praise)
    Button btn_praise;
    @Bind(R.id.btn_middle)
    Button btn_middle;
    @Bind(R.id.btn_bad)
    Button btn_bad;

    @Bind(R.id.tv_id)
    TextView tv_id;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_phone)
    TextView tv_phone;
    @Bind(R.id.tv_adrr)
    TextView tv_adrr;

    private FeedbackPresent present;

    @Override
    protected void initViews(View view) {
        int ownerRecordId = getIntent().getIntExtra("ownerRecordId",0);
        String ownerName = getIntent().getStringExtra("ownerName");
        String ownerPhone = getIntent().getStringExtra("ownerPhone");
        String address = getIntent().getStringExtra("address");
        int type = getIntent().getIntExtra("type", 0);

        tv_id.setText(ownerRecordId+"");
        tv_adrr.setText(address);
        tv_name.setText(ownerName);
        tv_phone.setText(ownerPhone);

        present.seeFeedback(ownerRecordId, type);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new FeedbackPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void feekBackResult(EntityResult<Feedback> result) {
        if (result.getCode() == 0) {  //成功
            Feedback feedback = result.getData();
            tv_createTime.setText("意见发布时间:" + feedback.getRateTime());
            tv_description.setText(feedback.getDescrpition());
            if (feedback.getRating() == 0) {    //差评
                btn_bad.setBackgroundResource(R.drawable.rect_green_long);
                btn_praise.setBackgroundResource(R.drawable.rect_gre_long);
                btn_middle.setBackgroundResource(R.drawable.rect_gre_long);
            } else if (feedback.getRating() == 1) {     //一般
                btn_middle.setBackgroundResource(R.drawable.rect_green_long);
                btn_bad.setBackgroundResource(R.drawable.rect_gre_long);
                btn_praise.setBackgroundResource(R.drawable.rect_gre_long);
            } else {       //好评
                btn_praise.setBackgroundResource(R.drawable.rect_green_long);
                btn_middle.setBackgroundResource(R.drawable.rect_gre_long);
                btn_bad.setBackgroundResource(R.drawable.rect_gre_long);
            }
        }
    }

    @Override
    public void loadError(String msg) {
        ToastTools.showShort(this,false,"请求失败！");
    }
}
