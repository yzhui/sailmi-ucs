package com.sailmi.system.wrapper;

import com.sailmi.core.mp.support.BaseEntityWrapper;
import com.sailmi.core.tool.utils.BeanUtil;
import com.sailmi.system.entity.AuthClient;

import com.sailmi.system.entity.SystemEntity;
import com.sailmi.system.vo.AuthClientVo;
import com.sailmi.system.vo.SystemVO;


public class AuthClientWrapper extends BaseEntityWrapper<AuthClient, AuthClientVo> {

	public static AuthClientWrapper build() {
		return new AuthClientWrapper();
	}

	@Override
	public AuthClientVo entityVO(AuthClient system) {
		AuthClientVo systemVO = BeanUtil.copy(system, AuthClientVo.class);
		return systemVO;
	}
}
