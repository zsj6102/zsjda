package com.colpencil.tenement.View.Activitys.Workbeach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.tenement.Bean.Online;
import com.colpencil.tenement.Present.Home.ServiceListPresent;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Tools.XmppTools;
import com.colpencil.tenement.View.Adpaters.OnlineListAdapter;
import com.colpencil.tenement.View.Imples.OnlineListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshViewHolder;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;

import java.util.List;

import butterknife.Bind;

/**
 * @author 汪 亮
 * @Description: 在线客服列表
 * @Email DramaScript@outlook.com
 * @date 2016/8/24
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_online_list
)
public class OnlineListActivity extends ColpencilActivity implements OnlineListView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.bg_online_list)
    BGARefreshLayout bg_online_list;

    @Bind(R.id.lv_online_list)
    ListView lv_online_list;


    private OnlineListAdapter adapter;
    private ServiceListPresent present;
    private XMPPConnection con;
    private Roster roster;

    @Override
    protected void initViews(View view) {
        setStatecolor(getResources().getColor(R.color.colorPrimary));
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("在线客服");
        BGARefreshViewHolder holder = new BGANormalRefreshViewHolder(this, true);
        bg_online_list.setRefreshViewHolder(holder);
        bg_online_list.setDelegate(this);


    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new ServiceListPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
//        con = XmppTools.getInstance().getCon();
//        roster = con.getRoster();
        if (mPresenter == null)
            return;
        ((ServiceListPresent) mPresenter).getList(roster);
    }

    @Override
    public void onlineList(final List<Online> list) {
        ColpencilLogger.e("列表="+list.toString());
        adapter = new OnlineListAdapter(this, list, R.layout.item_online_list);
        lv_online_list.setAdapter(adapter);
        lv_online_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
