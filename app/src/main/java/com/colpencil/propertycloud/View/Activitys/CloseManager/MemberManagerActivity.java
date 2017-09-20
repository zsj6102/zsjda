package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.Member;
import com.colpencil.propertycloud.Present.CloseManager.MemberPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Adapter.MemberAdapter;
import com.colpencil.propertycloud.View.Imples.MemberManagerView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * @Description: 成员管理
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/12
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_member
)
public class MemberManagerActivity extends ColpencilActivity implements MemberManagerView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.bg_normal)
    BGARefreshLayout bg_normal;

    @Bind(R.id.lv_normal)
    ListView lv_normal;

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_add_own)
    TextView tv_add_own;

    @Bind(R.id.tv_zu)
    TextView tv_zu;
    private MemberPresent present;
    private MemberAdapter adapter;
    private Intent intent;

    @Override
    protected void initViews(View view) {
        tv_title.setText("成员管理");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        BGARefreshViewHolder holder = new BGANormalRefreshViewHolder(this, true);
        bg_normal.setRefreshViewHolder(holder);
        bg_normal.setDelegate(this);
        tv_add_own.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/9/12 添加家庭成员
                intent = new Intent(MemberManagerActivity.this,AddMemberActivity.class);
                startActivity(intent);
            }
        });
        tv_zu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/9/12 添加租客

            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new MemberPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        if (mPresenter==null)
            return;
        ((MemberPresent)mPresenter).getMemberList();
    }

    @Override
    public void memberList(List<Member> list) {
        adapter = new MemberAdapter(this,list, R.layout.item_member);
        lv_normal.setAdapter(adapter);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
