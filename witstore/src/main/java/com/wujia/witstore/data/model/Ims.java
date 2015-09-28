package com.wujia.witstore.data.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/***********************************************************
 * @ClassName: Ims
 * @Description: 密集架表
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年5月15日 下午2:35:03
 ************************************************************/
public class Ims implements Serializable{

	/**
	 * @Fields id : 密集架ID
	 */
	private int id;

	/**
	 * @Fields storeId : 库房ID
	 */
	private String storeId;

	/**
	 * @Fields areaId : 分区ID
	 */
	private String areaId;

	/**
	 * @Fields status : 状态
	 */
	private String status;

	/**
	 * @Fields columnNo : 列号
	 */
	private String columnNo;

	/**
	 * @Fields showNum : 排序号
	 */
	private String showNum;

	/**
	 * @Fields storage : 存储
	 */
	private String storage;

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

	/**
	 * @Fields ImsSpec : 密集架描述
	 */
	@SerializedName("imsShelveSpec")
	private ImsSpec imsSpec;



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getColumnNo() {
		return columnNo;
	}

	public void setColumnNo(String columnNo) {
		this.columnNo = columnNo;
	}

	public String getShowNum() {
		return showNum;
	}

	public void setShowNum(String showNum) {
		this.showNum = showNum;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
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

	public ImsSpec getImsSpec() {
		return imsSpec;
	}

	public void setImsSpec(ImsSpec imsSpec) {
		this.imsSpec = imsSpec;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

}
