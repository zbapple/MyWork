package com.wujia.witstore.connector.official;

import com.wujia.witstore.connector.ImsConnector;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.AppConfig;
import com.wujia.witstore.ui.activity.LoginActivity;

import org.kymjs.kjframe.http.HttpCallBack;

import java.util.HashMap;
import java.util.Map;

/*********************************************************** 
* @ClassName: ImsConnectorImpl 
* @Description: �ܼ��ܽӿ�ʵ��
* @author ZOUBIN <zoubin1987@foxmail.com> 
* @date 2015��6��29�� ����10:49:38 
************************************************************/
public class ImsConnectorImpl implements ImsConnector {

	@Override
	public void imsQuery(int storeId, int areaId, String userId, String token,
			HttpCallBack callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("storeId", storeId==0?"":String.valueOf(storeId));
		params.put("userId", userId);
		params.put("areaId", areaId==0?"":String.valueOf(areaId));
		params.put("token", token);
		MyHttpUtil.getInstance().holdCookiePost(params, AppConfig.IMS_QUERY,callback);
	}

	@Override
	public void imsHandle(String opMark, String userId, int id, String deviceNumb,
			String osVersion, String token, HttpCallBack callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("opMark", String.valueOf(opMark));
		params.put("userId", LoginActivity.appContext.getUserId());
		params.put("id", String.valueOf(id));
		params.put("deviceNumb",deviceNumb); 
		params.put("osVersion", osVersion);
		params.put("token", token);
		MyHttpUtil.getInstance().holdCookiePost(params, AppConfig.IMS_HANDLE,callback);
	}






}
