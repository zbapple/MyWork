package com.wujia.witstore.data.model;

import java.io.Serializable;

import org.kymjs.kjframe.database.annotate.Id;

import com.google.gson.annotations.SerializedName;

/*********************************************************** 
 * @ClassName: Hcs
 * @Description: 温湿度数据
 * @author ZOUBIN <zoubin1987@foxmail.com>
 * @date 2015年6月11日 下午2:03:52
 ************************************************************/
public class Hcs implements Serializable{


	/**
	 * @Fields id : id
	 */
	@Id()
	private int id;
	/**
	 * @Fields brand : 品牌
	 */
	private String brand;
	/**
	 * @Fields code :代码
	 */
	private String code;
	/**
	 * @Fields createcode :新建人
	 */
	private String createcode;
	/**
	 * @Fields createdate :新建时间
	 */
	private String createdate;
	/**
	 * @Fields createid :新建人用户ID
	 */
	private String createid;
	/**
	 * @Fields createname : 新建人姓名
	 */
	private String createname;
	/**
	 * @Fields humidityLowerLimit : 湿度下限
	 */
	private String humidityLowerLimit;
	/**
	 * @Fields humidityUpperLimit : 湿度上限
	 */
	private String humidityUpperLimit;
	/**
	 * @Fields monitorAddr : 监控位置
	 */
	private String monitorAddr;
	/**
	 * @Fields name : 设备名称
	 */
	private String name;
	/**
	 * @Fields remark :备注
	 */
	private String remark;
	/**
	 * @Fields showNum : 显示序号
	 */
	private String showNum;
	/**
	 * @Fields status : 状态
	 */
	private String status;
	/**
	 * @Fields tempLowerLimit : 温度下限
	 */
	private String tempLowerLimit;
	/**
	 * @Fields tempUpperLimit : 温度上限
	 */
	private String tempUpperLimit;
	private String threeId;
	/**
	 * @Fields temperature : 温度
	 */
	private String temperature;
	/**
	 * @Fields humidity : 湿度
	 */
	private String humidity;
	/**
	 * @Fields ktzt : 空调状态
	 */
	private String ktzt;
	/**
	 * @Fields qszt : 去湿状态
	 */
	private String qszt;
	/**
	 * @Fields zszt : 增湿状态
	 */
	private String zszt;
	/**
	 * @Fields tfzt : 通风状态
	 */
	private String tfzt;
	/**
	 * @Fields jhzt : 激活状态
	 */
	private String jhzt;
	/**
	 * @Fields gjzt : 关机状态
	 */
	private String gjzt;
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
	 * @Fields hcsHost : 设备主机信息
	 */
	private HcsHost hcsHost;


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

	public String getHumidityLowerLimit() {
		return humidityLowerLimit;
	}

	public void setHumidityLowerLimit(String humidityLowerLimit) {
		this.humidityLowerLimit = humidityLowerLimit;
	}

	public String getHumidityUpperLimit() {
		return humidityUpperLimit;
	}

	public void setHumidityUpperLimit(String humidityUpperLimit) {
		this.humidityUpperLimit = humidityUpperLimit;
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

	public String getTempLowerLimit() {
		return tempLowerLimit;
	}

	public void setTempLowerLimit(String tempLowerLimit) {
		this.tempLowerLimit = tempLowerLimit;
	}

	public String getTempUpperLimit() {
		return tempUpperLimit;
	}

	public void setTempUpperLimit(String tempUpperLimit) {
		this.tempUpperLimit = tempUpperLimit;
	}

	public String getThreeId() {
		return threeId;
	}

	public void setThreeId(String threeId) {
		this.threeId = threeId;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getKtzt() {
		return ktzt;
	}

	public void setKtzt(String ktzt) {
		this.ktzt = ktzt;
	}

	public String getQszt() {
		return qszt;
	}

	public void setQszt(String qszt) {
		this.qszt = qszt;
	}

	public String getZszt() {
		return zszt;
	}

	public void setZszt(String zszt) {
		this.zszt = zszt;
	}

	public String getTfzt() {
		return tfzt;
	}

	public void setTfzt(String tfzt) {
		this.tfzt = tfzt;
	}

	public String getJhzt() {
		return jhzt;
	}

	public void setJhzt(String jhzt) {
		this.jhzt = jhzt;
	}

	public String getGjzt() {
		return gjzt;
	}

	public void setGjzt(String gjzt) {
		this.gjzt = gjzt;
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

	public HcsHost getHcsHost() {
		return hcsHost;
	}

	public void setHcsHost(HcsHost hcsHost) {
		this.hcsHost = hcsHost;
	}

}
