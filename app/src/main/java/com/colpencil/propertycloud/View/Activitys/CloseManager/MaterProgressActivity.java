package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
import com.colpencil.propertycloud.View.Activitys.BaseActivity;
import com.colpencil.propertycloud.View.Activitys.Home.DecorateApplyActivity;
import com.colpencil.propertycloud.View.Activitys.Home.ReadActivity;
import com.colpencil.propertycloud.View.Activitys.Home.RegistryFormListActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.MaterProgressAdapter;
import com.jph.takephoto.model.TResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.OkhttpUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

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

@ActivityFragmentInject(
        contentViewId = R.layout.activity_materi_progress
)
public class MaterProgressActivity extends BaseActivity {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_rigth)
    TextView tv_rigth;

    @Bind(R.id.ll_rigth)
    RelativeLayout ll_rigth;

    @Bind(R.id.lv_materi)
    ListView lv_materi;

    @Bind(R.id.tv_cun)
    TextView tv_cun;

    @Bind(R.id.tv_comit)
    TextView tv_comit;

    @Bind(R.id.ll_bottom)
    LinearLayout ll_bottom;

    private TextView house_name;

    private List<FitmentStatue> materList = new ArrayList<>();
    private MaterProgressAdapter adapter;
    private String id;
    private int aprovid;
    private int comunityid;
    private int houseid;
    private boolean flag = true;
    private List<File> files = new ArrayList<>();
    private File file;
    private Observable<RxBusMsg> mater;
    private int isAll = 0;

    @Override
    protected void initViews(View view) {
        tv_title.setText("签署材料");
        id = getIntent().getStringExtra("id");
        tv_rigth.setText("取消申请");
        ll_rigth.setVisibility(View.VISIBLE);
        View houseHead = View.inflate(this, R.layout.item_house_head2, null);
        house_name = (TextView) houseHead.findViewById(R.id.house_name);
        lv_materi.addHeaderView(houseHead);
        adapter = new MaterProgressAdapter(this, materList);
        lv_materi.setAdapter(adapter);
        getData();
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mater = RxBus.get().register("mater", RxBusMsg.class);
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
                        getData();
                        break;
                }
            }
        };
        mater.subscribe(subscriber);
        tv_cun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(0);
                RxBus.get().post("update", rxBusMsg);
                ToastTools.showShort(MaterProgressActivity.this, true, "保存成功！");
                finish();
            }
        });
        tv_comit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < materList.size(); i++) {
                    if (materList.get(i).status == 0) {
                        isAll = 1;
                    }
                }
                if (isAll == 1) {
                    ToastTools.showShort(MaterProgressActivity.this, "你将相关签署材料填写完整再提交！");
                    return;
                }
                confirmDecortApprove(aprovid);
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

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        files.clear();
        for (int i = 0; i < result.getImages().size(); i++) {
            file = new File(result.getImages().get(i).getCompressPath());
            files.add(file);
        }
        if (flag) {
            change(houseid + "", aprovid + "", null, files);
        } else {
            change(houseid + "", aprovid + "", files, null);
        }
    }

    private void getProcess(final String approveId) {
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
                                materList.clear();
                                house_name.setText(fitmentProcessResultListInfo.house_name);
                                aprovid = fitmentProcessResultListInfo.data.get(0).aprovid;
                                comunityid = fitmentProcessResultListInfo.data.get(0).comunityid;
                                houseid = fitmentProcessResultListInfo.data.get(0).houseid;
                                materList.addAll(fitmentProcessResultListInfo.data.get(0).materList);
                                adapter.setWholeprogress(fitmentProcessResultListInfo.data.get(0).wholeprogress);
                                adapter.setApproveid(aprovid);
                                adapter.notifyDataSetChanged();
                                if (fitmentProcessResultListInfo.data.get(0).wholeprogress <= 1) {
                                    ll_bottom.setVisibility(View.VISIBLE);
                                    ll_rigth.setVisibility(View.VISIBLE);
                                    if (fitmentProcessResultListInfo.data.get(0).wholeprogress == 1) {
                                        if ("已拒绝".equals(fitmentProcessResultListInfo.data.get(1).group)) {
                                            tv_comit.setText("重新提交");
                                        }
                                    }
                                } else {
                                    ll_bottom.setVisibility(View.GONE);
                                    ll_rigth.setVisibility(View.VISIBLE);
                                }
                                if (fitmentProcessResultListInfo.data.get(0).wholeprogress <= 1) {
                                    lv_materi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            switch (position) {
                                                case 1:
                                                    Intent intent = new Intent(MaterProgressActivity.this, ReadActivity.class);
                                                    intent.putExtra("flag", 1);
                                                    intent.putExtra("type", 1);
                                                    intent.putExtra("name", "装修管理规定");
                                                    intent.putExtra("community_id", comunityid + "");
                                                    intent.putExtra("aprovid", aprovid);
                                                    intent.putExtra("houseid", houseid);
                                                    startActivity(intent);
                                                    break;
                                                case 2:
                                                    intent = new Intent(MaterProgressActivity.this, ReadActivity.class);
                                                    intent.putExtra("flag", 2);
                                                    intent.putExtra("type", 1);
                                                    intent.putExtra("name", "装修承诺书");
                                                    intent.putExtra("community_id", comunityid + "");
                                                    intent.putExtra("aprovid", aprovid);
                                                    intent.putExtra("houseid", houseid);
                                                    startActivity(intent);
                                                    break;
                                                case 3:
                                                    ColpencilLogger.e("status="+materList.get(position-1).status+"position="+position);
                                                    if (materList.get(position-1).status == 0) { // 上传
                                                        intent = new Intent(MaterProgressActivity.this, DecorateApplyActivity.class);
                                                        intent.putExtra("type", 1);
                                                        intent.putExtra("aprovid", aprovid);
                                                        intent.putExtra("houseid", houseid);
                                                        startActivity(intent);
                                                    } else {
                                                        intent = new Intent(MaterProgressActivity.this, DecorateApplyActivity.class);
                                                        intent.putExtra("type", 2);
                                                        intent.putExtra("aprovid", aprovid);
                                                        intent.putExtra("houseid", houseid);
                                                        startActivity(intent);
                                                    }
                                                    break;
                                                case 4:
                                                    openSelect(false, 3);
                                                    flag = true;
                                                    break;
                                                case 5:
                                                    openSelect(false, 12);
                                                    flag = false;
                                                    break;
                                                case 6:
                                                    if (materList.get(position - 1).status == 0) {
                                                        intent = new Intent(MaterProgressActivity.this, RegistryFormListActivity.class);
                                                        intent.putExtra("approveid", aprovid);
                                                        intent.putExtra("houseId", houseid);
                                                        intent.putExtra("type", 1); // 0 是新增  1 ：修改
                                                        startActivity(intent);
                                                    } else {
                                                        intent = new Intent(MaterProgressActivity.this, RegistryFormListActivity.class);
                                                        intent.putExtra("approveid", aprovid);
                                                        intent.putExtra("houseId", houseid);
                                                        intent.putExtra("type", 0); // 0 是新增  1 ：修改
                                                        startActivity(intent);
                                                    }
                                                    break;
                                            }
                                        }
                                    });
                                }
                                break;
                            case 1:
                                ToastTools.showShort(MaterProgressActivity.this, false, message);
                                break;
                            case 2:

                                break;
                            case 3:
                                startActivity(new Intent(MaterProgressActivity.this, LoginActivity.class));
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
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
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
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("update", rxBusMsg);
                                finish();
                                ToastTools.showShort(MaterProgressActivity.this, true, "提交成功！");
                                break;
                            case 1:
                                ToastTools.showShort(MaterProgressActivity.this, false, message);
                                break;
                            case 3:
                                startActivity(new Intent(MaterProgressActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    /**
     * 提交 扫描件
     *
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
        RetrofitManager.getInstance(1, this, HostUrl.BASE_URL)
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
                                    NotificationTools.show(MaterProgressActivity.this, "该房间已提交申请。");
                                    return;
                                }
                                getData();
                                ToastTools.showShort(MaterProgressActivity.this, true, "提交成功！");
                                break;
                            case 1:
                                ToastTools.showShort(MaterProgressActivity.this, false, message);
                                break;
                            case 3:
                                startActivity(new Intent(MaterProgressActivity.this, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    private void getData() {
        getProcess(id + "");
    }
}
