package com.wujia.witstore.data.model;


public class ResultInfo {

	private String status ;

	private Object result ;

	private String msg ;

	private String flag ;

	public ResultInfo(){}

	public ResultInfo(String status ,Object result,String msg){
		this.status=status;
		this.result=result;
		this.msg=msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
