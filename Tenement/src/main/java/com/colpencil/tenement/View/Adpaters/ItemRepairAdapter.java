package com.colpencil.tenement.View.Adpaters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.OwnerRepair;
import com.colpencil.tenement.Bean.Result;
import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Tools.PhotoPreview.PhotoActivity;
import com.colpencil.tenement.Ui.ExpandableTextView;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.AssignRepairActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.DoAdviceActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.FeedbackActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.ScanCodeActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.StartPolingActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilGridView;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 陈宝
 * @Description:业主报修
 * @Email DramaScript@outlook.com
 * @date 2016/8/25
 */
public class ItemRepairAdapter extends SuperAdapter<OwnerRepair> {

    private Activity context;
    private int type;
    private int Ordertype;

    public ItemRepairAdapter(Activity context, List<OwnerRepair> mDatas, int itemLayoutId,int type,int Ordertype) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        this.type = type;
        this.Ordertype = Ordertype;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, OwnerRepair item) {
        ColpencilGridView gridView = holder.getView(R.id.gridview);
        List<String> imageList = new ArrayList<>();
        for (int i=0;i<item.getImgUrls().size();i++){
            imageList.add(item.getImgUrls().get(i));
        }
        CleanImgAdapter adapter = new CleanImgAdapter(context, imageList, R.layout.item_green_image);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (item.getImgUrls().size()!=0){
                    List<String> imageList = new ArrayList<String>();
                    for (int i=0;i<item.getImgUrls().size();i++){
                        imageList.add(item.getImgUrls().get(i));
                    }
                    int childCount = parent.getChildCount();
                    ArrayList<Rect> rects = new ArrayList<>();
                    for (int i = 0; i < childCount; i++) {
                        Rect rect = new Rect();
                        View child = parent.getChildAt(i);
                        child.getGlobalVisibleRect(rect);
                        rects.add(rect);
                    }
                    Intent intent = new Intent(context, PhotoActivity.class);
                    String imageArray[] = new String[imageList.size()];
                    for(int i = 0;i<imageArray.length;i++){
                        imageArray[i] = imageList.get(i);
                    }
                    intent.putExtra("imgUrls", imageArray);
                    intent.putExtra("index", position);
                    intent.putExtra("bounds", rects);
                    context.startActivity(intent);
                    ColpencilLogger.e("size="+item.getImgUrls().size());
                    context.overridePendingTransition(0, 0);
                }
            }
        });
        if (item.getImgUrls().size()==0){
            gridView.setVisibility(View.GONE);
        }else {
            gridView.setVisibility(View.VISIBLE);
        }
        holder.setText(R.id.item_owner_repair_title,item.getTitle());
        holder.setText(R.id.item_owner_repair_desc,item.getDesc());
        holder.setText(R.id.item_owner_repair_village,item.getCommunityName());
        ColpencilLogger.e("VoiceDuration="+item.getVoiceDuration());
        if (item.getVoiceDuration()==-1.0){
            holder.setVisibility(R.id.ll_vocie,View.GONE);
        }else {
            holder.setVisibility(R.id.ll_vocie,View.VISIBLE);
            holder.setText(R.id.tv_time,item.getVoiceDuration()+"");
            holder.setOnClickListener(R.id.ll_vocie, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 播放录音文件
                    MediaPlayer player = new MediaPlayer();
                    try {
                        player.setDataSource(item.getVoiceUrl());
                        player.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    player.start();
                }
            });
        }
        if(type == 0){
            holder.setText(R.id.item_owner_repair_staff,"未指派");
        } else {
            holder.setText(R.id.item_owner_repair_staff,item.getStaff());
        }
        holder.setText(R.id.item_owner_repair_ownername,item.getOwnerName()+",");
        holder.setText(R.id.item_owner_repair_phone,item.getOwnerPhone());
        holder.setText(R.id.item_owner_repair_address,item.getAddress());
        holder.setText(R.id.item_owner_repair_time,item.getTime());



        holder.setOnClickListener(R.id.ll_call, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(item.getOwnerPhone())){
                    ToastTools.showShort(context,"拨打的电话有误！");
                    return;
                }
                MaterialDialog show = new MaterialDialog.Builder(context)
                        .title("温馨提示")
                        .content("是否拨打该电话？")
                        .positiveText(R.string.online_yes)
                        .negativeText(R.string.online_no)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                Uri data = Uri.parse("tel:" + item.getOwnerPhone());
                                intent.setData(data);
                                context.startActivity(intent);
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            }
                        })
                        .show();
            }
        });


        LinearLayout ll_btns = holder.getView(R.id.ll_btns);
        switch (item.getState()){
            case 0:
                holder.setVisibility(R.id.tv_statue,View.INVISIBLE);
                ll_btns.setWeightSum(2);
                holder.setVisibility(R.id.tv_chu,View.GONE);
                break;
            case 1:
                holder.setVisibility(R.id.tv_statue,View.INVISIBLE);
                ll_btns.setWeightSum(2);
                holder.setVisibility(R.id.tv_chu,View.GONE);
                break;
            case 2:
                holder.setVisibility(R.id.tv_statue,View.GONE);
                ll_btns.setWeightSum(2);
                holder.setVisibility(R.id.tv_chu,View.GONE);
                break;
            case 3:
                holder.setVisibility(R.id.tv_statue, View.VISIBLE);
                ll_btns.setWeightSum(2);
                holder.setVisibility(R.id.tv_chu,View.GONE);
                break;
        }
        if (item.getDevId()==-1){
            holder.setText(R.id.tv_see,"关联设备");
            holder.setOnClickListener(R.id.tv_see, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ScanCodeActivity.class);
                    intent.putExtra("where",2);
                    intent.putExtra("orderId",item.getOwnerRecordId());
                    intent.putExtra("eqType",item.getEqType()+"");
                    context.startActivity(intent);
                }
            });
        }else {
            holder.setText(R.id.tv_see,"维修记录");
            holder.setOnClickListener(R.id.tv_see, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, StartPolingActivity.class);
                    intent.putExtra("flag",0);
                    intent.putExtra("equipmentId",item.getDevId()+"");
                    intent.putExtra("eqType",item.getEqType()+"");
                    ColpencilLogger.e("devId="+item.getDevId());
                    ColpencilLogger.e("eqType="+item.getEqType());
                    context.startActivity(intent);
                }
            });
        }
        switch (type){
            case 0:
                holder.setVisibility(R.id.rl_chu, View.GONE);
                holder.setVisibility(R.id.rl_ping, View.GONE);
                ll_btns.setWeightSum(2);
                holder.setText(R.id.tv_do, "去指派");
                holder.setOnClickListener(R.id.tv_do, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 去指派，权限
                        Intent intent = new Intent(context, AssignRepairActivity.class);
                        intent.putExtra("orderId", item.getOwnerRecordId());
                        intent.putExtra("communityId", item.getCommunityId());
                        intent.putExtra(StringConfig.TYPE, 0);
                        context.startActivity(intent);
                    }
                });
                break;
            case 1:  // 待处理
                holder.setVisibility(R.id.rl_chu,View.GONE);
                holder.setVisibility(R.id.rl_ping,View.GONE);
                ll_btns.setWeightSum(2);
                holder.setText(R.id.tv_do,"开始处理");
                if(SharedPreferencesUtil.getInstance(context).getInt(StringConfig.USERID) == item.getEmpId()){
                    holder.setBackgroundResource(R.id.tv_do, R.drawable.rect_blue);
                } else {
                    holder.setBackgroundResource(R.id.tv_do, R.drawable.rect_gre);
                }
                holder.setOnClickListener(R.id.tv_do, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2016/9/20 开始处理
                        if(SharedPreferencesUtil.getInstance(context).getInt(StringConfig.USERID) == item.getEmpId()){
                            MaterialDialog show = new MaterialDialog.Builder(mContext)
                                    .title("温馨提示")
                                    .content("请您确认已到报修服务地点并准备开始进行维修工作")
                                    .positiveText(R.string.online_yes)
                                    .negativeText(R.string.online_no)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            stateChange(2,item.getOwnerRecordId());
                                        }
                                    })
                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        }
                                    })
                                    .show();
                        } else {
                            ToastTools.showShort(context, false, "您没有此记录的处理权限");
                        }
                    }
                });
                break;
            case 2://  处理中
                holder.setVisibility(R.id.rl_chu,View.GONE);
                holder.setVisibility(R.id.rl_ping,View.GONE);
                ll_btns.setWeightSum(2);
                holder.setText(R.id.tv_do,"已完成");
                if(SharedPreferencesUtil.getInstance(context).getInt(StringConfig.USERID) == item.getEmpId()){
                    holder.setBackgroundResource(R.id.tv_do, R.drawable.rect_blue);
                } else {
                    holder.setBackgroundResource(R.id.tv_do, R.drawable.rect_gre);
                    holder.setTextColor(R.id.tv_do, context.getResources().getColor(R.color.white));
                }
                holder.setOnClickListener(R.id.tv_do, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2016/9/20 完成
                        if(SharedPreferencesUtil.getInstance(context).getInt(StringConfig.USERID) == item.getEmpId()){
                            Intent intent = new Intent(context, DoAdviceActivity.class);
                            intent.putExtra("orderId",item.getOwnerRecordId()+"");
                            intent.putExtra("type",3);
                            intent.putExtra("flag",0);
                            context.startActivity(intent);
                        } else {
                            ToastTools.showShort(context, false, "您没有此记录的处理权限权限");
                        }
                    }
                });
                break;
            case 3://  已完成
                ExpandableTextView expand_text_view = holder.getView(R.id.expand_text_view);
                ExpandableTextView expand_text_view2 = holder.getView(R.id.expand_text_view2);
                if (TextUtils.isEmpty(item.getRepairDesc())){
                    holder.setVisibility(R.id.rl_chu,View.GONE);
                }else {
                    holder.setVisibility(R.id.rl_chu,View.VISIBLE);
                    expand_text_view.setText(item.getRepairDesc());
                }
                if (TextUtils.isEmpty(item.getFeedback())){
                    holder.setVisibility(R.id.rl_ping,View.GONE);
                }else {
                    holder.setVisibility(R.id.rl_ping,View.VISIBLE);
                    expand_text_view2.setText(item.getFeedback());
                }
                holder.setVisibility(R.id.tv_do,View.VISIBLE);
                ColpencilLogger.e("---------------------------执行啦！");
                if (item.getHasFeedback()==0){
                    ll_btns.setWeightSum(2);
                    holder.setText(R.id.tv_do,"处理意见");
                    holder.setVisibility(R.id.tv_chu,View.GONE);
                    holder.setVisibility(R.id.ll_btns,View.GONE);
                    holder.setOnClickListener(R.id.tv_do, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO: 2016/10/25 查看处理意见
                            Intent intent = new Intent(context, DoAdviceActivity.class);
                            intent.putExtra("orderId",item.getOwnerRecordId()+"");
                            intent.putExtra("type",100);
                            intent.putExtra("flag",0);
                            intent.putExtra("repairDesc",item.getRepairDesc());
                            context.startActivity(intent);
                        }
                    });
                }else {
                    ll_btns.setWeightSum(3);
                    holder.setText(R.id.tv_do,"查看反馈");
                    holder.setVisibility(R.id.tv_do,View.INVISIBLE);
                    holder.setVisibility(R.id.tv_chu,View.GONE);
                    holder.setText(R.id.tv_chu,"处理意见");
                    holder.setOnClickListener(R.id.tv_chu, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO: 2016/10/25 查看处理意见
                            Intent intent = new Intent(context, DoAdviceActivity.class);
                            intent.putExtra("orderId",item.getOwnerRecordId()+"");
                            intent.putExtra("type",100);
                            intent.putExtra("flag",0);
                            intent.putExtra("repairDesc",item.getRepairDesc());
                            context.startActivity(intent);
                        }
                    });
                    holder.setOnClickListener(R.id.tv_do, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, FeedbackActivity.class);
                            intent.putExtra("ownerRecordId",item.getOwnerRecordId());
                            intent.putExtra("ownerName",item.getOwnerName()+"");
                            intent.putExtra("ownerPhone",item.getOwnerPhone()+"");
                            intent.putExtra("address",item.getAddress()+"");
                            intent.putExtra("type",Ordertype);
                            context.startActivity(intent);
                        }
                    });
                }

                break;
        }

    }

    private void stateChange(int type,int orderId){
        DialogTools.showLoding(context,"加载","正在请求中。。。");
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type+"");
        params.put("orderId", orderId+"");
        Observable<Result> resultObservable = RetrofitManager.getInstance(1, context, Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .changeState(params)
                .map(new Func1<Result, Result>() {
                    @Override
                    public Result call(Result result) {
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Subscriber<Result> subscriber = new Subscriber<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ColpencilLogger.e("服务器错误："+e.getMessage());
            }

            @Override
            public void onNext(Result result) {
                int code = result.code;
                String message = result.message;
                switch (code){
                    case 0:
                        RxBusMsg rxBusMsg = new RxBusMsg();
                        rxBusMsg.setType(1);
                        RxBus.get().post("stateChange",rxBusMsg);
                        ToastTools.showShort(context,true,message);
                        break;
                    case 1:
                        ToastTools.showShort(context,false,message);
                        break;
                    case 3:
                        context.startActivity(new Intent(context, LoginActivity.class));
                        break;
                    default:
                        ToastTools.showShort(context, false, message);
                        break;
                }
                DialogTools.dissmiss();
            }
        };
        resultObservable.subscribe(subscriber);
    }
}
