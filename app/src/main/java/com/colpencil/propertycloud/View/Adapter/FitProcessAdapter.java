package com.colpencil.propertycloud.View.Adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Api.HostApi;
import com.colpencil.propertycloud.Bean.FitProcess;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Home.AdviceListActivity;
import com.colpencil.propertycloud.View.Activitys.Home.LoadUriActivity;
import com.colpencil.propertycloud.View.Activitys.Home.PaySureActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DialogTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.yinghe.whiteboardlib.bean.Image;

import java.util.HashMap;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 汪 亮
 * @Description: 装修进度的适配器
 * @Email DramaScript@outlook.com
 * @date 2016/12/20
 */
public class FitProcessAdapter extends BaseAdapter {

    private List<FitProcess> fitProcessList;

    private Activity activity;
    private Intent intent;

    public FitProcessAdapter(List<FitProcess> fitProcessList, Activity activity) {
        this.fitProcessList = fitProcessList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return fitProcessList.size() - 1;
    }

    @Override
    public Object getItem(int position) {
        return fitProcessList.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position - 1;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(activity, R.layout.item_fitment_group, null);
            viewHolder.gr_vv1 = convertView.findViewById(R.id.gr_vv1);
            viewHolder.gr_vv2 = convertView.findViewById(R.id.gr_vv2);
            viewHolder.iv_yuan = (ImageView) convertView.findViewById(R.id.iv_yuan);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_reson = (TextView) convertView.findViewById(R.id.tv_reson);
            viewHolder.tv_comit = (TextView) convertView.findViewById(R.id.tv_comit);
            viewHolder.tv_clear = (TextView) convertView.findViewById(R.id.tv_clear);
            viewHolder.tv_pay1 = (TextView) convertView.findViewById(R.id.tv_pay1);
            viewHolder.tv_pay2 = (TextView) convertView.findViewById(R.id.tv_pay2);
            viewHolder.tv_pay_name1 = (TextView) convertView.findViewById(R.id.tv_pay_name1);
            viewHolder.tv_pay_name2 = (TextView) convertView.findViewById(R.id.tv_pay_name2);
            viewHolder.iv_check1 = (ImageView) convertView.findViewById(R.id.iv_check1);
            viewHolder.iv_check2 = (ImageView) convertView.findViewById(R.id.iv_check2);
            viewHolder.ll_reson2 = (LinearLayout) convertView.findViewById(R.id.ll_reson2);
            viewHolder.ll_reson = (LinearLayout) convertView.findViewById(R.id.ll_reson);
            viewHolder.ll_btn = (LinearLayout) convertView.findViewById(R.id.ll_btn);
            viewHolder.ll_pay = (LinearLayout) convertView.findViewById(R.id.ll_pay);
            viewHolder.rl1 = (RelativeLayout) convertView.findViewById(R.id.rl1);
            viewHolder.rl2 = (RelativeLayout) convertView.findViewById(R.id.rl2);
            viewHolder.ll_wu = (LinearLayout) convertView.findViewById(R.id.ll_wu);
            viewHolder.tv_yijian = (TextView) convertView.findViewById(R.id.tv_yijian);
            viewHolder.tv_jian = (TextView) convertView.findViewById(R.id.tv_jian);
            viewHolder.tv_li = (TextView) convertView.findViewById(R.id.tv_li);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final FitProcess fitProcess = fitProcessList.get(position + 1);
        int status = fitProcess.status;
        int wholeprogress = fitProcess.wholeprogress;
        ColpencilLogger.e("status=" + status + ",position=" + position);
        viewHolder.tv_name.setText(fitProcess.group);

        if (status == 1) {
            viewHolder.iv_yuan.setImageDrawable(activity.getResources().getDrawable(R.drawable.circle_green));
            viewHolder.gr_vv1.setBackgroundColor(activity.getResources().getColor(R.color.main_blue));
            if (wholeprogress >= position + 1) {
                viewHolder.gr_vv2.setBackgroundColor(activity.getResources().getColor(R.color.main_blue));
            } else {
                viewHolder.gr_vv2.setBackgroundColor(activity.getResources().getColor(R.color.main_gre));
            }
        } else {
            if (wholeprogress >= position) {
                viewHolder.gr_vv1.setBackgroundColor(activity.getResources().getColor(R.color.main_blue));
            } else {
                if (wholeprogress == 1) {
                    if (position == 1) {
                        viewHolder.gr_vv1.setBackgroundColor(activity.getResources().getColor(R.color.main_blue));
                    } else {
                        viewHolder.gr_vv1.setBackgroundColor(activity.getResources().getColor(R.color.main_gre));
                    }
                } else {
                    viewHolder.gr_vv1.setBackgroundColor(activity.getResources().getColor(R.color.main_gre));
                }
            }
            viewHolder.iv_yuan.setImageDrawable(activity.getResources().getDrawable(R.drawable.circle_gre));
        }
        switch (wholeprogress) {
            case 1:
                if (position==1){
                    viewHolder.ll_reson2.setVisibility(View.GONE);
                    viewHolder.ll_reson.setVisibility(View.VISIBLE);
                    viewHolder.ll_pay.setVisibility(View.VISIBLE);
                    viewHolder.iv_check1.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_check));
                    viewHolder.tv_pay1.setVisibility(View.GONE);
                    viewHolder.iv_check2.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_check));
                    viewHolder.tv_pay2.setVisibility(View.GONE);
                }else {
                    viewHolder.ll_reson2.setVisibility(View.GONE);
                    viewHolder.ll_reson.setVisibility(View.GONE);
                    viewHolder.ll_pay.setVisibility(View.GONE);
                }
                if (position == 0) {
                    if ("待提交".equals(fitProcess.group)) {
                        viewHolder.ll_reson.setVisibility(View.GONE);
                        viewHolder.ll_reson2.setVisibility(View.GONE);
                    } else if ("待审核".equals(fitProcess.group)) {
                        viewHolder.ll_reson.setVisibility(View.VISIBLE);
                        viewHolder.ll_reson2.setVisibility(View.GONE);
                    } else if ("已审核".equals(fitProcess.group)) {
                        viewHolder.ll_reson.setVisibility(View.GONE);
                    } else if ("已拒绝".equals(fitProcess.group)) {
                        viewHolder.ll_reson2.setVisibility(View.VISIBLE);
                        viewHolder.tv_reson.setText(fitProcessList.get(1).remark);
                        viewHolder.ll_reson.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.ll_reson.setVisibility(View.GONE);
                    }
                } else {
                    viewHolder.ll_reson.setVisibility(View.GONE);
                }
                viewHolder.tv_comit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogTools.showLoding(activity,"温馨提示","正在提交。。。");
                        confirmDecortApprove(fitProcessList.get(1).aprovid);
                    }
                });
                viewHolder.tv_clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogTools.showLoding(activity,"温馨提示","正在删除。。。");
                        disFitment(fitProcessList.get(1).aprovid);
                    }
                });
                break;
            case 2:
                if (position == 1) {
                    viewHolder.ll_reson.setVisibility(View.VISIBLE);
                    viewHolder.ll_pay.setVisibility(View.VISIBLE);
                    viewHolder.ll_reson2.setVisibility(View.GONE);
                    //  去支付
                    if (fitProcess.materList.size() == 0) {
                        viewHolder.ll_pay.setVisibility(View.GONE);
                    } else {
                        if (fitProcess.materList.size() == 1) {
                            viewHolder.tv_pay2.setVisibility(View.GONE);
                            viewHolder.tv_pay_name1.setText(fitProcess.materList.get(0).name);
                            if (fitProcess.materList.get(0).status == 0) {
                                viewHolder.iv_check1.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_uncheck));
                                viewHolder.tv_pay1.setVisibility(View.VISIBLE);
                                viewHolder.tv_pay1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        intent = new Intent(activity, PaySureActivity.class);
                                        intent.putExtra("type", 1);
                                        intent.putExtra("billIds", fitProcess.materList.get(0).sn + "");
                                        activity.startActivity(intent);
                                    }
                                });
                            } else {
                                viewHolder.iv_check1.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_check));
                                viewHolder.tv_pay1.setVisibility(View.GONE);
                            }

                        } else {
                            viewHolder.tv_pay_name1.setText(fitProcess.materList.get(0).name);
                            viewHolder.tv_pay_name2.setText(fitProcess.materList.get(1).name);
                            if (fitProcess.materList.get(0).status == 0) {
                                viewHolder.iv_check1.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_uncheck));
                                viewHolder.tv_pay1.setVisibility(View.VISIBLE);
                                viewHolder.tv_pay1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        intent = new Intent(activity, PaySureActivity.class);
                                        intent.putExtra("type", 1);
                                        intent.putExtra("billIds", fitProcess.materList.get(0).sn + "");
                                        activity.startActivity(intent);
                                    }
                                });
                            } else {
                                viewHolder.iv_check1.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_check));
                                viewHolder.tv_pay1.setVisibility(View.GONE);
                            }
                            if (fitProcess.materList.get(1).status == 0) {
                                viewHolder.iv_check2.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_uncheck));
                                viewHolder.tv_pay2.setVisibility(View.VISIBLE);
                                viewHolder.tv_pay2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        intent = new Intent(activity, PaySureActivity.class);
                                        intent.putExtra("type", 1);
                                        intent.putExtra("billIds", fitProcess.materList.get(1).sn + "");
                                        activity.startActivity(intent);
                                    }
                                });
                            } else {
                                viewHolder.iv_check2.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_check));
                                viewHolder.tv_pay2.setVisibility(View.GONE);
                            }
                        }
                    }
                } else {
                    viewHolder.ll_reson.setVisibility(View.GONE);
                    viewHolder.ll_pay.setVisibility(View.GONE);
                    viewHolder.ll_reson2.setVisibility(View.GONE);
                }
                break;
            case 3:
                viewHolder.ll_reson2.setVisibility(View.GONE);
                if (position==1){
                    viewHolder.ll_reson.setVisibility(View.VISIBLE);
                    viewHolder.ll_pay.setVisibility(View.VISIBLE);
                    viewHolder.iv_check1.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_check));
                    viewHolder.tv_pay1.setVisibility(View.GONE);
                    viewHolder.iv_check2.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_check));
                    viewHolder.tv_pay2.setVisibility(View.GONE);
                }else {
                    viewHolder.ll_reson.setVisibility(View.GONE);
                    viewHolder.ll_pay.setVisibility(View.GONE);
                }

                break;
            case 4:
                viewHolder.ll_reson2.setVisibility(View.GONE);
                if (position==1){
                    viewHolder.ll_reson.setVisibility(View.VISIBLE);
                    viewHolder.ll_pay.setVisibility(View.VISIBLE);
                    viewHolder.iv_check1.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_check));
                    viewHolder.tv_pay1.setVisibility(View.GONE);
                    viewHolder.iv_check2.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_check));
                    viewHolder.tv_pay2.setVisibility(View.GONE);
                }else {
                    viewHolder.ll_reson.setVisibility(View.GONE);
                    viewHolder.ll_pay.setVisibility(View.GONE);
                }
                if (position == 3) {
                    viewHolder.ll_wu.setVisibility(View.VISIBLE);
                    viewHolder.tv_yijian.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(activity, AdviceListActivity.class);
                            intent.putExtra("aprovid", fitProcess.aprovid);
                            activity.startActivity(intent);
                        }
                    });
                    viewHolder.tv_jian.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(activity, LoadUriActivity.class);
                            intent.putExtra("url", fitProcess.url);
                            intent.putExtra("title", "监理日记");
                            activity.startActivity(intent);
                        }
                    });
                    viewHolder.tv_li.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            applaLeave(fitProcess.decorateid + "");
                        }
                    });
                } else {
                    viewHolder.ll_wu.setVisibility(View.GONE);
                }
                break;
            case 5:
                viewHolder.ll_wu.setVisibility(View.GONE);
                viewHolder.ll_reson2.setVisibility(View.GONE);
                if (position==1){
                    viewHolder.ll_reson.setVisibility(View.VISIBLE);
                    viewHolder.ll_pay.setVisibility(View.VISIBLE);
                    viewHolder.iv_check1.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_check));
                    viewHolder.tv_pay1.setVisibility(View.GONE);
                    viewHolder.iv_check2.setImageDrawable(activity.getResources().getDrawable(R.mipmap.fit_check));
                    viewHolder.tv_pay2.setVisibility(View.GONE);
                }else {
                    viewHolder.ll_reson.setVisibility(View.GONE);
                    viewHolder.ll_pay.setVisibility(View.GONE);
                }
                break;
            default:
                viewHolder.ll_wu.setVisibility(View.GONE);
                viewHolder.ll_reson2.setVisibility(View.GONE);
                viewHolder.ll_reson.setVisibility(View.GONE);
                viewHolder.ll_pay.setVisibility(View.GONE);
                break;

        }

        return convertView;
    }

    /**
     * 申请离场
     */
    private void applaLeave(String decorate_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("decorate_id", decorate_id);
        RetrofitManager.getInstance(1, activity, HostUrl.BASE_URL)
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
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("update", rxBusMsg);
                                ToastTools.showShort(activity, true, "申请成功！");
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

    /**
     * 确认提交申请
     *
     * @param aprovid
     */
    private void confirmDecortApprove(int aprovid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("approveid", aprovid + "");
        RetrofitManager.getInstance(1, activity, HostUrl.BASE_URL)
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
                        DialogTools.dissmiss();
                        switch (resultInfo.code) {
                            case 0:
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("update", rxBusMsg);
                                ToastTools.showShort(activity, true, "提交成功！");
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

    /**
     * 取消装修
     *
     * @param aprovid
     */
    private void disFitment(int aprovid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("approveid", aprovid + "");
        RetrofitManager.getInstance(1, activity, HostUrl.BASE_URL)
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
                        DialogTools.dissmiss();
                        switch (resultInfo.code) {
                            case 0:
                                RxBusMsg rxBusMsg = new RxBusMsg();
                                rxBusMsg.setType(0);
                                RxBus.get().post("cg", rxBusMsg);
                                ToastTools.showShort(activity, true, "取消成功！");
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

    class ViewHolder {
        public View gr_vv1;
        public View gr_vv2;
        public ImageView iv_yuan;
        public TextView tv_name;
        public TextView tv_reson;
        public TextView tv_comit;
        public TextView tv_clear;
        public TextView tv_pay1;
        public TextView tv_pay2;
        public LinearLayout ll_reson2;
        public LinearLayout ll_reson;
        public LinearLayout ll_btn;
        public LinearLayout ll_pay;
        public RelativeLayout rl1;
        public RelativeLayout rl2;

        public ImageView iv_check1;
        public ImageView iv_check2;
        public TextView tv_pay_name1;
        public TextView tv_pay_name2;

        public LinearLayout ll_wu;
        public TextView tv_yijian;
        public TextView tv_jian;
        public TextView tv_li;
    }
}
