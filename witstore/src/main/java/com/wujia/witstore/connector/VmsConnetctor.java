package com.wujia.witstore.connector;

import org.kymjs.kjframe.http.HttpCallBack;

/*********************************************************** 
 * @ClassName: VmsConnetctor
 * @Description: 视频连接
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年5月19日 下午3:26:11
 ************************************************************/
public interface VmsConnetctor {

	/**
	 * @Title: vmsQuery
	 * @Description: 视频连接参数
	 * @param @param userId
	 * @param @param token
	 * @param @return
	 * @return String
	 */
	public void vmsQuery(String userId,String token, HttpCallBack callback);

}
