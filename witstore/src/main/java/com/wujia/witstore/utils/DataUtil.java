package com.wujia.witstore.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import org.kymjs.kjframe.utils.StringUtils;

/***********************************************************
 * @ClassName: DataUtil
 * @Description: 数据转换工具类
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年5月18日 下午1:21:32
 ************************************************************/
public class DataUtil {

	/**
	 * @Title: toListBean
	 * @Description: 将List<Map<?, ?>> 转换成 List<T>类型
	 * @param list
	 * @param classOfT
	 * @return List<T>
	 */
	public static <T> List<T> toListBean(List<Map<?, ?>> list, Class<T> classOfT) {
		Gson gs = new Gson();
		List<T> listOut = null;
		if (list != null && list.size() > 0) {
			listOut = new ArrayList<T>();

			for (Map<?, ?> a : list) {
				T store = gs.fromJson(gs.toJson(a), classOfT);
				listOut.add(store);
			}
		}
		return listOut;
	}

	public static int getInt(String value, int defaultValue) {

		if (StringUtils.isEmpty(value)) {
			return defaultValue;
		}
		try {
			float v=Float.valueOf(value);
			return  (int)v;
		} catch (ClassCastException e) {
			return defaultValue;
		}
	}

	public static String getType(String a){
		String type="未知";
		switch (a){
			case "ils":
				type="灯光";
				break;
			case "acs":
				type="门禁";
				break;
			case "vms":
				type="视频";
				break;
			case "dms":
				type="光盘";
				break;
			case "ims":
				type="密集架";
				break;
			case "hcs":
				type="温湿度";
				break;
			case "rfid":
				type="RFID";
				break;

		}
		return type;
	}

}
