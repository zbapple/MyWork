package com.wujia.witstore.connector;

import org.kymjs.kjframe.http.HttpCallBack;

/**
 * @ClassName: ImsConnector
 * @Description: 密集架接口
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年5月14日 上午11:25:19
 *
 */
public interface ImsConnector {

	/**
	 * @Title: imsQuery
	 * @Description: 密集架查询
	 * @param  storeId 库房ID
	 * @param  areaId  分区ID
	 * @param  userId  用户id
	 * @param  token   安全令牌（以后扩展通讯加密加密功能）
	 * @return String
	 */
	public void imsQuery(int storeId,int  areaId,String  userId,String token, HttpCallBack callback);

	/**
	 * @Title: imsHandle
	 * @Description: 密集架操作
	 * @param  opMark 操作标识（0停止,1左开 ,2右开,3通风,4合拢）
	 * @param  userId 用户id
	 * @param  id     密集架id
	 * @param  deviceNumb 设备号（手机型号）
	 * @param  osVersion  手机操作系统版本
	 * @param  token 安全令牌
	 * @return String {"status": "0","result": [],"msg": "执行成功"}
	 */
	public void imsHandle(String opMark,String userId,int id,String deviceNumb,String osVersion,String token, HttpCallBack callback);
}
