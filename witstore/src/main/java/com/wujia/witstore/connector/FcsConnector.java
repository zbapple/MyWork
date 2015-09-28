package com.wujia.witstore.connector;

import org.kymjs.kjframe.http.HttpCallBack;

/*********************************************************** 
 * @ClassName: FcsConnetctor
 * @Description: 消防接口
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年5月14日 下午2:23:22
 ************************************************************/
public interface FcsConnector {

	/**
	 * @Title: fcsQuery
	 * @Description: 消防状态查询
	 * @param  userId 用户ID
	 * @param  token  安全令牌
	 * @return String{"status": "0","result": [],"msg": "执行成功"}
	 */
	public void fcsQuery(String userId,String token, HttpCallBack callback);
}
