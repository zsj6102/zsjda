package com.colpencil.propertycloud.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.View.Activitys.ApiCloudActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.ComplainDetailActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.FitmentProcessActivity2;
import com.colpencil.propertycloud.View.Activitys.CloseManager.LiveInfoActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.MenberManagerActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.RepairDetailActivity;
import com.colpencil.propertycloud.View.Activitys.CloseManager.TenementRepairsActivity;
import com.colpencil.propertycloud.View.Activitys.Home.ComplaintHistroyActivity;
import com.colpencil.propertycloud.View.Activitys.Home.FitmentHomeActivity;
import com.colpencil.propertycloud.View.Activitys.Home.LoadUriActivity;
import com.colpencil.propertycloud.View.Activitys.Home.PayFeesActivity;
import com.colpencil.propertycloud.View.Activitys.Home.RepairsHistoryActivity;
import com.colpencil.propertycloud.View.Activitys.HomeActivity;
import com.colpencil.propertycloud.View.Fragments.CloseManager.FitmentProcessFragment;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

public class MyPushReceiver extends BroadcastReceiver {

    private static final String TAG = "PushReceiver";
    private static String extra;
    private String deviceId;


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        ColpencilLogger.e("onReceive - " + intent.getAction());
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            ColpencilLogger.e("idshgwslg");
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                .getAction())) {
            ColpencilLogger.e("收到了自定义消息。消息内容是**************："
                    + bundle.getString(JPushInterface.EXTRA_EXTRA) + "deviceId=" + deviceId);
            JSONObject jo = null;
            try {
                jo = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                String logo = jo.getString("logo");
                String organizer = jo.getString("organizer");
                String type = jo.getString("type");
                String url = jo.getString("url");
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(2);
                rxBusMsg.setsType(type);
                rxBusMsg.setUrl(url);
                rxBusMsg.setLogo(logo);
                rxBusMsg.setOrganizer(organizer);
                ColpencilLogger.e("---------------开始发红包啦");
                RxBus.get().post("message", rxBusMsg);
//                EventBus.getDefault().post(rxBusMsg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            RxBusMsg rxBusMsg = new RxBusMsg();
//            rxBusMsg.setType(2);
////			rxBusMsg.setName(deviceId);
//            RxBus.get().post("message", rxBusMsg);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction())) {
            ColpencilLogger.e("收到了通知:" + extra);
            // 在这里可以做些统计，或者做些其他工作
            JSONObject jo = null;
            try {
                jo = new JSONObject(extra);
                String code = jo.getString("code");
                if (code.equals("0")) {
                    String title = jo.optString("title");
                    String content = jo.optString("content");
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(1);
                    rxBusMsg.setContent(content);
                    rxBusMsg.setTitle(title);
                    RxBus.get().post("message", rxBusMsg);
                }
               /* else if (code.equals("99")){
                    String logo = jo.getString("logo");
                    String organizer = jo.getString("organizer");
                    String type = jo.getString("type");
                    String url = jo.getString("url");
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(2);
                    rxBusMsg.setType(Integer.valueOf(type));
                    rxBusMsg.setUrl(url);
                    rxBusMsg.setLogo(logo);
                    rxBusMsg.setOrganizer(organizer);
                    RxBus.get().post("message", rxBusMsg);
                }*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {
            ColpencilLogger.e("用户点击打开了通知：" + extra);
            // 在这里可以自己写代码去定义用户点击后的行为
            // String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            // System.out.println("附加信息:" + extra);
            Intent i = new Intent(); // 自定义打开的界面
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            try {
                JSONObject jo = new JSONObject(extra);
                String code = jo.getString("code");
                String content = jo.optString("content");
                String url = jo.optString("url");
                String hseid = jo.optString("hseid");
                RxBusMsg rxBusMsg = new RxBusMsg();
                if (code.equals("0")) {
                    // 0 物业公告
                    String title = jo.optString("title");
                    i.setClass(context, HomeActivity.class);
                    JSONObject jo2 = new JSONObject(content);
                    context.startActivity(i);
                    rxBusMsg.setType(1);
                    rxBusMsg.setContent(jo2.optString("content"));
                    rxBusMsg.setTitle(title);
                    RxBus.get().post("message", rxBusMsg);
                } else if (code.equals("1")) { // 物业报修
                    JSONObject job = new JSONObject(content);
                    i.putExtra("id", job.optString("orderId"));
                    i.setClass(context, RepairDetailActivity.class);
                    context.startActivity(i);
                } else if (code.equals("2")) { // 投诉表扬
                    JSONObject job = new JSONObject(content);
                    i.putExtra("id", job.optString("orderId"));
                    i.putExtra("type", Integer.valueOf(job.optString("type")));
                    i.setClass(context, ComplainDetailActivity.class);
                    context.startActivity(i);
                } else if (code.equals("4")) { // 产生缴费账单
                    i.setClass(context, PayFeesActivity.class);
                    context.startActivity(i);
                } else if (code.equals("5")) { // 大后台给用户发了红包
                    i.setClass(context, HomeActivity.class);
                    i.putExtra("type", 2);
                    context.startActivity(i);
                } else if (code.equals("6")) { // 装修申请有商家查看联系方式
                    i.setClass(context, ApiCloudActivity.class);
                    i.putExtra("startUrl", url);
                    context.startActivity(i);
                } else if (code.equals("7")) { // 装修申请有大后台审核成单通过
                    i.setClass(context, ApiCloudActivity.class);
                    i.putExtra("startUrl", url);
                    context.startActivity(i);
                } else if (code.equals("8")) { // 物业发放钥匙通知
                    i.setClass(context, LoadUriActivity.class);
                    i.putExtra("url", "http://www.daiqijia.com/wymgr/property/opendoor/key_management.do?ownerType=" + 1);
                    i.putExtra("type", 2);
                    i.putExtra("title", "钥匙管理");
                    context.startActivity(i);
                } else if (code.equals("9")) { // 添加新房产通知
                    i.setClass(context, LiveInfoActivity.class);
                    context.startActivity(i);
                } else if (code.equals("10")) { // 添加新成员通知
                    i.setClass(context, MenberManagerActivity.class);
                    i.putExtra("hseid", Integer.valueOf(hseid));
                    context.startActivity(i);
                } else if (code.equals("11")) { // 装修进度审核通过
                    i.setClass(context, FitmentProcessActivity2.class);
                    context.startActivity(i);
                } else if (code.equals("12")) { // 装修进度装修中
                    i.setClass(context, FitmentProcessActivity2.class);
                    context.startActivity(i);
                } else if (code.equals("22")) { // 主人，您是不是忘记付款啦~快付款带我回家吧！
                    i.setClass(context, ApiCloudActivity.class);
                    i.putExtra("startUrl", url);
                    context.startActivity(i);
                } else if (code.equals("23")) { // 您购买的团购商品已成团，就等商家发货啦！点我查看
                    i.setClass(context, ApiCloudActivity.class);
                    i.putExtra("startUrl", url);
                    context.startActivity(i);
                } else if (code.equals("24")) { // 您的服务订单已有商户接单啦！请保持手机通畅，点我查看
                    i.setClass(context, ApiCloudActivity.class);
                    i.putExtra("startUrl", url);
                    context.startActivity(i);
                } else if (code.equals("25")) { // &发布单位 在派发大奖啦！又可以坐着数钱了，点我查看
                    i.setClass(context, ApiCloudActivity.class);
                    i.putExtra("startUrl", url);
                    context.startActivity(i);
                } else if (code.equals("26")) { // 开奖了！开奖了！开奖了！老司机快上车！
                    i.setClass(context, ApiCloudActivity.class);
                    i.putExtra("startUrl", url);
                    context.startActivity(i);
                } else if (code.equals("27")) { // 您已收到一张&商户名称的现金券，可以直接用于消费，点我查看~
                    i.setClass(context, ApiCloudActivity.class);
                    i.putExtra("startUrl", url);
                    context.startActivity(i);
                } else if (code.equals("99")){ // 红包界面
                    i.setClass(context, ApiCloudActivity.class);
                    i.putExtra("startUrl", url);
                    context.startActivity(i);
                }else {
                    i.setClass(context, HomeActivity.class);
                    context.startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
