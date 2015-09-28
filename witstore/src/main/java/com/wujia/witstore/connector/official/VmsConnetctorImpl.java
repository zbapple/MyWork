package com.wujia.witstore.connector.official;

import java.util.HashMap;
import java.util.Map;

import org.kymjs.kjframe.http.HttpCallBack;

import com.wujia.witstore.connector.VmsConnetctor;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.AppConfig;

public class VmsConnetctorImpl  implements VmsConnetctor {

	@Override
	public void vmsQuery(String userId, String token, HttpCallBack callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("storeId", "");
		params.put("areaId", "");
		params.put("userId", userId);
		params.put("token", token);
		MyHttpUtil.getInstance().holdCookiePost(params, AppConfig.VMS_QUERY,callback);
	}

}
