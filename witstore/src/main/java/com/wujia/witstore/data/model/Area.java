package com.wujia.witstore.data.model;

import java.io.Serializable;

import org.kymjs.kjframe.database.annotate.Id;

import com.google.gson.annotations.SerializedName;

/***********************************************************
 * @ClassName: Area
 * @Description: 分区表
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年5月15日 下午2:31:31
 ************************************************************/
public class Area implements Serializable{


	/**
	 * @Fields id : 分区ID
	 */
	// 将id属性设置为主键，必须有一个主键，
	// 其实如果变量名为：'id'或'_id'默认就是主键
	// 也就是在一个JavaBean里面必须有'id'或'_id'或'@Id()'注解，否则会报错
	@Id()
	private String id;

	/**
	 * @Fields storeId : 库房ID
	 */
	@SerializedName("wmsStore")
	private Store store;

	/**
	 * @Fields name : 分区名称
	 */
	private String name;

	/**
	 * @Fields status : 状态
	 */
	private String status;

	private String code;

	private String createcode;

	private String createdate;

	private String createid;

	private String createname;

	private String remark;

	private String showNum;

	private String storeId;




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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
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



}
