package com.wujia.witstore.data.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Ils implements Serializable{

	@SerializedName("id")
	private int deviceId; //设备ID

	private String status; //状态

	private String name; //灯光名称

	private String brightness; //亮度

	private String storeId; // 库房Id

	private String areaId; //分区ID

	private String monitorAddr; //分组名称

	/**
	 * @Fields store : 库房
	 */
	@SerializedName("wmsStore")
	private Store store;

	/**
	 * @Fields store : 库房分区
	 */
	@SerializedName("wmsStoreArea")
	private Area area;

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrightness() {
		return brightness;
	}

	public void setBrightness(String brightness) {
		this.brightness = brightness;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getMonitorAddr() {
		return monitorAddr;
	}

	public void setMonitorAddr(String monitorAddr) {
		this.monitorAddr = monitorAddr;
	}


}
