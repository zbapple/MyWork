package com.wujia.witstore.http;

import android.content.Context;

import com.google.gson.Gson;
import com.wujia.witstore.data.model.ResultInfo;
import com.wujia.witstore.ui.AppConfig;
import com.wujia.witstore.ui.AppContext;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.ui.KJActivityStack;
import org.kymjs.kjframe.utils.PreferenceHelper;

import java.util.Map;
import java.util.Map.Entry;

/*********************************************************** 
 * @ClassName: MyHttpUtil
 * @Description: 通讯父类
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年6月29日 上午11:12:12
 ************************************************************/
public class MyHttpUtil {

	private static MyHttpUtil httpUtil;
	public HttpConfig httpConfig;
	public  static Context cxt;
	private static  String path;

	private MyHttpUtil() {
		httpConfig = new HttpConfig();
		cxt = KJActivityStack.create().topActivity();

		if (cxt == null) {
			throw new NullPointerException("you have to extends KJActivity");
		}
	}

	public static MyHttpUtil getInstance() {
		httpUtil = httpUtil == null ? new MyHttpUtil() : httpUtil;
		path=AppConfig.PATH_HTTP+PreferenceHelper.readString(cxt, AppConfig.APP_SET, "ip", AppConfig.IP).trim()+"/"+
				PreferenceHelper.readString(cxt, AppConfig.APP_SET, "path", AppConfig.P_NAME).trim();
		return httpUtil;
	}

	public ResultInfo getResultInfo(String jsonStr) throws Exception   {
		Gson gson = new Gson();
		ResultInfo esultInfo =null;
		try {
			esultInfo = gson.fromJson(jsonStr, ResultInfo.class);
		} catch (Exception e) {
			throw new Exception(AppString.ERR_TIMEOUT);
		}
		return esultInfo;
	}

	public void post(Map<String, String> map, String url, HttpCallBack callback) {
		KJHttp  kjhttp = new KJHttp();
		kjhttp.cancleAll();
		kjhttp.cleanCache();
		HttpParams params = new HttpParams();
		for (Entry<String, String> a : map.entrySet()) {
			params.put(a.getKey(), a.getValue());
		}
		String urlout = path + url + params.getUrlParams();
		kjhttp.post(urlout, null, callback);
	}

	@SuppressWarnings("static-access")
	public void holdCookiePost(Map<String, String> map, String url,HttpCallBack callback) {
		AppContext ct = (AppContext) getCxt().getApplicationContext();
		HttpConfig hcfg = new HttpConfig();
		hcfg.setCookieString(ct.getCookie());
		hcfg.TIMEOUT=20000;
		KJHttp  kjhttp = new KJHttp(hcfg);
		kjhttp.cleanCache();
		HttpParams params = new HttpParams();
		for (Entry<String, String> a : map.entrySet()) {
			params.put(a.getKey(), a.getValue());
		}
		String urlout = path + url + params.getUrlParams().trim();
		kjhttp.post(urlout, null,false, callback);
	}

	public Context getCxt() {
		return cxt = KJActivityStack.create().topActivity();

	}

}
