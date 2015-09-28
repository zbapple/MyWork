package com.iflytek;

import com.wujia.witstore.connector.AcsConnector;
import com.wujia.witstore.connector.IlsConnector;
import com.wujia.witstore.connector.ImsConnector;
import com.wujia.witstore.connector.official.AcsConnectorImpl;
import com.wujia.witstore.connector.official.IlsConnectorImpl;
import com.wujia.witstore.connector.official.ImsConnectorImpl;
import com.wujia.witstore.ui.activity.LoginActivity;
import com.wujia.witstore.utils.MyUtils;

import org.kymjs.kjframe.http.HttpCallBack;

public class VoiceContorl {

	public static void imshandle(String mack, int id,HttpCallBack callback_handles) {

		ImsConnector imsConn = new ImsConnectorImpl();
		imsConn.imsHandle(mack, LoginActivity.appContext.getUserId(), id,
				MyUtils.getDeviceNumb(), MyUtils.getOsVersion(), "1",
				callback_handles);
	}
	
	public static void acshandle(int id, int opMark ,HttpCallBack callback_handles) {
       
		AcsConnector acsConn = new AcsConnectorImpl();
		acsConn.acsHandle(LoginActivity.appContext.getUserId(), id,
				MyUtils.getDeviceNumb(), MyUtils.getOsVersion(), opMark,"token",
				callback_handles);
	}
	
	public static void ilshandle(int id, int opMark ,HttpCallBack callback_handles) {
		IlsConnector ilsConn = new IlsConnectorImpl();
		ilsConn.ilsHandle(LoginActivity.appContext.getUserId(), id,
				MyUtils.getDeviceNumb(), MyUtils.getOsVersion(), opMark,"token",
				callback_handles);		
	}
	
	

}
