package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.LiveInfoList;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Present.CloseManager.LiveInfoPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.LiveInfoAdapter;
import com.colpencil.propertycloud.View.Imples.LiveInfoListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author 汪 亮
 * @Description: 居住情况
 * @Email DramaScript@outlook.com
 * @date 2016/9/12
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_liveinfo
)
public class LiveInfoActivity extends ColpencilActivity implements LiveInfoListView, View.OnClickListener {


    @Bind(R.id.lv_ju)
    ListView lv_ju;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;

    @Bind(R.id.rlEmpty)
    RelativeLayout rlEmpty;

    @Bind(R.id.rlError)
    RelativeLayout rlError;

    @Bind(R.id.btnReloadEmpty)
    Button btnReloadEmpty;

    @Bind(R.id.btnReload)
    Button btnReload;

    private LiveInfoAdapter adapter;
    private LiveInfoPresent present;
    private Intent intent;

    private List<LiveInfoList> lists = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        tv_title.setText("家及家人");
        ll_left.setOnClickListener(this);
        lv_ju.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        adapter = new LiveInfoAdapter(this, lists, R.layout.item_ju);
        lv_ju.setAdapter(adapter);
        present.getLiveInfo();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new LiveInfoPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void liveInfoList(ResultListInfo<LiveInfoList> liveInfoListViews) {
        int code = liveInfoListViews.code;
        if (code == 0) {
            lv_ju.setVisibility(View.VISIBLE);
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.GONE);
        } else if (code == 2) {
            rlProgress.setVisibility(View.GONE);
            rlError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.VISIBLE);
            btnReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    present.getLiveInfo();
                }
            });
        } else if (code == 3) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        lists.clear();
        lists.addAll(liveInfoListViews.data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadError(String msg) {
        lv_ju.setVisibility(View.GONE);
        rlProgress.setVisibility(View.GONE);
        rlError.setVisibility(View.VISIBLE);
        rlEmpty.setVisibility(View.GONE);
        btnReloadEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.getLiveInfo();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }
    }
}
