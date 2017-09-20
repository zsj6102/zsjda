package com.colpencil.propertycloud.View.Fragments.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.colpencil.propertycloud.Bean.AdviceList;
import com.colpencil.propertycloud.Bean.ListAdvice;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Present.Home.AdviceListPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Home.AdviceDetailActivity;
import com.colpencil.propertycloud.View.Adapter.AdviceListAdapter;
import com.colpencil.propertycloud.View.Imples.AdviceListView;
import com.google.gson.Gson;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author 汪 亮
 * @Description: 物业意见
 * @Email DramaScript@outlook.com
 * @date 2016/9/7
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_advice_list
)
public class AdviceListFragment extends ColpencilFragment implements AdviceListView {


    private AdviceListPresent present;

    private AdviceListAdapter adapter;

    private int mType;

    @Bind(R.id.lv_normal)
    RecyclerView lv_normal;

    private LinearLayoutManager layoutManager;

    private List<AdviceList> lists = new ArrayList<>();
    private Gson gson;
    private String advice;
    private ListAdvice listAdvice;
    private int aprovid;

    public static AdviceListFragment newInstance(int type, String advice) {
        Bundle args = new Bundle();
        AdviceListFragment fragment = new AdviceListFragment();
        args.putInt("type", type);
        args.putString("advice", advice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        gson = new Gson();
        getData();
        layoutManager = new LinearLayoutManager(getActivity());
        lv_normal.setLayoutManager(layoutManager);

    }

    private void getData() {
        mType = getArguments().getInt("type");
        advice = getArguments().getString("advice");
        listAdvice = gson.fromJson(advice, ListAdvice.class);
        setData(listAdvice);
    }

    private void setData(final ListAdvice listAdvice) {
        adapter = new AdviceListAdapter(getActivity(), listAdvice.opinion, R.layout.item_advice);
        lv_normal.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Intent intent =  new Intent(getActivity(), AdviceDetailActivity.class);
                String json = gson.toJson(listAdvice.opinion.get(position));
                intent.putExtra("json",json);
                startActivity(intent);
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new AdviceListPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }


    @Override
    public void refresh(ResultListInfo<AdviceList> result) {

    }

    @Override
    public void loadMore(ResultListInfo<AdviceList> result) {

    }

    @Override
    public void loadError(String msg) {
    }

}
