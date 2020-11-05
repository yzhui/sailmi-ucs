package com.sailmi.system.user.mapper;

import com.sailmi.system.user.entity.RealUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;


@Mapper
public interface RealUserMapper {

	/**
	 * 根据ID获取用户真实信息
	 *
	 * @param userId 输入参数：用户ID
	 * @return 返回值：用户真实信息实体
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
	 * 修改信息
	 * @param reUser
	 * @return
	 */
	int update(RealUserEntity reUser);

	/**
	 * 插入信息
	 * @param reUser
	 * @return
	 */
	int insert(RealUserEntity reUser);

	/**
	 * 删除信息
	 * @param realinfo
	 * @return
	 */
	int delete(RealUserEntity realinfo);

	/**
	 * 将用户实名认证状态改为已认证
	 * @param userId
	 * @return
	 */
	int realStatus(BigInteger userId);

	/**
	 * 上传的图片路径
	 * @param reUser
	 */
	int insertImgUrl(RealUserEntity reUser);

	/**
	 * 真实姓名同步到用户表
	 * @param realName
	 * @return
	 */
	int insertAccount(@Param("realName") String realName, @Param("id") BigInteger id);

}
