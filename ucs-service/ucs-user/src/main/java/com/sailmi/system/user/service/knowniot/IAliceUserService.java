package com.sailmi.system.user.service.knowniot;

import com.sailmi.system.user.persist.AliceUserModel;

import java.util.Map;

/**
 * @author Tomonori
 * @mail gutrse3321@live.com
 * @data 2020-11-10 20:01
 */
public interface IAliceUserService {

	/**
	 * 创建员工，关联企业、部门
	 * @return
	 */
	String createKnowniotUser(AliceUserModel model);
}
