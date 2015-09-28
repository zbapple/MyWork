package com.wujia.witstore.connector.official;

import java.util.HashMap;
import java.util.Map;

import org.kymjs.kjframe.http.HttpCallBack;

import com.wujia.witstore.connector.FcsConnector;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.AppConfig;

public class FcsConnectorImpl implements FcsConnector {



	@Override
	public void fcsQuery(String userId, String token, HttpCallBack callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("token", token);
		MyHttpUtil.getInstance().holdCookiePost(params, AppConfig.FCS_QUERY,callback);
	}

}
