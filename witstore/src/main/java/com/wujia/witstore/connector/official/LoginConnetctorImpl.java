package com.wujia.witstore.connector.official;

import java.util.HashMap;
import java.util.Map;
import org.kymjs.kjframe.http.HttpCallBack;
import com.wujia.witstore.connector.LoginConnetctor;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.AppConfig;

public class LoginConnetctorImpl implements LoginConnetctor {

	@Override
	public void authentication(String userName, String passWord,
			HttpCallBack callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("loginname", userName);
		params.put("password", passWord);
		MyHttpUtil.getInstance().post(params, AppConfig.LOGIN_URL, callback);

	}

	@Override
	public void serchQuery(String a, HttpCallBack callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("q", a==null?"":a);
		params.put("wt", "json");
		params.put("indent","true");
		MyHttpUtil.getInstance().post(params, AppConfig.SERCH, callback);
	}

	@Override
	public void mseQuery(String userId, String token, HttpCallBack callback) {
		Map<String, String> params = new HashMap<>();
		params.put("userId", userId);
		params.put("token", token);
		MyHttpUtil.getInstance().holdCookiePost(params, AppConfig.MES,callback);
	}


}
