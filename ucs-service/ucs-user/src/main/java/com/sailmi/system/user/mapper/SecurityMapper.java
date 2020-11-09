package com.sailmi.system.user.mapper;

import java.math.BigInteger;
import java.util.HashMap;

import com.sailmi.system.user.entity.SecurityAppealEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

//import com.linose.common.entity.SecurityAppealEntity;

/**
 * 安全凭证mapper
 * @author suyt
 * @author xingsg
 *
 */
@Mapper
public interface SecurityMapper {

	/**
	 * 上传头像
	 *
	 * @param saveMap
	 */
	int saveHeadImg(HashMap<String, Object> saveMap);

	/**
	 * 验证邮箱是否唯一
	 * @param eMailAddr 邮箱地址
	 * @return
	 */
	int eMailOnlyOne(String eMailAddr);
	/**
	 * 修改密码
	 * @param userId
	 * @param newpassword
	 * @return
	 */
	int updatePassword(@Param("userId") String userId, @Param("newpassword") String newpassword);
	/**
	 * 客服申诉
	 * @param securityAppealEntity
	 * @return
	 */
	int insertAppeal(SecurityAppealEntity securityAppealEntity);
	/**
	 * 修改用户emaol
	 * @param userId
	 * @param userEmail
	 */
	void updateUserEmail(@Param("userId") String userId, @Param("userEmail") String userEmail);
	/**
	 * 修改用户手机号
	 * @param userId
	 * @param userPhone
	 */
	void updateUserPhone(@Param("userId") String userId, @Param("userPhone") String userPhone);

	/**
	 * 修改邮箱
	 * @param eMailAddr
	 * @param id
	 */
	void updateUserEmaila(@Param("eMailAddr") String eMailAddr, @Param("id") BigInteger id);

}
