package com.sailmi.system.user.entity;

import java.math.BigInteger;

public class SecurityAppealEntity {

	/**
	 * 申诉ID
	 */
	private BigInteger id;

	/**
	 * 申诉原因
	 */
	private String reason;

	/**
	 * 姓名
	 */
	private String realName;

	/**
	 * 身份证号
	 */
	private String IDcard;

	/**
	 * 手机
	 */
	private String userPhone;

	/**
	 * 邮箱
	 */
	private String userEmail;

	/**
	 * 统一账户ID
	 */
	private BigInteger accountId;

	private String appealDate;



	public String getAppealDate() {
		return appealDate;
	}

	public void setAppealDate(String appealDate) {
		this.appealDate = appealDate;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIDcard() {
		return IDcard;
	}

	public void setIDcard(String iDcard) {
		IDcard = iDcard;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public BigInteger getAccountId() {
		return accountId;
	}

	public void setAccountId(BigInteger accountId) {
		this.accountId = accountId;
	}



}
