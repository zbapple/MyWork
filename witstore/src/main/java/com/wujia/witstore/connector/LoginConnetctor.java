package com.wujia.witstore.connector;

import org.kymjs.kjframe.http.HttpCallBack;

/*********************************************************** 
 * @ClassName: Login
 * @Description: 登录认证接口
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年5月14日 上午11:35:30
 ************************************************************/
public interface LoginConnetctor {
	/**
	 * @Title: authentication
	 * @Description: 登录认证
	 * @param  userName 用户名称
	 * @param  passWord 用户密码
	 * @return String {"status": "0","result": [],"msg": "执行成功"}
	 */
	void authentication(String userName,String passWord, HttpCallBack callback);

	void serchQuery(String a,HttpCallBack callback);
	/**
	 * 首页广播查询
	 * */
	void mseQuery(String userId,String token,HttpCallBack callback);
}
