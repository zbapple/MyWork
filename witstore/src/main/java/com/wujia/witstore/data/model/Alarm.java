package com.wujia.witstore.data.model;

import java.io.Serializable;

public class Alarm implements Serializable{

	/**
	 * @Fields id : 报警信息ID
	 */
	private int id ;
	/**
	 * @Fields alarmLevel : 报警级别
	 */
	private String alarmLevel;
	/**
	 * @Fields alarmReason : 报警描述
	 */
	private String alarmReason;
	/**
	 * @Fields alarmType : 报警类型
	 */
	private String alarmType;
	/**
	 * @Fields createdate : 新建时间
	 */
	private String createdate;
	/**
	 * @Fields deviceId : 报警设备ID
	 */
	private String deviceId;
	/**
	 * @Fields deviceName : 报警设备名称
	 */
	private String deviceName;
	/**
	 * @Fields deviceType : 设备类型
	 */
	private String deviceType;
	/**
	 * @Fields isHandle : 操作标记
	 */
	private String isHandle;
	/**
	 * @Fields operateCode : 操作人
	 */
	private String operateCode;
	/**
	 * @Fields operateId : 操作人ID
	 */
	private String operateId;
	/**
	 * @Fields operateName : 操作人名
	 */
	private String operateName;

	private String alarmDate;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
	public String getAlarmReason() {
		return alarmReason;
	}
	public void setAlarmReason(String alarmReason) {
		this.alarmReason = alarmReason;
	}
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getIsHandle() {
		return isHandle;
	}
	public void setIsHandle(String isHandle) {
		this.isHandle = isHandle;
	}
	public String getOperateCode() {
		return operateCode;
	}
	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}
	public String getOperateId() {
		return operateId;
	}
	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public String getAlarmDate() {
		return alarmDate;
	}
	public void setAlarmDate(String alarmDate) {
		this.alarmDate = alarmDate;
	}



}
