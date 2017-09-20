package com.colpencil.propertycloud.View.Fragments.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.FitProcess;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.Tools.WarnDialog;
import com.colpencil.propertycloud.View.Activitys.CloseManager.ChecKStatueActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.MaterProgressActivity;
import com.colpencil.propertycloud.View.Activitys.Home.PaySureActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.NewFitmentPrgressAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.colpencil.propertycloud.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


@ActivityFragmentInject(
        contentViewId = R.layout.fragment_fit_process
)
public class NewFitmentProgressFragment extends ColpencilFragment {

    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;

    @Bind(R.id.lv_fit)
    ListView lv_fit;

    TextView house_name;

    private int mType;

    private String id;
    private List<FitProcess> fitProcessList = new ArrayList<>();
    private NewFitmentPrgressAdapter adapter;
    private Intent intent;

    private Observable<RxBusMsg> update;


    public static NewFitmentProgressFragment newInstance(int type, String id) {
        Bundle args = new Bundle();
        NewFitmentProgressFragment fragment = new NewFitmentProgressFragment();
        args.putInt("type", type);
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        lv_fit.setVisibility(View.GONE);
        rlProgress.setVisibility(View.VISIBLE);
        mType = getArguments().getInt("type");
        id = getArguments().getString("id");

        View houseHead = View.inflate(getActivity(), R.layout.item_house_head, null);
        house_name = (TextView) houseHead.findViewById(R.id.house_name);
        lv_fit.addHeaderView(houseHead);

        adapter = new NewFitmentPrgressAdapter(fitProcessList, getActivity());
        lv_fit.setAdapter(adapter);

        getProcess(id);

        update = RxBus.get().register("update", RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                switch (rxBusMsg.getType()) {
                    case 0:
                        fitProcessList.clear();
                        getProcess(id);
                        break;
                }
            }
        };
        update.subscribe(subscriber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("update",update);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    private void getProcess(String approveId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("approveId", approveId);
        RetrofitManager.getInstance(1, CluodApplaction.getInstance(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .curProgressEn(map)
                .map(new Func1<ResultListInfo<FitProcess>, ResultListInfo<FitProcess>>() {
                    @Override
                    public ResultListInfo<FitProcess> call(ResultListInfo<FitProcess> fitmentProcessResultListInfo) {
                        return fitmentProcessResultListInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultListInfo<FitProcess>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息：" + e.getMessage());
                    }

                    @Override
                    public void onNext(final ResultListInfo<FitProcess> fitmentProcessResultListInfo) {
                        int code = fitmentProcessResultListInfo.code;
                        String message = fitmentProcessResultListInfo.message;
                        switch (code) {
                            case 0:
                                lv_fit.setVisibility(View.VISIBLE);
                                rlProgress.setVisibility(View.GONE);
                                house_name.setText(fitmentProcessResultListInfo.house_name);
                                fitProcessList.addAll(fitmentProcessResultListInfo.data);
                                adapter.notifyDataSetChanged();
                                lv_fit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long ids) {

                                        switch (position) {
                                            case 1:
                                                intent = new Intent(getActivity(), MaterProgressActivity.class);
                                                intent.putExtra("id", id + "");
                                                startActivity(intent);
                                                break;
                                            case 2:
                                                if (fitmentProcessResultListInfo.data.get(0).wholeprogress == 1){
                                                    switch (fitProcessList.get(1).commonStatus) {
                                                        case -1: // 待提交

                                                            break;
                                                        case 0: // 待审核

                                                            break;
                                                        case 1: // 已审核

                                                            break;
                                                        case 2: // 已拒绝
                                                            // TODO: 2017/1/10 拒绝的原因
                                                            intent = new Intent(getActivity(), ChecKStatueActivity.class);
                                                            intent.putExtra("aprovid", fitmentProcessResultListInfo.data.get(0).aprovid+"");
                                                            intent.putExtra("reason", fitmentProcessResultListInfo.data.get(1).remark);
                                                            startActivity(intent);
                                                            break;
                                                    }
                                                }
                                                break;
                                            case 3: // 去支付
                                                if (fitmentProcessResultListInfo.data.get(0).wholeprogress == 2) {
                                                    intent = new Intent(getActivity(), PaySureActivity.class);
                                                    intent.putExtra("type", 1);
                                                    intent.putExtra("billIds", fitmentProcessResultListInfo.data.get(2).materList.get(0).sn + "," + fitmentProcessResultListInfo.data.get(2).materList.get(1).sn);
                                                    startActivity(intent);
                                                }
                                                break;
                                            case 4:
                                                if (fitmentProcessResultListInfo.data.get(0).wholeprogress == 3) {
                                                    if (fitmentProcessResultListInfo.data.get(3).commonStatus==0){
                                                        WarnDialog.showInfo(getActivity(), fitmentProcessResultListInfo.data.get(3).receiveInfo);
                                                    }
                                                }
                                                break;
                                            case 5:
                                                if (fitmentProcessResultListInfo.data.get(0).wholeprogress == 4){
                                                    if (fitProcessList.get(4).commonStatus==0){
                                                        // TODO: 2017/1/10 可以申请离场
                                                        MaterialDialog show = new MaterialDialog.Builder(getActivity())
                                                                .title("温馨提示")
                                                                .content("您确定要申请离场吗？")
                                                                .positiveText("是")
                                                                .negativeText("否")
                                                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                                    @Override
                                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                                        applaLeave( fitmentProcessResultListInfo.data.get(4).decorateid+"");
                                                                    }
                                                                })
                                                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                                                    @Override
                                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                                                    }
                                                                })
                                                                .show();
                                                    }
                                                }
                                                break;
                                        }
                                    }
                                });
                                break;
                            case 1:
                                ToastTools.showShort(getActivity(), false, message);
                                break;
                            case 2:

                                break;
                            case 3:
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    /**
     * 申请离场
     */
    private void applaLeave(String decorate_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("decorate_id", decorate_id);
        RetrofitManager.getInstance(1, getActivity(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .applyLeave(map)
                .map(new Func1<ResultInfo, ResultInfo>() {
                    @Override
                    public ResultInfo call(ResultInfo resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("服务器错误信息：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo resultInfo) {
                        String message = resultInfo.message;
                        switch (resultInfo.code) {
                            case 0:
                                fitProcessList.clear();
                                getProcess(id);
                                ToastTools.showShort(getActivity(), true, "申请成功！");
                                break;
                            case 1:
                                ToastTools.showShort(getActivity(), false, message);
                                break;
                            case 3:
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                break;
                        }
                    }
                });
    }
}
