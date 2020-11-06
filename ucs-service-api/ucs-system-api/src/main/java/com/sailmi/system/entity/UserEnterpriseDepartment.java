package com.sailmi.system.entity;

import java.math.BigInteger;

/**
 * 用户企业部门关联查询实体类
 *
 * @author hdd
 * @date 2019/08/02
 */
public class UserEnterpriseDepartment {

	// 企业ID
	private BigInteger enterpriseId;
	// 企业名称
	private String enterpriseName;
	// 创建时间
	private String createTime;
	// 部门id
	private BigInteger departmentId;
	// 部门名称
	private String departmentName;
	// 身份
	private int status;

	public BigInteger getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(BigInteger enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public BigInteger getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(BigInteger departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
