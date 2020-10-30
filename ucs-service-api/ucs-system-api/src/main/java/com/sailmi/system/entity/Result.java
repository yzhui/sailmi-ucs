package com.sailmi.system.entity;

import java.io.Serializable;

public class Result implements Serializable {
	/**
	* 编码
	*/
	private String code;
	/**
	* 消息
	*/
	private String msg;
	/**
	* 数据
	*/
	private Object data;


	public Result() {
	}


	public String getMsg() {
		return msg;
	}


	public Object getData() {
		return data;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}


	public void setData(Object data) {
		this.data = data;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}



}
