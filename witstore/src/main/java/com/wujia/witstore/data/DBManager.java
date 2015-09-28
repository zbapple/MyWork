package com.wujia.witstore.data;

import android.content.Context;

import com.wujia.witstore.ui.AppConfig;
import com.wujia.witstore.utils.MyUtils;

import org.kymjs.kjframe.KJDB;
import org.kymjs.kjframe.ui.KJActivityStack;

import java.util.List;

/***********************************************************
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @ClassName: DBManager
 * @Description: 本地数据管理类
 * @date 2015年6月29日 上午11:11:56
 ************************************************************/
public class DBManager {

    private KJDB kjdb;
    private static DBManager dbManager;

    private DBManager() {
        Context cxt = KJActivityStack.create().topActivity();
        if (cxt == null) {
            throw new NullPointerException("you have to extends KJActivity");
        }
        kjdb = KJDB.create(cxt, "sds.db", AppConfig.ISDEBUG, 1, null);

    }

    public static DBManager getInstance() {
        return dbManager = dbManager == null ? new DBManager() : dbManager;
    }

    public <T> void save(Class<T> classOfT, List<T> list) {
        try {
            kjdb = KJDB.create(true);
            for (T a : list) {
                // 如果数据库中已存在相同数据则不新增
                String id = (String) MyUtils.getClassValue(a, "id");
                T b = kjdb.findById(id, classOfT);

                if (b == null) {
                    kjdb.save(a);
                    continue;
                }
                if (!MyUtils.domainEquals(a, b)) {
                    kjdb.update(a);
                    continue;
                }
            }
        }catch (Exception e){}
    }

    public <T> void saveById(Class<T> classOfT, List<T> list) {
        try {
            kjdb = KJDB.create(true);
            for (T a : list) {
                // 如果数据库中已存在相同数据则不新增
                String id = (String) MyUtils.getClassValue(a, "id");
                T b = kjdb.findById(id, classOfT);

                if (b == null) {
                    kjdb.save(a);
                    continue;
                }
            }
        } catch (Exception e) {
        }

//			if(!MyUtils.domainEquals(a, b)){
//				kjdb.update(a);
//				continue;
//			}

    }

    public <T> List<T> queryList(Class<T> classOfT, String where) {
        List<T> datas = null;
        try {
            datas = kjdb.findAllByWhere(classOfT, where);
            return datas;
        } catch (Exception e) {
            return datas;
        }
    }


}
