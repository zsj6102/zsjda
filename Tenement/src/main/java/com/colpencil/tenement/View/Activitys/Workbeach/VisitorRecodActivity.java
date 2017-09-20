package com.colpencil.tenement.View.Activitys.Workbeach;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.tenement.Present.Home.VisitorRecodPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Imples.VisitorRecordView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import butterknife.Bind;

/**
 * @Description: 登记访客
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/26
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_visitor_login
)
public class VisitorRecodActivity extends ColpencilActivity implements VisitorRecordView{


    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.iv_recod)
    ImageView iv_recod;

    private VisitorRecodPresent present;

    @Override
    protected void initViews(View view) {
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        tv_title.setText("访客登记");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new VisitorRecodPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        if (mPresenter==null)
            return;
        ((VisitorRecodPresent)mPresenter).getImgeUrl();
    }

    @Override
    public void getReacod(String image) {

    }
}
