package com.wujia.witstore.data.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Scs implements Serializable{

	@SerializedName("id")
	private int deviceId; //设备ID

	private String status; //状态

	private String name; //名称

	private String storeId; // 库房Id

	private String areaId; //分区ID

	/**
	 * @Fields store : 库房
	 */
	@SerializedName("wmsStore")
	private Store store;

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



}
