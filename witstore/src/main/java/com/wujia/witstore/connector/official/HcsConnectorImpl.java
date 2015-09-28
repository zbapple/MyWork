package com.wujia.witstore.connector.official;

import java.util.HashMap;
import java.util.Map;

import org.kymjs.kjframe.http.HttpCallBack;

import com.wujia.witstore.connector.HcsConnector;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.AppConfig;

public class HcsConnectorImpl implements HcsConnector {

	@Override
	public void hcsQuery(int storeId, int areaId, String userId, String token,
			HttpCallBack callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("storeId", storeId==0?"":String.valueOf(storeId));
		params.put("userId", userId);
		params.put("areaId", areaId==0?"":String.valueOf(areaId));
		params.put("token", token);
		MyHttpUtil.getInstance().holdCookiePost(params, AppConfig.HCS_QUERY,callback);
	}

	@Override
	public void hscHandle(String deviceId, String deviceNumb, String osVersion,
			int Max, int Min, int opMark, String token, HttpCallBack callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("opMark", String.valueOf(opMark));
		params.put("deviceId", deviceId);
		params.put("Max", String.valueOf(Max));
		params.put("Min", String.valueOf(Min));
		params.put("deviceNumb", deviceNumb);
		params.put("osVersion", osVersion);
		params.put("token", token);
		MyHttpUtil.getInstance().holdCookiePost(params, AppConfig.HCS_HANDLE,callback);

	}

}
