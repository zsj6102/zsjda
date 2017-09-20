package com.colpencil.propertycloud.View.Fragments.Home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.GoodsList;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Adapter.GoodsListAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.ta.utdid2.android.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_find_goods
)
public class FindGoodsFragment extends ColpencilFragment {

    @Bind(R.id.lv_goods)
    ListView lv_goods;

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

    private int type;

    private List<GoodsList> lists = new ArrayList<>();

    private GoodsListAdapter adapter;

    public static FindGoodsFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        FindGoodsFragment fragment = new FindGoodsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        lv_goods.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        type = getArguments().getInt("type");
        adapter = new GoodsListAdapter(getActivity(),lists, R.layout.item_goods_list);
        lv_goods.setAdapter(adapter);
        getInfo();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    private void getInfo(){
        lists.clear();
        HashMap<String, String> params = new HashMap<>();
        params.put("cat_id",type+"");
        RetrofitManager.getInstance(1,getActivity(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .Goods(params)
                .map(new Func1<ResultListInfo<GoodsList>, ResultListInfo<GoodsList>>() {
                    @Override
                    public ResultListInfo<GoodsList> call(ResultListInfo<GoodsList> findGoodsIdResultListInfo) {
                        return findGoodsIdResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<GoodsList>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(final ResultListInfo<GoodsList> findGoodsIdResultListInfo) {
                        int code = findGoodsIdResultListInfo.code;
                        String message = findGoodsIdResultListInfo.message;
                        switch (code) {
                            case 0:
                                lists.addAll(findGoodsIdResultListInfo.data);
                                lv_goods.setVisibility(View.VISIBLE);
                                rlProgress.setVisibility(View.GONE);
                                rlEmpty.setVisibility(View.GONE);
                                adapter.setUrl(findGoodsIdResultListInfo.url);
                                adapter.notifyDataSetChanged();
                                break;
                            case 2:
                                lv_goods.setVisibility(View.GONE);
                                rlProgress.setVisibility(View.GONE);
                                rlEmpty.setVisibility(View.VISIBLE);
                                btnReload.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getInfo();
                                    }
                                });
                                break;
                            case 1:
                                lv_goods.setVisibility(View.GONE);
                                rlProgress.setVisibility(View.VISIBLE);
                                rlEmpty.setVisibility(View.GONE);
                                btnReloadEmpty.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getInfo();
                                    }
                                });
                                ToastTools.showShort(getActivity(), false, message);
                                break;

                        }
                    }
                });
    }
}
