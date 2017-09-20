package com.colpencil.tenement.View.Adpaters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.colpencil.tenement.Api.HostApi;
import com.colpencil.tenement.Bean.Advice;
import com.colpencil.tenement.Bean.Result;
import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.Config.StringConfig;
import com.colpencil.tenement.Config.Urlconfig;
import com.colpencil.tenement.R;
import com.colpencil.tenement.Ui.ExpandableTextView;
import com.colpencil.tenement.View.Activitys.OnlineTalk.VoiceCallActivity;
import com.colpencil.tenement.View.Activitys.Welcome.LoginActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.AssignRepairActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.DoAdviceActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.FeedbackActivity;
import com.easemob.chat.EMChatManager;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 投诉建议
 * @Email DramaScript@outlook.com
 * @date 2016/8/26
 */
public class AdviceAdapter extends SuperAdapter<Advice> {

    private Activity mContext;

    private int type;

    private int Ordertype;

    public AdviceAdapter(Activity context, List<Advice> items, int layoutResId, int flag, int Ordertype) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.type = flag;
        this.Ordertype = Ordertype;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Advice item) {
        holder.setText(R.id.tv_time, item.date);
        holder.setText(R.id.tv_date, item.sugCode);  // 投诉编号
        holder.setText(R.id.tv_server_id, item.orderId+"");
        if(type == 0){
            holder.setText(R.id.tv_order_name, "未指派");
        } else {
            holder.setText(R.id.tv_order_name, item.appoint);
        }

        holder.setText(R.id.tv_fen, item.classify);
        holder.setText(R.id.own_describe, item.describe);
        holder.setText(R.id.tv_name, item.name);
        holder.setText(R.id.tv_tou_name, item.phone);


        holder.setOnClickListener(R.id.ll_call, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(item.phone)) {
                    ToastTools.showShort(mContext, "拨打的电话有误！");
                    return;
                }
                MaterialDialog show = new MaterialDialog.Builder(mContext)
                        .title("温馨提示")
                        .content("是否拨打该电话？")
                        .positiveText(R.string.online_yes)
                        .negativeText(R.string.online_no)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                Uri data = Uri.parse("tel:" + item.phone);
                                intent.setData(data);
                                mContext.startActivity(intent);
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
        if (TextUtils.isEmpty(item.address)) {
            holder.setText(R.id.tv_adrr, "暂无");
        } else {
            holder.setText(R.id.tv_adrr, item.address);
        }
        TextView tv_state = holder.getView(R.id.tv_state);
        switch (type) {
            case 0: //待指派
                holder.setVisibility(R.id.rl_chu, View.GONE);
                holder.setVisibility(R.id.rl_ping, View.GONE);
                holder.setText(R.id.tv_state, "去指派");
                holder.setVisibility(R.id.tv_see, View.GONE);
                holder.setOnClickListener(R.id.tv_state, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 待指派
                        Intent intent = new Intent(mContext, AssignRepairActivity.class);
                        intent.putExtra("orderId",item.orderId);
                        intent.putExtra("communityId", item.communityId);
                        intent.putExtra(StringConfig.TYPE, 1);
                        mContext.startActivity(intent);
                    }
                });
                break;
            case 1: // 待处理
                holder.setVisibility(R.id.rl_chu, View.GONE);
                holder.setVisibility(R.id.rl_ping, View.GONE);
                holder.setText(R.id.tv_state, "开始处理");
                if(SharedPreferencesUtil.getInstance(mContext).getInt(StringConfig.USERID) == item.empId){
                    holder.setBackgroundResource(R.id.tv_state,R.drawable.rect_blue);
                } else {
                    holder.setBackgroundResource(R.id.tv_state, R.drawable.rect_gre);
                }
                holder.setOnClickListener(R.id.tv_state, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(SharedPreferencesUtil.getInstance(mContext).getInt(StringConfig.USERID) == item.empId){
                            MaterialDialog show = new MaterialDialog.Builder(mContext)
                                    .title("温馨提示")
                                    .content("请您确认已到投诉服务地点并准备开始进行处理问题")
                                    .positiveText(R.string.online_yes)
                                    .negativeText(R.string.online_no)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            stateChange(2, item.orderId+"");
                                        }
                                    })
                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        }
                                    })
                                    .show();

                        } else {
                            ToastTools.showShort(mContext, false, "您没有此记录的处理权限");
                        }
                    }
                });
                holder.setVisibility(R.id.tv_see, View.GONE);
                break;
            case 2: // 处理中
                holder.setVisibility(R.id.rl_chu, View.GONE);
                holder.setVisibility(R.id.rl_ping, View.GONE);
                holder.setText(R.id.tv_state, "已完成");
                if(SharedPreferencesUtil.getInstance(mContext).getInt(StringConfig.USERID) == item.empId){
                    holder.setBackgroundResource(R.id.tv_state, R.drawable.rect_blue);
                } else {
                    holder.setBackgroundResource(R.id.tv_state, R.drawable.rect_gre);
                    holder.setTextColor(R.id.tv_state, mContext.getResources().getColor(R.color.white));
                }
                holder.setOnClickListener(R.id.tv_state, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(SharedPreferencesUtil.getInstance(mContext).getInt(StringConfig.USERID) == item.empId){
                            Intent intent = new Intent(mContext, DoAdviceActivity.class);
                            intent.putExtra("orderId", item.orderId + "");
                            intent.putExtra("type", 3);
                            intent.putExtra("flag", 1);
                            mContext.startActivity(intent);
                        } else {
                            ToastTools.showShort(mContext, false, "您没有此记录的处理权限");
                        }
                    }
                });
                holder.setVisibility(R.id.tv_see, View.GONE);
                break;
            case 3: // 已完成
                holder.setVisibility(R.id.tv_state, View.GONE);
                ExpandableTextView tv_chu = holder.getView(R.id.expand_text_view);
                ExpandableTextView tv_fan = holder.getView(R.id.expand_text_view2);

                if (TextUtils.isEmpty(item.serviceDesc)) {
                    holder.setVisibility(R.id.rl_chu, View.GONE);
                } else {
                    holder.setVisibility(R.id.rl_chu, View.VISIBLE);
                    tv_chu.setText(item.serviceDesc);
                }
                if (TextUtils.isEmpty(item.feedback)) {
                    holder.setVisibility(R.id.rl_ping, View.GONE);
                } else {
                    holder.setVisibility(R.id.rl_ping, View.VISIBLE);
                    tv_fan.setText(item.feedback);
                }


                if (item.hasFeedback == 0) {
                    holder.setVisibility(R.id.tv_see, View.GONE);
                    holder.setVisibility(R.id.tv_state, View.GONE);
                    holder.setVisibility(R.id.rl_btn, View.GONE);
                    holder.setText(R.id.tv_state, "处理意见");
                    holder.setOnClickListener(R.id.tv_state, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, DoAdviceActivity.class);
                            intent.putExtra("orderId", item.orderId + "");
                            intent.putExtra("type", 100);
                            intent.putExtra("flag", 1);
                            intent.putExtra("repairDesc", item.serviceDesc);
                            mContext.startActivity(intent);
                        }
                    });
                } else {
                    holder.setVisibility(R.id.tv_see, View.GONE);
                    holder.setText(R.id.tv_see, "查看反馈");
                    holder.setOnClickListener(R.id.tv_see, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, FeedbackActivity.class);
                            intent.putExtra("ownerRecordId", item.orderId);
                            intent.putExtra("ownerName", item.name + "");
                            intent.putExtra("ownerPhone", item.phone + "");
                            intent.putExtra("address", item.address + "");
                            intent.putExtra("type", Ordertype);
                            mContext.startActivity(intent);
                        }
                    });
                }


                break;
        }
    }

    private void stateChange(int type, String orderId) {
        DialogTools.showLoding(mContext, "加载", "正在请求中。。。");
        HashMap<String, String> params = new HashMap<>();
        params.put("status", type + "");
        params.put("orderId", orderId);
        Observable<Result> resultObservable = RetrofitManager.getInstance(1, mContext, Urlconfig.BASEURL)
                .createApi(HostApi.class)
                .changeComplaintState(params)
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
                ColpencilLogger.e("服务器错误：" + e.getMessage());
            }

            @Override
            public void onNext(Result result) {
                int code = result.code;
                String message = result.message;
                switch (code) {
                    case 0:
                        RxBusMsg rxBusMsg = new RxBusMsg();
                        rxBusMsg.setType(1);
                        RxBus.get().post("stateChange", rxBusMsg);
                        ToastTools.showShort(mContext, true, message);
                        break;
                    case 1:
                        ToastTools.showShort(mContext, false, message);
                        break;
                    case 3:
                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        break;
                    default:
                        ToastTools.showShort(mContext, false, message);
                        break;
                }
                DialogTools.dissmiss();
            }
        };
        resultObservable.subscribe(subscriber);
    }
}
