package com.sailmi.system.vo;

import com.sailmi.system.entity.AuthClient;

public class AuthClientVo extends AuthClient {

	private  String systemName;//系统名称

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemName() {
		return systemName;
	}
}
