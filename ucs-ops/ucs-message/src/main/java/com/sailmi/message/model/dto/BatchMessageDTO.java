package com.sailmi.message.model.dto;

import java.util.Set;

import com.sailmi.message.model.validation.MessageSettingId;
import com.sailmi.message.model.validation.Content;
import com.sailmi.message.model.validation.Mobile;

public class BatchMessageDTO {

	/**
	 * 手机号码
	 */
	private Set<@Mobile String> mobile;

	/**
	 * 应用id
	 */
	@MessageSettingId
	private String appId;

	/**
	 * 短信内容
	 */
	@Content
	private String content;

	public Set<String> getMobile() {
		return mobile;
	}

	public void setMobile(Set<String> mobile) {
		this.mobile = mobile;
	}

	public String getAppId() {
		return appId;
	}

	public void setMessageSettingId(String appId) {
		this.appId = appId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
