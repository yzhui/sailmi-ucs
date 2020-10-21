package com.sailmi.message.model.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class QuerySendResult {

	/**
	 * 查询是否成功
	 */
	private boolean success;

	private byte sendStatus;

	private String content;

	private LocalDateTime receiveDate;

	private String failCode;

	public QuerySendResult(boolean success, byte sendStatus, String content, LocalDateTime receiveDate, String failCode) {
		this.success = success;
		this.sendStatus = sendStatus;
		this.content = content;
		this.receiveDate = receiveDate;
		this.failCode = failCode;
	}

	public QuerySendResult(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(byte sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getFailCode() {
		return failCode;
	}

	public void setFailCode(String failCode) {
		this.failCode = failCode;
	}

	public LocalDateTime getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(LocalDateTime receiveDate) {
		this.receiveDate = receiveDate;
	}

}
