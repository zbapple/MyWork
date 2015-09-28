package com.wujia.witstore.connector;

import org.kymjs.kjframe.http.HttpCallBack;

/*********************************************************** 
 * @ClassName: StoreDVConnetctor
 * @Description: 库房设备接口
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年5月14日 上午11:39:39
 ************************************************************/
public interface StoreConnetctor {

	/**
	 * @Title: storeQuery
	 * @Description:  库房查询
	 * @param  userId 用户ID
	 * @param  token  安全令牌
	 * @return String {"status": "0","result": [],"msg": "执行成功"}
	 */
	public void storeQuery(String userId,String token, HttpCallBack callback);

	public void storeAlarm (String userId,String token, HttpCallBack callback);

	public void storeAlarmHandle(String userId ,int id,String deviceNumb,String osVersion ,String token, HttpCallBack callback);
}
