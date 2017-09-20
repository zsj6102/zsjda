package com.colpencil.tenement.View.Activitys.Workbeach;

import android.os.Bundle;
import android.view.View;

import com.colpencil.tenement.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

/**
 * @Description: 我的位置
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/19
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_myloaction
)
public class MyLoactionActivity extends ColpencilActivity {

    @Override
    protected void initViews(View view) {
        setStatecolor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
}
