package com.sdk.samples;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.webkit.CookieManager;

import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.uzmap.pkg.uzcore.UZCoreUtil;
import com.uzmap.pkg.uzcore.UZWebView;
import com.uzmap.pkg.uzcore.annotation.UzJavascriptMethod;
import com.uzmap.pkg.uzcore.uzmodule.UZModule;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class MyUZModule extends UZModule {
	static {
		System.err.println("MyUZModule cinit");
	}

	public MyUZModule(final UZWebView wv) {
		super(wv);
		//if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
		////System.err.println("MyUZModule acceptThirdPartyCookies?");
		///	CookieManager.getInstance().acceptThirdPartyCookies(mWebView);
		//}
		//wv.getSettings().cook

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					//Toast.makeText(getContext(), "setDebugger", Toast.LENGTH_SHORT).show();
					System.err.println("MyUZModule setDebugger?");
					wv.setWebContentsDebuggingEnabled(true);
					wv.addJavascriptInterface(this, "my");
				}
			});
		}
	}

	//requestCode  : 1000 +
	@UzJavascriptMethod
	public String jsmethod_startActivityForResult(final UZModuleContext moduleContext) {
		//PendingIntent pi=new PendingIntent();
		Intent intent = new Intent();
		String className = moduleContext.optString("activity", null);
		Integer requestCode = moduleContext.optInt("requestCode", -1);

		//chkk
		if(className.startsWith(".")){
			className = this.mContext.getPackageName() + className;
		}
		intent.setClassName(this.mContext, className);
		//WebPageModule  afterH5Event

		try {
			JSONObject param = moduleContext.optJSONObject("param");
			if (param != null){
				Iterator<String> ks = param.keys();
				while(ks.hasNext()){
					String k = ks.next();
					Object v = null;
					v = param.get(k);
					if(v instanceof  Number){
						intent.putExtra(k, (Number)v);
					}else if(v instanceof  String){
						intent.putExtra(k, (String)v);
					}else{
						//ignore
					}
				}

			}
		} catch (JSONException e) {
			Log.e("MyUZModule", "jsmethod_getPrefs2 err", e);
			moduleContext.success("error",false,false);
			return "error";
		}
		startActivityForResult(intent, requestCode);

		moduleContext.success("success",false,false);
		return  "success";
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}

	/**
	 * getPrefs2({key:'k1',function(ret,err){  ret.value  }}))
	 * getPrefs2({key:['k1','k2'],function(ret,err){  ret.values :{ k1: '',k2:'2'}  }}))
	 *  */
	@UzJavascriptMethod
	public String jsmethod_getPrefs2(final UZModuleContext moduleContext) {
		JSONObject rs = new JSONObject();
		String key = "";
		try{
			Object okey = moduleContext.get().get("key");
			if(okey instanceof  String){
				key=(String)okey;
			}else if(okey instanceof JSONArray){
			}
		}catch(Exception e){}
		Object v = SharedPreferencesUtil.getInstance(getContext()).sp.getAll().get(key);
		String retstr = "";
		try {
			rs.put("value", v);
			moduleContext.success(rs, false);
		} catch (JSONException e) {
			Log.e("MyUZModule", "jsmethod_getPrefs2 err", e);
		}
		//moduleContext.async();
		return  "success";
	}
	@UzJavascriptMethod
	public void jsmethod_setPrefs2(final UZModuleContext moduleContext) {
	}

	public String test1(){
		return "test1";
	}
	@UzJavascriptMethod
	public void jsmethod_test2(final UZModuleContext moduleContext) {
		moduleContext.success("succ test2",false,false);
	}
	/**
	 *  var yolandaMod=api.require('yolanda');
	 var cookieUrl =$.yolanda.config.getServerURL();
	 yolandaMod.cookie({method:'getCookie',url:cookieUrl}, function(ret1,err){
	 if(ret1.success==0){
	 var ccstr = ret1.value;
	 }
	 });
	 param:
	 {method: getCookie ,url:''   }
	 {method: setCookie ,url:'192.168.0.1/yonlandaStar',value;'JSESSIONID=111; path=/'   }
	 {method: setCookie ,url:'192.168.0.1/yonlandaStar',value;'JSESSIONID=111; path=/'   }
	 removeCookie:::  value="h5C1=''; Expires=Thu, 01-Jan-1970 00:00:10 GMT; Path=/yonlandaStar"

	 {method: removeAllCookie ,url:''    }
	 *
	 */
	@UzJavascriptMethod
	public void jsmethod_cookie(final UZModuleContext moduleContext){
		UZModuleContext callbackContext = moduleContext;

		String method = moduleContext.optString("method");
		HashMap<String,Object> maps = new HashMap<String,Object>();
		String url=moduleContext.optString("url");
		Log.e("MyUZModule", "jsmethod_cookie."+method+"");
		if("getCookie".equals(method)){
			String val = UZCoreUtil.getCookie(url);
//			moduleContext.success(val, false, false);
			maps.put("value", val);
			callback(callbackContext, 0, "", true, maps);
			return;
		}else if("setCookie".equals(method)){
			String value=moduleContext.optString("value");//JSESSIONID=111; path=/web1;
			UZCoreUtil.setCookie(url, value);
//			moduleContext.success("success", false, false);
			callback(callbackContext, 0, "", true, maps);
			return;
		}else if("removeAllCookie".equals(method)){
			CookieManager.getInstance().removeAllCookie();
//			moduleContext.success("success", false, false);
			callback(callbackContext, 0, "", true, maps);
			return;
		}
		Log.e("MyUZModule", "jsmethod_cookie."+method+"  无此方法");
		// moduleContext.error("success", false, false);
		callback(callbackContext, -1, "无此方法", false, maps);
	}

	private void callback(UZModuleContext callbackContext, int code, String name, boolean success, HashMap<String, Object> maps) {
		callbackContext.success("success",false,false);
	}

	private void callback(UZModuleContext callbackContext) {
		try {
			JSONObject err = new JSONObject();
			err.put("msg","err");

			JSONObject result = new JSONObject();
			result.put("code", 0);
			result.put("result", 1);
			result.put("message", "");

			callbackContext.error(result, err, false);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}


//
	/**
	 //	 * getCookie("name")
	 //	 */
//	@UzJavascriptMethod
//	public void jsmethod_getCookie(final UZModuleContext moduleContext){
//		String url=moduleContext.optString("url");
//		String val = UZCoreUtil.getCookie(url);
//		moduleContext.success(val, false, false);
////		CookieManager.getInstance().getCookie(url)  vvv ; key=111;
////		YolandaPluginHelper.callback(callbackContext,-1,msg,false,null);
//	}
//	/*@UzJavascriptMethod
//	public void jsmethod_getCookieOfName(final UZModuleContext moduleContext){
//		String url=moduleContext.optString("url");//camera、album
//		String name=moduleContext.optString("name");
//		String val = UZCoreUtil.getCookie(url);
//		//parser CookieStr  .get(name)
//		moduleContext.success(val, false, false);
//	}*/
//	/*
//	allowFileSchemeCookies()
//	getInstance()
//	setAcceptFileSchemeCookies(boolean)
//	CookieManager()
//	acceptCookie()
//	clone()
//	getCookie(String)
//	hasCookies()
//	removeExpiredCookie()
//	setAcceptCookie(boolean)
//	setCookie(String, String)
//	removeAllCookie()
//	removeSessionCookie()*/
//	@UzJavascriptMethod
//	public void jsmethod_removeAllCookie(final UZModuleContext moduleContext){
//		CookieManager.getInstance().removeAllCookie();
//		moduleContext.success("success", false, false);
//	}
//
//	/**
//	 * setCookie({"url":"",value:'JSESSIONID=delete; exprate=0; path=/'})
//	 */
//	@UzJavascriptMethod
//	public void jsmethod_setCookie(final UZModuleContext moduleContext){
//		String url=moduleContext.optString("url");
//		String value=moduleContext.optString("value");//JSESSIONID=111; path=/yonlandaStar;
//		UZCoreUtil.setCookie(url, value);
//		moduleContext.success("success", false, false);
//	}
//
}