package com.colpencil.propertycloud.View.Fragments.Home;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.colpencil.propertycloud.Bean.LiveStreaming;
import com.colpencil.propertycloud.Present.Home.LiveStreamListPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Adapter.LiveStreamListAdapter;
import com.colpencil.propertycloud.View.Imples.StreamingListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshViewHolder;

import java.util.List;

import butterknife.Bind;

/**
 * @author 汪 亮
 * @Description: 直播列表
 * @Email DramaScript@outlook.com
 * @date 2016/9/7
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_myfitment
)
public class LiveStreamingListFragment extends ColpencilFragment implements StreamingListView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.bg_current)
    BGARefreshLayout bg_current;

    @Bind(R.id.lv_current)
    ListView lv_current;
    private LiveStreamListAdapter adapter;
    private LiveStreamListPresent present;

    @Override
    protected void initViews(View view) {
        BGARefreshViewHolder holder = new BGANormalRefreshViewHolder(getActivity(),true);
        bg_current.setRefreshViewHolder(holder);
        bg_current.setDelegate(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new LiveStreamListPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        if (mPresenter == null)
            return ;
        ((LiveStreamListPresent)mPresenter).getList();
    }

    @Override
    public void getList(List<LiveStreaming> list) {
        adapter = new LiveStreamListAdapter(getActivity(), list, R.layout.item_streaming_pic);
        lv_current.setAdapter(adapter);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
