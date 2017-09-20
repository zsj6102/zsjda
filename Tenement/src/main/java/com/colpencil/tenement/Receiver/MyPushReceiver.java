package com.colpencil.tenement.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.colpencil.tenement.Bean.RxBusMsg;
import com.colpencil.tenement.View.Activitys.HomeActivity;
import com.colpencil.tenement.View.Activitys.MainActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.AdviceActivity;
import com.colpencil.tenement.View.Activitys.Workbeach.OwnerRepairActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

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
		ColpencilLogger.e( "onReceive - " + intent.getAction());
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			ColpencilLogger.e("idshgwslg");
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {

			JSONObject jo = null;
			try {
				jo = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
				deviceId = jo.getString("deviceId");
			} catch (JSONException e) {
				e.printStackTrace();
			}
            ColpencilLogger.e("收到了自定义消息。消息内容是**************："
                    + bundle.getString(JPushInterface.EXTRA_EXTRA)+"deviceId="+deviceId);
            RxBusMsg rxBusMsg = new RxBusMsg();
            rxBusMsg.setType(2);
			rxBusMsg.setName(deviceId);
            RxBus.get().post("message",rxBusMsg);
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			ColpencilLogger.e("收到了通知");
			// 在这里可以做些统计，或者做些其他工作
            JSONObject jo = null;
            try {
                jo = new JSONObject(extra);
                String code = jo.getString("code");
                if (code.equals("0")){
                    String title = jo.optString("title");
                    String content = jo.optString("content");
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(1);
                    rxBusMsg.setContent(content);
                    rxBusMsg.setTitle(title);
                    RxBus.get().post("message",rxBusMsg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			ColpencilLogger.e("用户点击打开了通知："+extra);
			// 在这里可以自己写代码去定义用户点击后的行为
			// String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
			// System.out.println("附加信息:" + extra);
			Intent i = new Intent(); // 自定义打开的界面
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			try {
				JSONObject jo = new JSONObject(extra);
				String code = jo.getString("code");
				if (code.equals("0")){
					// 0 物业公告
                    String title = jo.optString("title");
                    String content = jo.optString("content");
                    i.setClass(context, MainActivity.class);
					context.startActivity(i);
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(1);
                    rxBusMsg.setContent(content);
                    rxBusMsg.setTitle(title);
                    RxBus.get().post("message",rxBusMsg);
				}else if (code.equals("1")){
					//  1 公共报修
					i.setClass(context, OwnerRepairActivity.class);
					context.startActivity(i);
				}else if (code.equals("2")){
					//  2 投诉建议
					i.setClass(context, AdviceActivity.class);
					context.startActivity(i);
				}else if (code.equals("3")){
					//  3 今日任务
					RxBusMsg rxBusMsg = new RxBusMsg();
					rxBusMsg.setType(3);
					RxBus.get().post("message",rxBusMsg);
					i.setClass(context, MainActivity.class);
					context.startActivity(i);
				}else if (code.equals("offline")){
//                    RxBusMsg rxBusMsg = new RxBusMsg();
//                    rxBusMsg.setType(2);
//                    RxBus.get().post("message",rxBusMsg);
                }
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

}
