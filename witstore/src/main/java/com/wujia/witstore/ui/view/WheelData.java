package com.wujia.witstore.ui.view;

import android.util.Log;

import com.wujia.witstore.connector.ImsConnector;
import com.wujia.witstore.connector.official.ImsConnectorImpl;
import com.wujia.witstore.data.DBManager;
import com.wujia.witstore.data.model.Area;
import com.wujia.witstore.data.model.Ims;
import com.wujia.witstore.data.model.ResultInfo;
import com.wujia.witstore.data.model.Store;
import com.wujia.witstore.http.MyHttpUtil;
import com.wujia.witstore.ui.activity.LoginActivity;
import com.wujia.witstore.utils.DataUtil;

import org.kymjs.kjframe.http.HttpCallBack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZOUBIN on 2015/9/6.
 * 15:58
 */
public  class WheelData {

    private static final WheelData single = new WheelData();

    /**
     * 一级轮子
     */
    private String[] oneDatas;

    /**
     * key - 一级 value - 二级
     */
    private Map<String, String[]> twoDatas =new HashMap<>();

    /**
     * key 二级 values - 三级
     */
    private Map<String, String[]> threeDatas  = Collections.synchronizedMap(new HashMap());

    /**
     * 一级轮子
     */
    private String[] oneDatasKey;

    /**
     * key - 一级 value - 二级
     */
    private Map<String, String[]> twoDatasKey =new HashMap<>();

    /**
     * key 二级 values - 三级
     */
    private Map<String, String[]> threeDatasKey  = Collections.synchronizedMap(new HashMap());

    private List<Store> oneEntity;

    private WheelData() {
        initData();
    }

    //静态工厂方法
    public static WheelData getInstance() {
        return single;
    }

    private void initData(){
        this.oneEntity= DBManager.getInstance().queryList(Store.class, "1=1");
        int storeId,areaId;
        if(null!=oneEntity&&oneEntity.size()>0){
            oneDatas=new String[oneEntity.size()];
            oneDatasKey=new String[oneEntity.size()];
            for (int i = 0;i<oneEntity.size();i++){
                Store store=oneEntity.get(i);
                oneDatas[i]=store.getName();
                storeId=DataUtil.getInt(store.getId(), 0);
                oneDatasKey[i]=storeId+"";
                ArrayList<Area> arealist=	(ArrayList<Area>) DBManager.getInstance().queryList(Area.class, "storeId='"+store.getId()+"'");
                if(null!=arealist){
                    String[] two=new String[arealist.size()];
                    String[] twoKey=new String[arealist.size()];
                    for(int j = 0;j<arealist.size();j++){
                        Area area=arealist.get(j);
                        two[j]=area.getName();
                        areaId=DataUtil.getInt(area.getId(), 0);
                        twoKey[j]=areaId+"";
                      //  querData(storeId,areaId);
                    }
                    if(two.length==0){
                        two=new String[]{""};
                        twoKey=new String[]{""};
                    }
                    twoDatasKey.put(store.getName(),twoKey);
                    twoDatas.put(store.getName(),two);
                }
            }
        }else{
            oneDatas  = new String[] { "" };
            oneDatasKey = new String[]{""};
        }
    }


    public void querData(int oneKey,int twoKey){
        ImsConnector imsConn = new ImsConnectorImpl();
        imsConn.imsQuery(oneKey,twoKey, LoginActivity.appContext.getUserId(), "1", callback_Query);
    }

    protected HttpCallBack callback_Query = new HttpCallBack() {
        @Override
        public void onSuccess(Map<String, String> headers, byte[] t) {
            try {
                super.onSuccess(headers, t);
                Log.i("callback_Query", new String(t));
                ResultInfo resultInfo = MyHttpUtil.getInstance().getResultInfo(new String(t));
                if (resultInfo.getStatus().equals("0")) {
                    List<Ims> dataList = (ArrayList<Ims>) DataUtil.toListBean(
                            (List<Map<?, ?>>) resultInfo.getResult(),
                            Ims.class);
                    if (null != dataList) {
                        String[] three=new String[dataList.size()];
                        String[] threeKey=new String[dataList.size()];
                        String storeId = null,areaId=null;
                        for(int j = 0;j<dataList.size();j++){
                            Ims ims=dataList.get(j);
                            three[j]=ims.getId()+"例";
                            threeKey[j]=ims.getId()+"";
                            if(storeId==null) {
                                storeId = DataUtil.getInt(ims.getStore().getId(),0)+"";
                            }
                            if(areaId==null) {
                                areaId = DataUtil.getInt(ims.getArea().getId(),0)+"";
                            }
                        }
                        threeDatasKey.put(storeId+areaId,threeKey);
                        threeDatas.put(storeId+areaId,three);
                    }
                } else {
                }
            } catch (Exception e) {
            }
        }
        @Override
        public void onFailure(int errorNo, String strMsg) {
            super.onFailure(errorNo, strMsg);
        }
    };


    public String[] getOneDatas() {
        return oneDatas;
    }

    public void setOneDatas(String[] oneDatas) {
        this.oneDatas = oneDatas;
    }

    public Map<String, String[]> getTwoDatas() {
        return twoDatas;
    }

    public void setTwoDatas(Map<String, String[]> twoDatas) {
        this.twoDatas = twoDatas;
    }

    public Map<String, String[]> getThreeDatas() {
        return threeDatas;
    }

    public void setThreeDatas(Map<String, String[]> threeDatas) {
        this.threeDatas = threeDatas;
    }

    public String[] getOneDatasKey() {
        return oneDatasKey;
    }

    public void setOneDatasKey(String[] oneDatasKey) {
        this.oneDatasKey = oneDatasKey;
    }

    public Map<String, String[]> getTwoDatasKey() {
        return twoDatasKey;
    }

    public void setTwoDatasKey(Map<String, String[]> twoDatasKey) {
        this.twoDatasKey = twoDatasKey;
    }

    public Map<String, String[]> getThreeDatasKey() {
        return threeDatasKey;
    }

    public void setThreeDatasKey(Map<String, String[]> threeDatasKey) {
        this.threeDatasKey = threeDatasKey;
    }
}
