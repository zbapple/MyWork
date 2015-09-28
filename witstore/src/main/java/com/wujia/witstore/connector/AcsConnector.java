package com.wujia.witstore.connector;

import org.kymjs.kjframe.http.HttpCallBack;

/*********************************************************** 
 * @ClassName: AcsConnector
 * @Description: 门禁接口
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年5月14日 下午2:15:12
 ************************************************************/
public interface AcsConnector {

	/**
	 * @Title: acsQuery
	 * @Description: 门禁查询
	 * @param  storeId 库房ID
	 * @param  areaId  区域ID
	 * @param  userId  用户ID
	 * @param  token   安全令牌
	 * @return String{"status": "0","result": [],"msg": "执行成功"}
	 */
	void acsQuery(int storeId,int areaId,String userId,String token, HttpCallBack callback);

	/**
	 * @Title: acsHandle
	 * @Description: 门禁操作
	 * @param  userId
	 * @param  deviceId
	 * @param  deviceNumb
	 * @param  osVersion
	 * @param  opMark
	 * @param  token
	 * @return String{"status": "0","result": [],"msg": "执行成功"}
	 */
	void acsHandle(String userId,int deviceId,String deviceNumb,String osVersion,int opMark,String token, HttpCallBack callback);

	/**
	 * @Title: acsHandle
	 * @Description: 门禁操作日志
	 * @param  userId
	 * @param  deviceId
	 * @param  token
	 * @return String{"status": "0","result": [],"msg": "执行成功"}
	 */
	void acsHandleLog(int deviceId,String userId,String token, HttpCallBack callback);
}
