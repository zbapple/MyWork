package com.wujia.witstore.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

/***********************************************************
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @ClassName: ClassUtil
 * @Description: 操作Class工具
 * @date 2015年5月18日 下午1:07:50
 ************************************************************/
public class ClassUtil {

    private static ClassUtil classUtil = null;

    private ClassUtil() {
    }

    public static ClassUtil getInstance() {
        return classUtil = classUtil == null ? new ClassUtil() : classUtil;
    }

    /**
     * @Title: getBeanName
     * @Description: 得到类名
     */
    public String getBeanName(Class<?> cls) {
        String clzStr = cls.getName().toString();
        String beanName = clzStr.substring(clzStr.lastIndexOf(".") + 1).toUpperCase();
        return beanName;
    }

    /**
     * @param @param fieldName
     * @param object
     * @return Object
     * @Title: getFieldValueByName
     * @Description: 根据属性名获取属性值
     */
    public Object getFieldValueByName(String fieldName, Object object) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = object.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(object, new Object[]{});
            return value;
        } catch (Exception e) {
            Log.e("获取属性出错：", e.getMessage());
            return null;
        }
    }

    /**
     * @param o 传入对象
     * @Title: getFiledName
     * @Description: 获取属性名数组
     */
    public String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * @Title: getFiledsInfo
     * @Description: 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     */
    public List<Map<String, Object>> getFiledsInfo(final Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> infoMap = null;
        for (int i = 0; i < fields.length; i++) {
            infoMap = new HashMap<String, Object>();
            infoMap.put("type", fields[i].getType().toString());
            infoMap.put("name", fields[i].getName());
            infoMap.put("value", getFieldValueByName(fields[i].getName(), object));
            list.add(infoMap);
        }
        return list;
    }

    /**
     * @param object 传入对象
     * @Title: getFiledValues
     * @Description: 获取对象的所有属性值，返回一个对象数组
     */
    public Object[] getFiledValues(Object object) {
        String[] fieldNames = this.getFiledName(object);
        Object[] value = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            value[i] = this.getFieldValueByName(fieldNames[i], object);
        }
        return value;
    }

    public <T> List<T> serchListValue(List<T> list, String key, String vale) {
        List<T> listOut;
        if (list != null && list.size() > 0) {
            listOut = new ArrayList<T>();
            for (T a : list) {
                LinkedTreeMap b= (LinkedTreeMap) a;
                    if (b.containsKey(key)) {
                        String d = b.get(key)+"";
                        float id=Float.valueOf(d);
                        int as=(int)id;
                        int bs=Integer.parseInt(vale);
                        if(as==bs) {
                            listOut.add(a);
                            return listOut;
                        }
                    }
            }
        }
        return list;
    }



}
