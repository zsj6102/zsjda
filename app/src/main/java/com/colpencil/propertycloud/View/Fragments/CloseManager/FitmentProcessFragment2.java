package com.colpencil.propertycloud.View.Fragments.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.Approveid;
import com.colpencil.propertycloud.Bean.FitProcess;
import com.colpencil.propertycloud.Bean.FitmentStatue;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Home.DecorateApplyActivity;
import com.colpencil.propertycloud.View.Activitys.Home.ReadActivity;
import com.colpencil.propertycloud.View.Activitys.Home.RegistryFormListActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.FitMaterAdapter;
import com.colpencil.propertycloud.View.Adapter.FitProcessAdapter;
import com.colpencil.propertycloud.View.Fragments.BaseFragment;
import com.jph.takephoto.model.TResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.OkhttpUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 装修进度
 * @Email DramaScript@outlook.com
 * @date 2016/11/17
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_fit_process
)
public class FitmentProcessFragment2 extends BaseFragment{


    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;

    @Bind(R.id.lv_fit)
    ListView lv_fit;

    TextView house_name;

    private int mType;

    private String id;

    private List<FitProcess> fitProcessList = new ArrayList<>();
    private List<FitmentStatue> materList = new ArrayList<>();

    private Observable<RxBusMsg> update;

    private FitProcessAdapter adapter;
    private FitMaterAdapter fitMaterAdapter;
    private ImageView iv_yuan;
    private View gr_vv2;
    private TextView tv_name;
    private RelativeLayout rl_fit_bottom;
    private TextView tv_comit;
    private TextView tv_del;
    private ColpencilListView cl_mater;
    private int aprovid;
    private int comunityid;
    private int houseid;
    private int wholeprogress;

    private boolean flag = true;

    private  int isAll = 0;

    private List<File> files = new ArrayList<>();
    private File file;

    public static FitmentProcessFragment2 newInstance(int type, String id) {
        Bundle args = new Bundle();
        FitmentProcessFragment2 fragment = new FitmentProcessFragment2();
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

        View fitHead = View.inflate(getActivity(), R.layout.item_fit_head, null);
        initHead(fitHead);

        lv_fit.addHeaderView(houseHead);
        lv_fit.addHeaderView(fitHead);

        adapter = new FitProcessAdapter(fitProcessList, getActivity());
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
                        onRefresh();
                        break;
                }
            }
        };
        update.subscribe(subscriber);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        files.clear();
        for (int i=0;i<result.getImages().size();i++){
            file = new File(result.getImages().get(i).getCompressPath());
            files.add(file);
        }
        if (flag){
            change(houseid+"",aprovid+"",null,files);
        }else {
            change(houseid+"",aprovid+"",files,null);
        }
    }

    private void initHead(View fitHead) {
        iv_yuan = (ImageView) fitHead.findViewById(R.id.iv_yuan);
        gr_vv2 = fitHead.findViewById(R.id.gr_vv2);
        tv_name = (TextView) fitHead.findViewById(R.id.tv_name);
        rl_fit_bottom = (RelativeLayout) fitHead.findViewById(R.id.rl_fit_bottom);
        tv_comit = (TextView) fitHead.findViewById(R.id.tv_comit);
        tv_del = (TextView) fitHead.findViewById(R.id.tv_del);
        cl_mater = (ColpencilListView) fitHead.findViewById(R.id.cl_mater);
        fitMaterAdapter = new FitMaterAdapter(materList, getActivity());
        cl_mater.setAdapter(fitMaterAdapter);
        tv_comit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<materList.size();i++){
                    if (materList.get(i).status==0){
                        isAll = 1;
                    }
                }
                if (isAll==1){
                    ToastTools.showShort(getActivity(),"你将相关签署材料填写完整再提交！");
                    return;
                }
                confirmDecortApprove(aprovid);
            }
        });
        tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disFitment(aprovid);
            }
        });
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
                    public void onNext(ResultListInfo<FitProcess> fitmentProcessResultListInfo) {
                        int code = fitmentProcessResultListInfo.code;
                        String message = fitmentProcessResultListInfo.message;
                        switch (code) {
                            case 0:
                                lv_fit.setVisibility(View.VISIBLE);
                                rlProgress.setVisibility(View.GONE);
                                aprovid = fitmentProcessResultListInfo.data.get(0).aprovid;
                                comunityid = fitmentProcessResultListInfo.data.get(0).comunityid;
                                houseid = fitmentProcessResultListInfo.data.get(0).houseid;
                                house_name.setText(fitmentProcessResultListInfo.house_name);
                                fitProcessList.addAll(fitmentProcessResultListInfo.data);
                                wholeprogress = fitProcessList.get(0).wholeprogress;
                                fitMaterAdapter.setWholeprogress(wholeprogress);
                                fitMaterAdapter.setApproveid(aprovid);
                                materList.addAll(fitmentProcessResultListInfo.data.get(0).materList);
                                fitMaterAdapter.notifyDataSetChanged();
                                adapter.notifyDataSetChanged();

                                if (wholeprogress <= 1) {
                                    cl_mater.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            switch (position) {
                                                case 0: // 装修公司管理规定
                                                    Intent intent = new Intent(getActivity(), ReadActivity.class);
                                                    intent.putExtra("flag", 1);
                                                    intent.putExtra("type", 1);
                                                    intent.putExtra("name", "装修管理规定");
                                                    intent.putExtra("community_id", comunityid + "");
                                                    intent.putExtra("aprovid", aprovid);
                                                    intent.putExtra("houseid", houseid);
                                                    startActivity(intent);
                                                    break;
                                                case 1: // 装修承诺书
                                                    intent = new Intent(getActivity(), ReadActivity.class);
                                                    intent.putExtra("flag", 2);
                                                    intent.putExtra("type", 1);
                                                    intent.putExtra("name", "装修承诺书");
                                                    intent.putExtra("community_id", comunityid + "");
                                                    intent.putExtra("aprovid", aprovid);
                                                    intent.putExtra("houseid", houseid);
                                                    startActivity(intent);
                                                    break;
                                                case 2: // 装修申请表
                                                    if (materList.get(position).status == 0) { // 上传
                                                        intent = new Intent(getActivity(), DecorateApplyActivity.class);
                                                        intent.putExtra("type", 1);
                                                        intent.putExtra("aprovid", aprovid);
                                                        intent.putExtra("houseid", houseid);
                                                        startActivity(intent);
                                                    } else {
                                                        intent = new Intent(getActivity(), DecorateApplyActivity.class);
                                                        intent.putExtra("type", 2);
                                                        intent.putExtra("aprovid", aprovid);
                                                        intent.putExtra("houseid", houseid);
                                                        startActivity(intent);
                                                    }
                                                    break;
                                                case 3: // 装修公司资质资料
                                                    openSelect(false, 3);
                                                    flag = true;
                                                    break;
                                                case 4: // 装修设计资料扫描件
                                                    openSelect(false, 12);
                                                    flag = false;
                                                    break;
                                                case 5: // 装修人员登记表
                                                    if (materList.get(position).status == 0) {
                                                        intent = new Intent(getActivity(), RegistryFormListActivity.class);
                                                        intent.putExtra("approveid", aprovid);
                                                        intent.putExtra("type", 1); // 0 是新增  1 ：修改
                                                        startActivity(intent);
                                                    } else {
                                                        intent = new Intent(getActivity(), RegistryFormListActivity.class);
                                                        intent.putExtra("approveid", aprovid);
                                                        intent.putExtra("type", 0); // 0 是新增  1 ：修改
                                                        startActivity(intent);
                                                    }
                                                    break;
                                            }
                                        }
                                    });
                                    if (wholeprogress==1){
                                        rl_fit_bottom.setVisibility(View.GONE);
                                    }
                                }else {
                                    rl_fit_bottom.setVisibility(View.GONE);
                                }
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

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    private void onRefresh() {
        materList.clear();
        fitProcessList.clear();
        getProcess(id);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("update", update);
    }

    /**
     * 提交 扫描件
     * @param houseId
     * @param approveid
     * @param decortDsnInfo
     * @param decortCoQua
     */
    private void change(String houseId, String approveid, List<File> decortDsnInfo, List<File> decortCoQua) {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("houseId", OkhttpUtils.toRequestBody(houseId));
        map.put("approveid", OkhttpUtils.toRequestBody(approveid));
        if (decortDsnInfo != null && decortDsnInfo.size() != 0) {
            for (int i = 0; i < decortDsnInfo.size(); i++) {
                map.put("decortDsnInfo\";filename=\"" + "imageList1" + i + ".jpg", RequestBody.create(MediaType.parse("image/png"), decortDsnInfo.get(i)));
            }
        }
        if (decortCoQua != null && decortCoQua.size() != 0) {
            for (int i = 0; i < decortCoQua.size(); i++) {
                map.put("decortCoQua\";filename=\"" + "imageList5" + i + ".jpg", RequestBody.create(MediaType.parse("image/png"), decortCoQua.get(i)));
            }
        }
        RetrofitManager.getInstance(1, getActivity(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .decortApprove(map)
                .map(new Func1<ResultInfo<Approveid>, ResultInfo<Approveid>>() {
                    @Override
                    public ResultInfo call(ResultInfo<Approveid> resultInfo) {
                        return resultInfo;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultInfo<Approveid>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ColpencilLogger.e("请求错误日志：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo<Approveid> resultInfo) {
                        int code = resultInfo.code;
                        String message = resultInfo.message;
                        switch (code) {
                            case 0:
                                if (resultInfo.data.approveid == -1) {
                                    NotificationTools.show(getActivity(), "该房间已提交申请。");
                                    return;
                                }
                                onRefresh();
                                ToastTools.showShort(getActivity(), true, "提交成功！");
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

    /**
     * 确认提交申请
     *
     * @param aprovid
     */
    private void confirmDecortApprove(int aprovid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("approveid", aprovid + "");
        RetrofitManager.getInstance(1, getActivity(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .confirmDecortApprove(map)
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
                              onRefresh();
                                ToastTools.showShort(getActivity(), true, "提交成功！");
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

    /**
     * 取消装修
     *
     * @param aprovid
     */
    private void disFitment(int aprovid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("approveid", aprovid + "");
        RetrofitManager.getInstance(1, getActivity(), HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .cancelDecortApprove(map)
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
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("cg", rxBusMsg);
                                ToastTools.showShort(getActivity(), true, "取消成功！");
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
