package com.colpencil.propertycloud.View.Activitys.Test;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.colpencil.propertycloud.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Permission.OnPermissionCallback;
import com.property.colpencil.colpencilandroidlibrary.Function.Permission.PermissionManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import butterknife.Bind;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_test
)
public class SecondActivity extends ColpencilActivity implements OnPermissionCallback {

    @Bind(R.id.tv)
    TextView textView;

    @Override
    protected void initViews(View view) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(SecondActivity.this,TestMvpActivity.class));
//                PermissionManager.getInstance().requestPermission(SecondActivity.this, SecondActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                PermissionManager.getInstance().requestPermission(SecondActivity.this, SecondActivity.this, new String[]{Manifest.permission.CAMERA});
//                PermissionManager.getInstance().requestAlertWindowPermission(SecondActivity.this, SecondActivity.this);

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
    public void onSuccess(String... permissions) {
        ToastTools.show(this,"成功",1);
    }

    @Override
    public void onFail(String... permissions) {
        ToastTools.show(this,"失败",1);
    }
}
