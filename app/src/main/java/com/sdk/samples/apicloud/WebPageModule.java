package com.sdk.samples.apicloud;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import android.view.WindowManager;
import android.widget.TextView;

import com.colpencil.propertycloud.View.Activitys.CloseManager.ChangePayActivity2;
import com.colpencil.propertycloud.View.Activitys.CloseManager.ForgetPayActivity;
import com.colpencil.propertycloud.View.Activitys.Welcome.LoginActivity;
import com.colpencil.propertycloud.View.Activitys.kindergarten.BabyMineLiveActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.uzmap.pkg.openapi.ExternalActivity;
import com.uzmap.pkg.openapi.Html5EventListener;
import com.uzmap.pkg.openapi.WebViewProvider;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;


/**
 * 
 * 使用SuperWebview的Activity，需继承自ExternalActivity
 * @author dexing.li
 *
 */
public class WebPageModule extends ExternalActivity {

	private TextView mProgress;

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
	}
	public static final int RequestCode_Login=1;
	public static final int RequestCode_InitPayPassword=2;
	public static final int RequestCode_Video = 3;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mProgress = new TextView(this);
		mProgress.setTextColor(0xFFFF0000);
		mProgress.setTextSize(20);
		mProgress.setVisibility(View.GONE);
		addContentView(mProgress, new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
		bindSomething();
	}
	private void bindSomething(){
		addHtml5EventListener(new Html5EventListener("login") {
			@Override
			public void onReceive(WebViewProvider provider, Object extra) {
				runOnUiThread(new Runnable() {
					public void run() {
						Intent intent = new Intent(WebPageModule.this, LoginActivity.class);
						intent.putExtra(LoginActivity.StartType,LoginActivity.StartType_Requestlogin);
						startActivityForResult(intent, RequestCode_Login);
					}
				});
			}
		});
		addHtml5EventListener(new Html5EventListener("camera_view") {
			@Override
			public void onReceive(WebViewProvider webViewProvider, final Object extra) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						String auth_code = null;
						String channel_id = null;
						String server_ip = null;
						String app_id = null;
						String port = null;
						Intent intent = new Intent(WebPageModule.this, BabyMineLiveActivity.class);
						JSONObject object = (JSONObject) extra;
						try {
							auth_code = object.getString("auth_code");
							channel_id = object.getString("channelID");
							server_ip = object.getString("serverIP");
							app_id = object.getString("appID");
							port = object.getString("port");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						intent.putExtra("auth_code", auth_code);
						intent.putExtra("channelID", channel_id);
						intent.putExtra("serverIP", server_ip);
						intent.putExtra("appID", app_id);
						intent.putExtra("port", port);
						startActivityForResult(intent, RequestCode_Video);
					}
				});
			}
		});
		addHtml5EventListener(new Html5EventListener("initPayPassword") {
			@Override
			public void onReceive(final WebViewProvider provider, final Object extra) {
				runOnUiThread(new Runnable() {
					public void run() {
						Intent intent = new Intent();
						if(extra instanceof  JSONObject){
							intent.setClass(WebPageModule.this, ChangePayActivity2.class);
							JSONObject jo =(JSONObject) extra;
							if( "false".equals(""+jo.opt("edit")) ){
								//初始支付密码
								intent.putExtra("state",ChangePayActivity2.State_InitPayPassword);
								startActivityForResult(intent, RequestCode_InitPayPassword);
							}else{
								//忘记密码
								intent.setClass(WebPageModule.this, ForgetPayActivity.class);
								startActivityForResult(intent, RequestCode_InitPayPassword);
							}
						}
					}
				});
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ColpencilLogger.d(getClass().getSimpleName()+" onActivityResult:",+requestCode+" "+resultCode);
		switch (requestCode){
			case RequestCode_Login :
				try {
					JSONObject extra = new JSONObject();
					extra.put("code",resultCode);
					extra.put("result","success");
					sendEventToHtml5("afterlogin", extra);
				} catch (JSONException e) {e.printStackTrace();	}
				break;
			case RequestCode_InitPayPassword :
				try {
					JSONObject extra = new JSONObject();
					extra.put("code",resultCode);
					extra.put("result","success");
					sendEventToHtml5("afterInitPayPassword", extra);
				} catch (JSONException e) {e.printStackTrace();	}
				break;
			case RequestCode_Video:
				try {
					JSONObject extra = new JSONObject();
					extra.put("code",requestCode);
					extra.put("result","success");
					sendEventToHtml5("aftercamera_view", extra);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			default:
				//if(requestCode>=1000){
					try {
						JSONObject extra = new JSONObject();
						extra.put("code",resultCode);
						extra.put("result","success");
						extra.put("requestCode",requestCode);
						sendEventToHtml5("afterH5Event", extra);
					} catch (JSONException e) {e.printStackTrace();	}
				//}
				break;
		}
	}

	/**
	 * 重写该函数，可实现处理收到来自Html5页面的操作请求，处理完毕后异步回调至Html5
	 */
	@Override
	protected boolean onHtml5AccessRequest(WebViewProvider provider, UZModuleContext moduleContext) {
		String name = moduleContext.optString("name");
		return true;
	}

	//默认处理收到收到来自Html5页面的操作请求，并通过UZModuleContext给予JS回调
	private void defaultHandleHtml5AccessRequest(final UZModuleContext moduleContext){
		String name = moduleContext.optString("name");
		//Object extra = moduleContext.optObject("extra");
		//AlertDialog.Builder dia = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
		//dia.setTitle("提示消息");
		//dia.setMessage("收到来自Html5页面的操作请求，访问的名称标识为：\n[" + name + "]\n传入的参数为：\n[" + extra + "]\n\n" + "是否处理？\n");
		//dia.setCancelable(false);
		//dia.setPositiveButton("不处理", null);
		//dia.setNegativeButton("处理", new OnClickListener() {
		//	public void onClick(DialogInterface dialog, int which) {
		//		dialog.dismiss();
		//		JSONObject json = new JSONObject();
		//		try{
		//			json.put("result0", "value0");
		//			json.put("result1", "value1");
		//			json.put("result2", "value2");
		//		}catch(Exception e){
		//			;
		//		}
		//		moduleContext.success(json, true);
		//	}
		//});
		//dia.show();
	}
	
	private void showAlert(String message){
		AlertDialog.Builder dia = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
		dia.setTitle("提示消息");
		dia.setMessage(message);
		dia.setCancelable(false);
		dia.setPositiveButton("确定", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				;
			}
		});
		dia.show();
	}
	
	private void showProgress(){
		mProgress.setVisibility(View.VISIBLE);
	}
	
	private void hidenProgress(){
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(mProgress.isShown()){
					mProgress.setVisibility(View.GONE);
				}
			}
		}, 1500);
	}
}
