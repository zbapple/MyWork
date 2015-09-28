package com.wujia.witstore.data.model;

import java.io.Serializable;

import org.kymjs.kjframe.database.annotate.Id;

/***********************************************************
 * @ClassName: Store
 * @Description: 库房表
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年5月15日 下午2:27:51
 ************************************************************/
public class Store implements Serializable{
	/**
	 * @Fields id : 库房ID
	 */
	@Id()
	private String id;
	/**
	 * @Fields name : 库房名称
	 */
	private String name;

	/**
	 * @Fields status : 状态
	 */
	private String status;

	/**
	 * @Fields floorNo : 几号楼
	 */
	private String floorNo;

	private String buildingNo;

	private String code;

	private String createcode;

	private String createdate;

	private String createid;

	private String createname;

	private String remark;

	private String showNum;

	public String getBuildingNo() {
		return buildingNo;
	}

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
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

	public String getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
