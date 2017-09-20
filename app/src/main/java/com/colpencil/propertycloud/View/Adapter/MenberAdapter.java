package com.colpencil.propertycloud.View.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.MenberInfo;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.CluodApplaction;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.HashMap;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 家庭成员列表
 * @Email DramaScript@outlook.com
 * @date 2016/11/15
 */
public class MenberAdapter extends SuperAdapter<MenberInfo> {

    private Activity activity;

    private boolean isQuan;

    public void setQuan(boolean quan) {
        isQuan = quan;
    }

    public MenberAdapter(Activity context, List<MenberInfo> items, int layoutResId) {
        super(context, items, layoutResId);
        activity = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final MenberInfo item) {
        holder.setText(R.id.tv_name, item.usrnm);
//        if (item.type) {
//            int memberId = Integer.valueOf(SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).getString("memberId"));
//            if (memberId != item.usrid) {
//                holder.setVisibility(R.id.iv_del, View.VISIBLE);
//                holder.setOnClickListener(R.id.iv_del, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showDialog(item.hseid + "", item.usrid + "");
//                    }
//                });
//            } else {
//                holder.setVisibility(R.id.iv_del, View.INVISIBLE);
//            }
//        } else {
//            holder.setVisibility(R.id.iv_del, View.INVISIBLE);
//        }

        int memberId = Integer.valueOf(SharedPreferencesUtil.getInstance(CluodApplaction.getInstance()).getString("memberId"));
        if (isQuan){
            if (memberId != item.usrid) {
                if (item.user_type==1){ // 产权人
                    holder.setVisibility(R.id.iv_del, View.GONE);
                }else {
                    holder.setVisibility(R.id.iv_del, View.VISIBLE);
                    holder.setOnClickListener(R.id.iv_del, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog(item.hseid + "", item.usrid + "");
                        }
                    });
                }

            } else {
                if (item.user_type==1){
                    holder.setVisibility(R.id.iv_del, View.GONE);
                }else {
                    holder.setVisibility(R.id.iv_del, View.VISIBLE);
                }
            }
        }else {
            holder.setVisibility(R.id.iv_del, View.GONE);
        }
    }

    /**
     * 注销游客
     */
    private void modFamilyMember(String hseId, String usrId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("hseid", hseId);
        map.put("usrId", usrId);
        RetrofitManager.getInstance(1, activity, HostUrl.BASE_URL)
                .createApi(HostApi.class)
                .modFamilyMember(map)
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
                        ColpencilLogger.e("服务器错误：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultInfo resultInfo) {
                        int code = resultInfo.code;
                        String message = resultInfo.message;
                        switch (code) {
                            case 0:
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("add", rxBusMsg);
                                break;
                            case 1:
                                ToastTools.showShort(activity, false, message);
                                break;
                            case 3:
                                activity.startActivity(new Intent(activity, LoginActivity.class));
                                break;
                        }
                    }
                });
    }

    public void showDialog(final String hseId, final String usrId) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_remove_member, null);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(view);
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics();// 获取屏幕尺寸
        lp.width = (int) (d.widthPixels * 0.75); // 宽度为屏幕80%
        lp.gravity = Gravity.CENTER; // 中央居中
        window.setAttributes(lp);
        TextView tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
        TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modFamilyMember(hseId, usrId);
                dialog.dismiss();
            }
        });
    }

}
