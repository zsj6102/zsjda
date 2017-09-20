package com.colpencil.propertycloud.View.Fragments.CloseManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.Approveid;
import com.colpencil.propertycloud.Bean.CurProgress;
import com.colpencil.propertycloud.Bean.Fitment;
import com.colpencil.propertycloud.Bean.FitmentProcess;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Home.MaterialManagementActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Adapter.FitmentAdapter;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NotificationTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.OkhttpUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;
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
        contentViewId = R.layout.fragment_fitment_process
)
public class FitmentProcessFragment extends ColpencilFragment {

    private int mType;

    @Bind(R.id.expend_fitment)
    ExpandableListView expend_fitment;

    public static final int REQUEST_CODE_SELECT = 100;
    public ArrayList<ImageItem> selImageList = new ArrayList<>(); //当前选择的所有图片
    public ArrayList<ImageItem> selImageList2 = new ArrayList<>(); //当前选择的所有图片
    public boolean flag = false;
    private List<FitmentProcess> list = new ArrayList<>();
    private FitmentAdapter adapter;
    private String curProgress;
    private Gson gson;
    private CurProgress progress;

    public List<File> files1 = new ArrayList<>();
    public List<File> files2 = new ArrayList<>();
    private Observable<RxBusMsg> update;

    public static FitmentProcessFragment newInstance(int type, String curProgress) {
        Bundle args = new Bundle();
        FitmentProcessFragment fragment = new FitmentProcessFragment();
        args.putInt("type", type);
        args.putString("curProgress", curProgress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        gson = new Gson();
        getData();
        expend_fitment.setGroupIndicator(null);
        adapter = new FitmentAdapter(list, getActivity(),this);
        expend_fitment.setAdapter(adapter);
        //默认展开所有
        int groupCount = expend_fitment.getCount();
        for (int i = 0; i < groupCount; i++) {
            expend_fitment.expandGroup(i);
        }

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
                switch (rxBusMsg.getType()){
                    case 0:
                        list.clear();
                        getData();
                        adapter.notifyDataSetChanged();
                        ColpencilLogger.e("我刷新啦。。。。。");
                        break;
                }
            }
        };
        update.subscribe(subscriber);
    }

    private void getData(){
        mType = getArguments().getInt("type");
        curProgress = getArguments().getString("curProgress");
        progress = gson.fromJson(this.curProgress, CurProgress.class);
        setData(progress);
    }

    /**
     * 设置数据
     * @param progress
     */
    private void setData(CurProgress progress) {
        FitmentProcess fitmentProcess = new FitmentProcess();
        fitmentProcess.content = "签署材料";
        fitmentProcess.auditstatus = progress.auditstatus;
        fitmentProcess.progress = progress.wholeprogress;
        fitmentProcess.aprovid = progress.aprovid;
        fitmentProcess.houseid = progress.houseid;
        fitmentProcess.comunityid = progress.communityid;
        fitmentProcess.json = curProgress;
        fitmentProcess.depositsn = progress.depositsn;
        fitmentProcess.relcostsn = progress.relcostsn;
        fitmentProcess.refund_reason = progress.refund_reason;
        fitmentProcess.depositpaystatus = progress.depositpaystatus;
        fitmentProcess.relcostspaystatus = progress.relcostspaystatus;
        fitmentProcess.decorateid = progress.decorateid;
        fitmentProcess.is_apply_leave = progress.is_apply_leave;
        ColpencilLogger.e("depositpaystatus="+fitmentProcess.depositpaystatus+",relcostspaystatus="+fitmentProcess.relcostspaystatus);
        ColpencilLogger.e("progress="+ progress.toString());
        for (int i=0;i<6;i++){
            Fitment fitment = new Fitment();
            fitment.deposit = progress.deposit;
            fitment.depositpaystatus = progress.depositpaystatus;
            fitment.relcostspaystatus = progress.relcostspaystatus;

            fitment.regulation = progress.regulation;
            fitment.design = progress.design;
            fitment.application = progress.application;
            fitment.aptitude = progress.aptitude;
            fitment.promise = progress.promise;
            fitment.personreg = progress.personreg;


            switch (i){
                case 0:
                    fitment.name = "《装修管理规定》";
                    break;
                case 1:
                    fitment.name = "《装修承诺书》";
                    break;
                case 2:
                    fitment.name = "《装修申请表》";
                    break;
                case 3:
                    fitment.name = "《装修公司资质》扫描件";
                    break;
                case 4:
                    fitment.name = "  装修设计资料扫描件";
                    break;
                case 5:
                    fitment.name = "《装修人员登记表》";
                    break;

            }
            fitmentProcess.fitments.add(fitment);
        }
        list.add(fitmentProcess);

        FitmentProcess fitmentProcess2 = new FitmentProcess();
        fitmentProcess2.aprovid = progress.aprovid;
        if (progress.wholeprogress<1){
            fitmentProcess2.content = "待审核";
        }else {
            if (progress.auditstatus==1){
                fitmentProcess2.content = "已审核";
            }else {
                fitmentProcess2.content = "待审核";
            }

        }
        fitmentProcess.auditstatus = progress.auditstatus;
        fitmentProcess2.progress = progress.wholeprogress;
        list.add(fitmentProcess2);

        if (progress.auditstatus!=2){
            FitmentProcess fitmentProcess3 = new FitmentProcess();
            fitmentProcess3.aprovid = progress.aprovid;
            if (progress.wholeprogress<2){
                fitmentProcess3.content = "待支付";
            }else {
                if (progress.depositpaystatus==-1||progress.relcostspaystatus==-1||progress.depositpaystatus==0||progress.relcostspaystatus==0){
                    fitmentProcess3.content = "待支付";
                }else {
                    fitmentProcess3.content = "已支付";
                }
            }
            fitmentProcess.auditstatus = progress.auditstatus;
            fitmentProcess3.progress = progress.wholeprogress;
            if (progress.wholeprogress>=2){
                for (int i=0;i<2;i++){
                    Fitment fitment = new Fitment();
                    fitment.deposit = progress.deposit;
                    fitment.relcosts = progress.relcosts;
                    fitment.depositpaystatus = progress.depositpaystatus;
                    fitment.relcostspaystatus = progress.relcostspaystatus;
                    fitmentProcess3.fitments.add(fitment);
                }
            }
            list.add(fitmentProcess3);

            FitmentProcess fitmentProcess4 = new FitmentProcess();
            fitmentProcess4.content = "待领取装修许可证";
            fitmentProcess.auditstatus = progress.auditstatus;
            fitmentProcess4.progress = progress.wholeprogress;
            fitmentProcess4.aprovid = progress.aprovid;
            list.add(fitmentProcess4);

            FitmentProcess fitmentProcess5 = new FitmentProcess();
            fitmentProcess5.content = "装修中";
            fitmentProcess5.aprovid = progress.aprovid;
            fitmentProcess.auditstatus = progress.auditstatus;
            fitmentProcess5.progress = progress.wholeprogress;
            if (progress.wholeprogress==4){
                Fitment fitment5 = new Fitment();
                fitmentProcess5.fitments.add(fitment5);
            }
            list.add(fitmentProcess5);

            FitmentProcess fitmentProcess6 = new FitmentProcess();
            fitmentProcess6.content = "已离场";
            fitmentProcess6.aprovid = progress.aprovid;
            fitmentProcess.auditstatus = progress.auditstatus;
            fitmentProcess6.progress = progress.wholeprogress;
            list.add(fitmentProcess6);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (flag == false) {
                    selImageList.addAll(images);
                } else {
                    selImageList2.addAll(images);
                }
                if (flag == false) { // 物业公司资质资料
                    if (selImageList.size() != 0) {
                        DialogTools.showLoding(getActivity(), "温馨提示", "正在获取中");
                        files2.clear();
                        Luban.get(getActivity())
                                .load(new File(selImageList.get(0).path))
                                .putGear(Luban.THIRD_GEAR)
                                .setCompressListener(new OnCompressListener() {
                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        DialogTools.dissmiss();
//                                        files1.add(file);
                                        change(progress.houseid+"",progress.aprovid+"",null,file);
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                }).launch();
                        ;
                        ColpencilLogger.e("我是物业公司资质资料:" + selImageList.get(0).path);
                    }


                } else { // 装修设计资料扫描件
                    if (selImageList2.size() != 0) {
                        DialogTools.showLoding(getActivity(), "温馨提示", "正在获取中");
                        files2.clear();
                        Luban.get(getActivity())
                                .load(new File(selImageList2.get(0).path))
                                .putGear(Luban.THIRD_GEAR)
                                .setCompressListener(new OnCompressListener() {
                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        DialogTools.dissmiss();
//                                        files2.add(file);
                                        change(progress.houseid+"",progress.aprovid+"",file,null);
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                }).launch();
                        ;
                    }

                }
            }
        }
    }

    private void change(String houseId,String approveid, File decortDsnInfo,File decortCoQua){
        Map<String, RequestBody> map = new HashMap<>();
        map.put("houseId", OkhttpUtils.toRequestBody(houseId));
        map.put("approveid", OkhttpUtils.toRequestBody(approveid));
        if (decortDsnInfo != null) {
            map.put("decortDsnInfo\";filename=\"" + "imageList" + 1 + ".jpg", RequestBody.create(MediaType.parse("image/png"), decortDsnInfo));
        }
        if (decortCoQua != null) {
            map.put("decortCoQua\";filename=\"" + "imageList" + 5 + ".jpg", RequestBody.create(MediaType.parse("image/png"), decortCoQua));
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
                                if (resultInfo.data.approveid==-1){
                                    NotificationTools.show(getActivity(),"该房间已提交申请。");
                                    return;
                                }
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("cg", rxBusMsg);
                                ToastTools.showShort(getActivity(), true, "提交成功！");
                                break;
                            case 1:
                                ToastTools.showShort(getActivity(), false, message);
                                break;
                            case 2:
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("update",update);
        System.gc();
    }
}
