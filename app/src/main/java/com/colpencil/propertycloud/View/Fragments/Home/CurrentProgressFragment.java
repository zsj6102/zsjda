package com.colpencil.propertycloud.View.Fragments.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.Approve;
import com.colpencil.propertycloud.Bean.Current;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.Present.Home.CurrentPresent;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Home.MaterialManagementActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.CurrentAdpater;
import com.colpencil.propertycloud.View.Imples.CurrentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshViewHolder;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Description: 装修当前进度
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/9/7
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_myfitment
)
public class CurrentProgressFragment extends ColpencilFragment implements CurrentView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.bg_current)
    BGARefreshLayout bg_current;

    @Bind(R.id.lv_current)
    ListView lv_current;
    
    @Bind(R.id.iv_shen)
    ImageView iv_shen;
    
    private CurrentPresent present;
    private CurrentAdpater adpater;

    @Override
    protected void initViews(View view) {
        BGARefreshViewHolder holder = new BGANormalRefreshViewHolder(getActivity(),true);
        bg_current.setRefreshViewHolder(holder);
        bg_current.setDelegate(this);
        iv_shen.setVisibility(View.VISIBLE);
        iv_shen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/9/9 申请装修
                crtDecora();
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new CurrentPresent();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        if (mPresenter==null)
            return;
        ((CurrentPresent)mPresenter).getCurrent();
    }

    @Override
    public void getInfoList(List<Current> list) {
        adpater = new CurrentAdpater(getActivity(),list, R.layout.item_current);
        lv_current.setAdapter(adpater);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    /**
     * 生成装修申请id
     */
    private void crtDecora(){
        HashMap<String,String> map = new HashMap<>();
        RetrofitManager.getInstance(1,getActivity(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .crtDecora(map)
                .map(new Func1<ResultInfo<Approve>, ResultInfo<Approve>>() {
                    @Override
                    public ResultInfo<Approve> call(ResultInfo<Approve> approveResultInfo) {
                        return approveResultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<Approve>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误："+e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo<Approve> approveResultInfo) {
                        int code = approveResultInfo.code;
                        String message = approveResultInfo.message;
                        switch (code){
                            case 0:
                                Approve data = approveResultInfo.data;
                                Intent intent = new Intent(getActivity(), MaterialManagementActivity.class);
                                intent.putExtra("approveid",data.approveid);
                                startActivity(intent);
                                break;
                            case 1:
                                ToastTools.showShort(getActivity(),message);
                                break;
                            case 3:
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                break;
                        }
                    }
                });
    }
}
