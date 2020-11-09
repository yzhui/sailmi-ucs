package com.sailmi.system.user.service;

import com.sailmi.system.user.entity.RealUserEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * 用户真实信息
 */
public interface RealUserService {

	/**
	 * 获取用户真实信息
	 */
	RealUserEntity get(BigInteger userId);

	/**
	 * 用户信息列表
	 *
	 * @param realUser 输入参数：用户ID
	 * @return 返回值：用户信息
	 */
	List<RealUserEntity> query(RealUserEntity realUser);

	/**
	 * 修改、增加用户真实信息 输入参数：用户真实信息实体类
	 *
	 * @param reUser
	 * @return 返回值：1成功，0失败
	 */
	int upsert(RealUserEntity reUser);

	/**
	 * 上传身份证信息
	 * @param realinfo
	 */
	int IDCard(RealUserEntity realinfo);


}
