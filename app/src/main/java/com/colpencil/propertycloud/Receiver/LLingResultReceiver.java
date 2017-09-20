/*   
 * Copyright (c) 2015-6-9 下午6:07:36  Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 * @author borturn  
 */
/**
 * @Title: LLingResultReceiver.java
 * @Package com.example.lling_demo
 * @Description: TODO
 * Copyright: Copyright (c) 2011 
 * Company:大连智慧城科技有限公司
 * 
 * @author borturn
 * @date 2015-6-9 下午6:07:36
 * @version V1.0
 */
package com.colpencil.propertycloud.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * This class is used for ...
 * 
 * @author borturn
 * @version 1.0, 2015-6-1 下午6:07:36
 */
public class LLingResultReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		String action = intent.getAction();
		String openKey = null;
		Log.i("BORTURN", action);
		if (action
				.equals("com.example.lling.act.LLING_KEY_ACCOUNT_VERIFY_ERROR")) {
			openKey = bundle.getString("OPEN_KEY");
			Toast.makeText(context, "帐户校验失败!" + openKey, Toast.LENGTH_SHORT)
					.show();
		} else if (action.equals("com.example.lling.act.LLING_KEY_FORMAT_ERROR")) {
			openKey = bundle.getString("OPEN_KEY");
			Toast.makeText(context, "开门KEY错误!" + openKey, Toast.LENGTH_SHORT)
					.show();
		} else if (action
				.equals("com.example.lling.act.LLING_KEY_TIME_SOON_VERIFY_WARN")) {
			openKey = bundle.getString("OPEN_KEY");
			Toast.makeText(context, "开门KEY有效时间即将到期!" + openKey,
					Toast.LENGTH_SHORT).show();
		} else if (action
				.equals("com.example.lling.act.LLING_KEY_TIME_VERIFY_ERROR")) {
			openKey = bundle.getString("OPEN_KEY");
			Toast.makeText(context, "开门KEY时间过期!" + openKey, Toast.LENGTH_SHORT)
					.show();
		} else if (action
				.equals("com.example.lling.act.LLING_KEYS_NOFOUND_VERIFY_ERROR")) {
			Toast.makeText(context, "未找到可用的有效KEY!", Toast.LENGTH_SHORT).show();
		} else if (action.equals("com.izhihuicheng.act.LLING_OD_ON_CONNECT")) {
			openKey = bundle.getString("OPEN_KEY");
			Toast.makeText(context, "开始连接!" + openKey, Toast.LENGTH_SHORT)
					.show();
		} else if (action
				.equals("com.example.lling.act.OPEN_NOFOUND_OPERATOR_ERROR")) {
			int openType = bundle.getInt("OPEN_TYPE");
			Toast.makeText(context, "没有找到指定的开门方式!" + openType,
					Toast.LENGTH_SHORT).show();
		} else if (action
				.equals("com.izhihuicheng.act.LLING_OPEN_REQ_PARAMS_ERROR")) {
			Toast.makeText(context, "开门参数错误!", Toast.LENGTH_SHORT).show();
		} else if (action.equals("com.izhihuicheng.act.LLING_OD_RUNNING")) {
			Toast.makeText(context, "正在执行开门!", Toast.LENGTH_SHORT).show();
		}
	}

}
