package com.wujia.witstore.connector.official;

import com.wujia.witstore.connector.IlsConnector;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.AppConfig;
import com.wujia.witstore.ui.activity.LoginActivity;

import org.kymjs.kjframe.http.HttpCallBack;

import java.util.HashMap;
import java.util.Map;

public class IlsConnectorImpl implements IlsConnector {

	@Override
	public void ilsQuery(int storeId, int areaId, String userId, String token,
			HttpCallBack callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("storeId", storeId==0?"":String.valueOf(storeId));
		params.put("userId", userId);
		params.put("areaId", areaId==0?"":String.valueOf(areaId));
		params.put("token", token);
		MyHttpUtil.getInstance().holdCookiePost(params, AppConfig.ILS_QUERY,callback);
	}

	@Override
	public void ilsHandle(String userId, int deviceId, String deviceNumb,
			String osVersion, int opMark, String token, HttpCallBack callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("opMark", String.valueOf(opMark));
		params.put("userId", LoginActivity.appContext.getUserId());
		params.put("deviceId", String.valueOf(deviceId));
		params.put("deviceNumb",deviceNumb); 
		params.put("osVersion", osVersion);
		params.put("token", token);
		MyHttpUtil.getInstance().holdCookiePost(params, AppConfig.ILS_HANDLE,callback);
	}

}
