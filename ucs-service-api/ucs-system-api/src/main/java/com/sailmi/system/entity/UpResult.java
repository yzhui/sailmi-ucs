package com.sailmi.system.entity;

import java.io.Serializable;

/**
 * <p>Description: 上传图片使用工具</p>
 *
 * @author syt
 * @date 2020/11/4/0004  17:46
 * @classname sailmi-ucscom.sailmi.system.entityUpResult
 */
public class UpResult implements Serializable {
	// 判断结果
	private boolean success;
	// 返回信息
	private String message;

	public UpResult(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
