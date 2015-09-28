package com.wujia.witstore.connector.official;

import com.wujia.witstore.connector.AcsConnector;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.AppConfig;
import com.wujia.witstore.ui.activity.LoginActivity;

import org.kymjs.kjframe.http.HttpCallBack;

import java.util.HashMap;
import java.util.Map;

/*********************************************************** 
* @ClassName: AcsConnectorImpl 
* @Description:  
* @author ZOUBIN <zoubin1987@foxmail.com> 
* @date 2015��5��14�� ����3:07:42 
************************************************************/
public class AcsConnectorImpl implements AcsConnector {




	@Override
	public void acsQuery(int storeId, int areaId, String userId, String token,
			HttpCallBack callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("storeId", storeId==0?"":String.valueOf(storeId));
		params.put("userId", userId);
		params.put("areaId", areaId==0?"":String.valueOf(areaId));
		params.put("token", token);
		MyHttpUtil.getInstance().holdCookiePost(params, AppConfig.ACS_QUERY,
				callback);
	}

	@Override
	public void acsHandle(String userId, int deviceId, String deviceNumb,
			String osVersion, int opMark, String token, HttpCallBack callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("opMark", String.valueOf(opMark));
		params.put("userId", LoginActivity.appContext.getUserId());
		params.put("deviceId", String.valueOf(deviceId));
		params.put("deviceNumb",deviceNumb); 
		params.put("osVersion", osVersion);
		params.put("token", token);
		MyHttpUtil.getInstance().holdCookiePost(params, AppConfig.ACS_HANDLE,
				callback);
	}

	@Override
	public void acsHandleLog( int deviceId, String userId, String token, HttpCallBack callback) {

			Map<String, String> params = new HashMap<String, String>();
			params.put("deviceType","ACS");
			params.put("userId", LoginActivity.appContext.getUserId());
			params.put("deviceId", String.valueOf(deviceId));
			params.put("token", token);
			MyHttpUtil.getInstance().holdCookiePost(params, AppConfig.ACS_HANDLElOG,
					callback);
	}

}
