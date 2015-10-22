package com.wujia.witstore.ui;


public class AppConfig {
	public static final String APP_SET="wujia";
    public static final String PATH_HTTP="http://";
    public static  String IP="192.168.1.191:8081";
    public static  String P_NAME="bis";
	public static final String LOGIN_URL="/login/mobDirectLogin.do";
    public static final String ACS_QUERY="/mobile/mobAcsDeviceList.do";
    public static final String ACS_HANDLE="/mobile/mobSendAcsDevice.do";
    public static final String ACS_HANDLElOG="/mobile/mobAcsOperateLogList.do";
    public static final String FCS_QUERY="/mobile/mobFcsDeviceList.do";
    public static final String FCS_HANDLE="";
    public static final String HCS_QUERY="/mobile/mobHcsDeviceList.do";
    public static final String HCS_HANDLE="";
    public static final String ILS_QUERY="/mobile/mobIlsDeviceList.do";
    public static final String ILS_HANDLE="/mobile/mobSendIlsDevice.do";
    public static final String IMS_QUERY="/mobile/mobImsShelveList.do";
    public static final String IMS_HANDLE="/mobile/mobSendImsShelve.do";
    public static final String VMS_QUERY="/mobile/mobVmsDeviceList.do";
    public static final String STORE_QUERY="/mobile/mobStoreList.do";
    public static final String STORE_ALARM="/mobile/mobDamAlarmList.do";
    public static final String STORE_ALARMHANLE="/mobile/mobDamAlarmEdit.do";
    public static final String SERCH="/solr/collection1/select";
    public static final String MES="/mobile/mobIasPublishDocList.do";
    public static final boolean ISDEBUG=true;

    public static final String HCS_OPEN="1";
    public static final String HCS_CLOSE="0";
    
    public static final String ILS_OPEN="1";
    public static final String ILS_CLOSE="0";

    public static final String ACS_OPEN="1";
    public static final String ACS_CLOSE="0";
    
    public static final String ACS_OPEN_FLAG="ILS_OPEN";
    public static final String ACS_CLOSE_FLAG="ILS_CLOSE";
    
    public static final String IMS_OPEN_FLAG="ILS_OPEN";
    public static final String IMS_CLOSE_FLAG="ILS_CLOSE";
    public static final int SERCH_DIF=9999999;
}
