package com.wujia.witstore.connector;

import org.kymjs.kjframe.http.HttpCallBack;

/*********************************************************** 
 * @ClassName: IlsConnector
 * @Description: 灯光接口
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年5月14日 上午11:59:31
 ************************************************************/
public interface IlsConnector {

	/**
	 * @Title: ilsQuery
	 * @Description: 灯光查询
	 * @param  storeId
	 * @param  areaId
	 * @param  userId
	 * @param  token
	 * @return String{"status": "0","result": [],"msg": "执行成功"}
	 */
	public void ilsQuery(int storeId,int areaId,String userId,String token, HttpCallBack callback);

	/**
	 * @Title: ilsHandle
	 * @Description: 灯光操作
	 * @param  userId     用户ID
	 * @param  deviceId   灯光设备ID
	 * @param  deviceNumb 手机型号
	 * @param  osVersion  手机操作系统版本
	 * @param  opMark     操作标识pMark{0-100}；opMark=0 关，opMark>0开，具体数值为亮度调节
	 * @param  token      安全令牌
	 * @return String{"status": "0","result": [],"msg": "执行成功"}
	 */
	public void ilsHandle(String userId ,int deviceId,String deviceNumb,String osVersion,int opMark ,String token, HttpCallBack callback);
}
