package com.wujia.witstore.connector.official;

import java.util.HashMap;
import java.util.Map;

import org.kymjs.kjframe.http.HttpCallBack;

import com.wujia.witstore.connector.StoreConnetctor;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.AppConfig;

public class StoreConnetctorImpl implements StoreConnetctor {

	@Override
	public void storeQuery(String userId, String token, HttpCallBack callback) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("token", token);
		MyHttpUtil.getInstance().holdCookiePost(params, AppConfig.STORE_QUERY,callback);

	}

	@Override
	public void storeAlarm(String userId, String token, HttpCallBack callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("token", token);
		MyHttpUtil.getInstance().holdCookiePost(params, AppConfig.STORE_ALARM,callback);
		
	}

	@Override
	public void storeAlarmHandle(String userId, int deviceId,
			String deviceNumb, String osVersion, String token,
			HttpCallBack callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("id", String.valueOf(deviceId));
		params.put("deviceNumb",deviceNumb); 
		params.put("osVersion", osVersion);
		params.put("token", token);
		MyHttpUtil.getInstance().holdCookiePost(params, AppConfig.STORE_ALARMHANLE,callback);
	}

}
