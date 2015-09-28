package com.wujia.witstore.ui;

import android.app.Application;

import com.wujia.witstore.data.DBManager;
import com.wujia.witstore.data.model.Store;

import org.kymjs.kjframe.utils.PreferenceHelper;

import java.util.List;
/***********************************************************
 * @ClassName: AppContext
 * @Description: 全局配置
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年6月29日 上午11:10:01
 ************************************************************/
public class AppContext extends Application {

    private  String userId;
    private  String userName;
    private  List<Store> store;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setCookie(String cookie) {
        PreferenceHelper.write(this, AppConfig.APP_SET, "cookie", cookie);
    }

    public void getCookie(String cookie) {
        PreferenceHelper.write(this, AppConfig.APP_SET, "cookie", cookie);
    }

    public List<Store> getStore() {
        if(store==null){
            store =DBManager.getInstance().queryList(Store.class, "status='再用'");
        }
        return store;
    }

    public String getCookie() {
        return PreferenceHelper.readString(this, AppConfig.APP_SET, "cookie","");
    }

    public  String getUserId() {
        return userId==null?"":userId;
    }

    public  void setUserId(String userId) {
        this.userId = userId;
    }

    public  String getUserName() {
        return userName;
    }

    public  void setUserName(String userName) {
        this.userName = userName;
    }

    public  void setStore(List<Store> store) {
        this.store = store;
    }
}
