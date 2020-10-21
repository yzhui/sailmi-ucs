package com.sailmi.system.vo;

import com.sailmi.system.entity.Tenant;

public class TenantVo extends Tenant {

	private  String enterpriseName;//企业名称

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName=enterpriseName;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}
}
