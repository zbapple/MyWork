package com.wujia.witstore.connector;

import org.kymjs.kjframe.http.HttpCallBack;

/*********************************************************** 
 * @ClassName: HcsConnector
 * @Description: 温湿度接口
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年5月14日 上午11:52:04
 ************************************************************/
public interface HcsConnector {

	/**
	 * @Title: hcsQuery
	 * @Description: 温湿度数据查询
	 * @param  storeId 库房ID
	 * @param  areaId  区域ID
	 * @param  userId  用户ID
	 * @param  token   安全令牌
	 * @return String{"status": "0","result": [],"msg": "执行成功"}
	 */
	public void hcsQuery(int storeId,int areaId,String userId,String token, HttpCallBack callback);

	/**
	 * @Title: hscHandle
	 * @Description: 温湿度上限下限设置
	 * @param  deviceId   温湿度设备ID
	 * @param  deviceNumb 手机型号
	 * @param  osVersion  手机操作系统版本
	 * @param  Max    温湿度上限
	 * @param  Min    温湿度下限
	 * @param  opMark 操作标识:0-温度，1-湿度
	 * @param  token  安全令牌
	 * @return String{"status": "0","result": [],"msg": "执行成功"}
	 */
	public void hscHandle(String deviceId,String deviceNumb,String osVersion,int Max,int Min,int opMark,String token, HttpCallBack callback);
}
