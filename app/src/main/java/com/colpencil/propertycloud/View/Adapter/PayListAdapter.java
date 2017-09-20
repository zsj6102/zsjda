package com.colpencil.propertycloud.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.PayList;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Activitys.Home.LoadUriActivity;
import com.colpencil.propertycloud.View.Activitys.Home.PaySureActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * @author 汪 亮
 * @Description: 缴费列表
 * @Email DramaScript@outlook.com
 * @date 2016/9/6
 */
public class PayListAdapter extends SuperAdapter<PayList> {

    private Context context;
    private int payItemsId;
    private int flag;

    public PayListAdapter(Context context, List<PayList> items, int layoutResId, int payItemsId,int flag) {
        super(context, items, layoutResId);
        this.context = context;
        this.payItemsId = payItemsId;
        this.flag = flag;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final PayList item) {
        TextView tv_yuan_pay = holder.getView(R.id.tv_yuan_pay);
        tv_yuan_pay.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if (item.getPaystatus() == 0) {// 待缴费的状态

            if (flag==8){ // 押金
                holder.setVisibility(R.id.tv_yuan_pay,View.GONE);
                holder.setVisibility(R.id.tv_last,View.GONE);
                holder.setVisibility(R.id.tv_the,View.GONE);
                holder.setVisibility(R.id.tv_dan,View.GONE);
                holder.setText(R.id.tv_time,"押金："+ item.getDeposit());
                holder.setText(R.id.tv_name,"其他账单："+ item.getRelated_costs());
                holder.setText(R.id.tv_end,"费用说明：" + item.getRemark());
                holder.setVisibility(R.id.ll_call,View.GONE);
            }else {// 其他
                holder.setVisibility(R.id.tv_yuan_pay,View.VISIBLE);
                holder.setVisibility(R.id.tv_last,View.VISIBLE);
                holder.setVisibility(R.id.tv_the,View.VISIBLE);
                holder.setVisibility(R.id.tv_dan,View.VISIBLE);
                holder.setText(R.id.tv_time,"缴费月度："+ item.getPayment());
                holder.setText(R.id.tv_dan,"缴费房间："+ item.getPayer());
                holder.setText(R.id.tv_phone,item.getTel());
                holder.setVisibility(R.id.ll_call,View.VISIBLE);
                holder.setOnClickListener(R.id.ll_call, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(item.getTel())){
                            ToastTools.showShort(context,"拨打的电话有误！");
                            return;
                        }
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + item.getTel());
                        intent.setData(data);
                        context.startActivity(intent);
                    }
                });
                holder.setText(R.id.tv_name,"业主姓名："+ item.getOwnernm());
            }
            holder.setVisibility(R.id.tv_ad,View.VISIBLE);
            holder.setVisibility(R.id.tv_pay,View.VISIBLE);
            holder.setVisibility(R.id.tv_histroy,View.GONE);
            holder.setVisibility(R.id.ll_pay,View.GONE);
            holder.setVisibility(R.id.tv_pay_name,View.VISIBLE);
            holder.setVisibility(R.id.ll_unpay,View.VISIBLE);
            holder.setVisibility(R.id.tv_name,View.VISIBLE);
            holder.setText(R.id.tv_unpay_money,item.getBillamout()+""); // 代缴金额
            holder.setText(R.id.tv_yuan_pay,item.getOriginal_bill_amount()+""); // 原价
            holder.setText(R.id.tv_pay_name,item.getPayitemsnm());
            holder.setOnClickListener(R.id.tv_pay, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  去缴费
                    Intent intent = new Intent(context, PaySureActivity.class);
                    intent.putExtra("billIds", item.getBillid() + "");
                    context.startActivity(intent);
                }
            });
            holder.setText(R.id.tv_ad,item.getAdvDescribe()+""); // 原价
            holder.setOnClickListener(R.id.tv_ad, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoadUriActivity.class);
                    intent.putExtra("url",item.getAdvUrl());
                    intent.putExtra("title", "");
                    context.startActivity(intent);
                }
            });


        } else {// 已经缴费的状态

            if (flag==8){ // 押金
                holder.setVisibility(R.id.tv_pay_name,View.VISIBLE);
                holder.setVisibility(R.id.tv_histroy,View.GONE);
                holder.setVisibility(R.id.ll_unpay,View.VISIBLE);
                holder.setText(R.id.tv_ya_pay,"已缴金额（元）");
                holder.setVisibility(R.id.tv_yuan_pay,View.GONE);
                holder.setVisibility(R.id.tv_pay,View.GONE);
                holder.setVisibility(R.id.tv_ad,View.GONE);
                holder.setVisibility(R.id.ll_pay,View.GONE);
                holder.setVisibility(R.id.tv_last,View.GONE);
                holder.setVisibility(R.id.tv_the,View.GONE);
                holder.setVisibility(R.id.tv_dan,View.GONE);
                holder.setText(R.id.tv_pay_name,item.getPayitemsnm());
                if (item.getPaystatus() == 1){ // 待退款
                    holder.setVisibility(R.id.tv_name,View.GONE);
                    holder.setText(R.id.tv_time,"预期退回时间："+ item.getReturn_time());
                    holder.setText(R.id.tv_end,"当前状态：待退回" );
                    holder.setVisibility(R.id.ll_call,View.GONE);
                }else if (item.getPaystatus() == 3){ // 已退款
                    holder.setVisibility(R.id.tv_name,View.VISIBLE);
                    holder.setText(R.id.tv_time,"预期退回时间："+ item.getReturn_time());
                    holder.setText(R.id.tv_name,"当前状态：已退回");
                    holder.setText(R.id.tv_end,"退回方式：线上支付退回账户余额");
                    holder.setVisibility(R.id.ll_call,View.GONE);
                }

            }else {// 其他
                holder.setVisibility(R.id.tv_pay_name,View.GONE);
                holder.setVisibility(R.id.tv_histroy,View.VISIBLE);
                holder.setVisibility(R.id.ll_unpay,View.GONE);
                holder.setVisibility(R.id.ll_pay,View.VISIBLE);
                holder.setVisibility(R.id.tv_name,View.GONE);
                holder.setVisibility(R.id.tv_last,View.VISIBLE);
                holder.setVisibility(R.id.tv_the,View.VISIBLE);
                holder.setVisibility(R.id.tv_dan,View.VISIBLE);
                holder.setText(R.id.tv_histroy,item.getCreate_month() + "月份缴费记录");
                holder.setText(R.id.tv_his_pay, item.getPayamount() + "");
                holder.setText(R.id.tv_dan,"支付方式："+item.getPaytype());
                holder.setText(R.id.tv_end,"缴费人："+item.getPayernm());
                holder.setVisibility(R.id.ll_call,View.GONE);
            }
        }

        if (payItemsId == 1) {//水费
            holder.setVisibility(R.id.tv_last, View.VISIBLE);
            holder.setVisibility(R.id.tv_the, View.VISIBLE);
            holder.setText(R.id.tv_last, "上月见抄：" + item.getLastrecord() + "吨");
            holder.setText(R.id.tv_the, "本月见抄：" + item.getCurrecord() + "吨");
        } else if (payItemsId == 2) {// 电费
            holder.setVisibility(R.id.tv_last, View.VISIBLE);
            holder.setVisibility(R.id.tv_the, View.VISIBLE);
            holder.setText(R.id.tv_last, "上月见抄：" + item.getLastrecord() + "度");
            holder.setText(R.id.tv_the, "本月见抄：" + item.getCurrecord() + "度");
        } else {
            holder.setVisibility(R.id.tv_last, View.GONE);
            holder.setVisibility(R.id.tv_the, View.GONE);
        }

    }
}
