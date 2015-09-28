package com.wujia.witstore.ui;


public class AppConfig {
	public static final String APP_SET="wujia";
	public static final String PATH="http://192.168.1.191:8081";
	//public static final String PATH="http://192.168.1.235:8080/bis";
	public static final String LOGIN_URL="/bis/login/mobDirectLogin.do";
    public static final String ACS_QUERY="/bis/mobile/mobAcsDeviceList.do";
    public static final String ACS_HANDLE="/bis/mobile/mobSendAcsDevice.do";
    public static final String ACS_HANDLElOG="/bis/mobile/mobAcsOperateLogList.do";
    public static final String FCS_QUERY="/bis/mobile/mobFcsDeviceList.do";
    public static final String FCS_HANDLE="";
    public static final String HCS_QUERY="/bis/mobile/mobHcsDeviceList.do";
    public static final String HCS_HANDLE="";
    public static final String ILS_QUERY="/bis/mobile/mobIlsDeviceList.do";
    public static final String ILS_HANDLE="/bis/mobile/mobSendIlsDevice.do";
    public static final String IMS_QUERY="/bis/mobile/mobImsShelveList.do";
    public static final String IMS_HANDLE="/bis/mobile/mobSendImsShelve.do";
    public static final String VMS_QUERY="/bis/mobile/mobVmsDeviceList.do";
    public static final String STORE_QUERY="/bis/mobile/mobStoreList.do";
    public static final String STORE_ALARM="/bis/mobile/mobDamAlarmList.do";
    public static final String STORE_ALARMHANLE="/bis/mobile/mobDamAlarmEdit.do";
    public static final String SERCH="/solr/collection1/select";
    public static final String MES="/bis/mobile/mobIasPublishDocList.do";
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
