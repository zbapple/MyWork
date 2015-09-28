package com.wujia.witstore.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kymjs.kjframe.ui.KJActivityStack;
import org.kymjs.kjframe.utils.SystemTool;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MyUtils {

	public MyUtils() {
	}

	public  static String getOsVersion(){
		return ""+SystemTool.getAppVersionName(KJActivityStack.create().topActivity());
	}

	public  static String getDeviceNumb(){
		try {
			return "System:"+SystemTool.getSystemVersion()+"SDK:"+SystemTool.getSDKVersion()+"phone:"+URLEncoder.encode(android.os.Build.MODEL.trim(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "System:"+SystemTool.getSystemVersion()+"SDK:"+SystemTool.getSDKVersion()+"phone:null";
		}

	}



	/**
	 * 比较两个BEAN或MAP对象的值是否相等 如果是BEAN与MAP对象比较时MAP中的key值应与BEAN的属性值名称相同且字段数目要一致
	 *
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean domainEquals(Object source, Object target) {
		if (source == null || target == null) {
			return false;
		}
		boolean rv = true;
		if (source instanceof Map) {
			rv = mapOfSrc(source, target, rv);
		} else {
			rv = classOfSrc(source, target, rv);
		}
		return rv;
	}

	/**
	 * 源目标为MAP类型时
	 *
	 * @param source
	 * @param target
	 * @param rv
	 * @return
	 */
	private static boolean mapOfSrc(Object source, Object target, boolean rv) {
		HashMap<String, String> map = new HashMap<String, String>();
		map = (HashMap) source;
		for (String key : map.keySet()) {
			if (target instanceof Map) {
				HashMap<String, String> tarMap = new HashMap<String, String>();
				tarMap = (HashMap) target;
				if (tarMap.get(key) == null) {
					rv = false;
					break;
				}
				if (!map.get(key).equals(tarMap.get(key))) {
					rv = false;
					break;
				}
			} else {
				String tarValue = getClassValue(target, key) == null ? ""
						: getClassValue(target, key).toString();
				if (!tarValue.equals(map.get(key))) {
					rv = false;
					break;
				}
			}
		}
		return rv;
	}

	/**
	 * 源目标为非MAP类型时
	 *
	 * @param source
	 * @param target
	 * @param rv
	 * @return
	 */
	private static boolean classOfSrc(Object source, Object target, boolean rv) {
		Class<?> srcClass = source.getClass();
		Field[] fields = srcClass.getDeclaredFields();
		for (Field field : fields) {
			String nameKey = field.getName();
			if (target instanceof Map) {
				HashMap<String, String> tarMap = new HashMap<String, String>();
				tarMap = (HashMap) target;
				String srcValue = getClassValue(source, nameKey) == null ? ""
						: getClassValue(source, nameKey).toString();
				if (tarMap.get(nameKey) == null) {
					rv = false;
					break;
				}
				if (!tarMap.get(nameKey).equals(srcValue)) {
					rv = false;
					break;
				}
			} else {
				String srcValue = getClassValue(source, nameKey) == null ? ""
						: getClassValue(source, nameKey).toString();
				String tarValue = getClassValue(target, nameKey) == null ? ""
						: getClassValue(target, nameKey).toString();
				if (!srcValue.equals(tarValue)) {
					rv = false;
					break;
				}
			}
		}
		return rv;
	}

	/**
	 * 根据字段名称取值
	 *
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getClassValue(Object obj, String fieldName) {
		if (obj == null) {
			return null;
		}
		try {
			Class beanClass = obj.getClass();
			Method[] ms = beanClass.getMethods();
			for (int i = 0; i < ms.length; i++) {
				// 非get方法不取
				if (!ms[i].getName().startsWith("get")) {
					continue;
				}
				Object objValue = null;
				try {
					objValue = ms[i].invoke(obj, new Object[] {});
				} catch (Exception e) {
					continue;
				}
				if (objValue == null) {
					continue;
				}
				if (ms[i].getName().toUpperCase()
						.equals(fieldName.toUpperCase())
						|| ms[i].getName().substring(3).toUpperCase()
						.equals(fieldName.toUpperCase())) {
					return objValue;
				} else if (fieldName.toUpperCase().equals("SID")
						&& (ms[i].getName().toUpperCase().equals("ID") || ms[i]
						.getName().substring(3).toUpperCase()
						.equals("ID"))) {
					return objValue;
				}
			}
		} catch (Exception e) {
			// logger.info("取方法出错！" + e.toString());
		}
		return null;
	}

	public static boolean  copyApkFromAssets(Context context, String fileName, String path) {
		boolean copyIsFinish = false;
		try {
			InputStream is = context.getAssets().open(fileName);
			File file = new File(path);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();
			copyIsFinish = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return copyIsFinish;
	}

	public static boolean isAppInstalledUrl(Context context, String uri) {
		PackageManager pm = context.getPackageManager();
		boolean installed = false;
		try {
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
			installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			installed = false;
		}
		return installed;
	}

	public static boolean isAppInstalled(Context context, String packageName) {
		final PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		List<String> pName = new ArrayList<String>();
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				pName.add(pn);
			}
		}
		return pName.contains(packageName);
	}
}