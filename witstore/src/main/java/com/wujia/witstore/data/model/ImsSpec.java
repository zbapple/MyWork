package com.wujia.witstore.data.model;

import java.io.Serializable;

/***********************************************************
 * @ClassName: AreaSpec
 * @Description: 密集架描述
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年6月3日 下午3:32:58
 ************************************************************/
public class ImsSpec implements Serializable{

	private String id;
	private String capacity;
	private String createid;
	private String layerNo;
	private String name;
	private String remark;
	private String segmentNo;
	private String size;
	private String specsName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getCreateid() {
		return createid;
	}

	public void setCreateid(String createid) {
		this.createid = createid;
	}

	public String getLayerNo() {
		return layerNo;
	}

	public void setLayerNo(String layerNo) {
		this.layerNo = layerNo;
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

	public String getSegmentNo() {
		return segmentNo;
	}

	public void setSegmentNo(String segmentNo) {
		this.segmentNo = segmentNo;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSpecsName() {
		return specsName;
	}

	public void setSpecsName(String specsName) {
		this.specsName = specsName;
	}

}
