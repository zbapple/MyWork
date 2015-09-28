package com.wujia.witstore.data.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Acs implements Serializable{
	private int id;
	private String brand;
	private String code;
	private String createcode;
	private String createdate;
	private String createid;
	private String createname;
	private String deviceIp;
	private String devicePort;
	private String monitorAddr;
	private String name;
	private String remark;
	private String showNum;
	private String status;
	private String threeId;
	private AcsHost acsHost;

	@SerializedName("wmsStore")
	private Store store;


	@SerializedName("wmsStoreArea")
	private Area area;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCreatecode() {
		return createcode;
	}

	public void setCreatecode(String createcode) {
		this.createcode = createcode;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getCreateid() {
		return createid;
	}

	public void setCreateid(String createid) {
		this.createid = createid;
	}

	public String getCreatename() {
		return createname;
	}

	public void setCreatename(String createname) {
		this.createname = createname;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public String getDevicePort() {
		return devicePort;
	}

	public void setDevicePort(String devicePort) {
		this.devicePort = devicePort;
	}

	public String getMonitorAddr() {
		return monitorAddr;
	}

	public void setMonitorAddr(String monitorAddr) {
		this.monitorAddr = monitorAddr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShowNum() {
		return showNum;
	}

	public void setShowNum(String showNum) {
		this.showNum = showNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getThreeId() {
		return threeId;
	}

	public void setThreeId(String threeId) {
		this.threeId = threeId;
	}

	public AcsHost getAcsHost() {
		return acsHost;
	}

	public void setAcsHost(AcsHost acsHost) {
		this.acsHost = acsHost;
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

}
