package com.sailmi.system.user.entity;

import java.math.BigInteger;
/**
 * 上次操作企业实体类
 * @author syt
 */
public class LastEnterpriseEntity {

	/**
	 * 上次操作企业的名称
	 */
	private String lastEnterpriseName;

	/**
	 * 上次操作企业的ID
	 */
	private BigInteger lastEnterpriseId;

	public String getLastEnterpriseName() {
		return lastEnterpriseName;
	}

	public void setLastEnterpriseName(String lastEnterpriseName) {
		this.lastEnterpriseName = lastEnterpriseName;
	}

	public BigInteger getLastEnterpriseId() {
		return lastEnterpriseId;
	}

	public void setLastEnterpriseId(BigInteger lastEnterpriseId) {
		this.lastEnterpriseId = lastEnterpriseId;
	}



}
