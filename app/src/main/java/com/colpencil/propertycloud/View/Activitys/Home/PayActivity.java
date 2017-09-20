package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.colpencil.propertycloud.Present.Home.OrderPayActivity;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.EditTextUtils;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.ColpencilFrame;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import org.json.JSONObject;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xiaohuihui on 2017/1/5.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_pay
)
public class PayActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_ok)
    TextView tv_ok;
    @Bind(R.id.head)
    CircleImageView imageView;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.et_money)
    EditText et_money;
    @Bind(R.id.et_remark)
    EditText et_remark;

    private int sellId;

    @Override
    protected void initViews(View view) {
        tv_title.setText("向商户付款");
        ll_left.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        EditTextUtils.setPricePoint(et_money);
        initData();
    }

    private void initData() {
        String data = getIntent().getStringExtra("datas");
        try {
            JSONObject object = new JSONObject(data);
            Glide.with(this).load(object.optString("store_logo")).into(imageView);
            tv_name.setText(object.optString("store_name"));
            sellId = object.optInt("seller_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        int id = v.getId();
        if (id == R.id.ll_left) {
            finish();
        } else if (id == R.id.tv_ok) {
            String price = et_money.getText().toString();
            String remark = et_remark.getText().toString();
            if (TextUtils.isEmpty(price)) {
                ToastTools.showShort(this, "付款金额不能为空");
                return;
            }
            Intent intent = new Intent(this, OrderPayActivity.class);
            intent.putExtra("type", 2);
            intent.putExtra("totalAmount", Double.valueOf(price));
            intent.putExtra("remark", remark);
            intent.putExtra("sellId", sellId);
            startActivity(intent);
            mAm.finishActivity(ScanpayActivity.class);
            finish();
        }
    }

}
