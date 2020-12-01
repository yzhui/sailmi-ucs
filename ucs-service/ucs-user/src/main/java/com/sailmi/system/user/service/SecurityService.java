package com.sailmi.system.user.service;

import java.math.BigInteger;

import com.sailmi.system.entity.Result;
import com.sailmi.system.user.entity.SecurityAppealEntity;
import org.springframework.web.multipart.MultipartFile;

//import com.linose.common.entity.SecurityAppealEntity;
//import com.linose.common.util.Result;


/**
 * 安全凭证接口
 */
public interface SecurityService {

	/**
	 * 上传头像
	 * @param headImg 	图片
	 * @param id		当前登录用户ID
	 * @param //headUrl	文件上传路径
	 */
	int uploadHeadImg(MultipartFile headImg, BigInteger id, String path);

	int saveHeadImg(BigInteger id, String link);
	/**
	 * 邮箱唯一性校验
	 * @param eMailAddr 邮箱地址
	 * @return
	 */
	int eMailOnlyOne(String eMailAddr);
	/**
	 * 手机号邮箱校验
	 * @param userEmail
	 * @param userPhone
	 * @param code
	 * @param status
	 * @param userId
	 */

	Result checkPhoneOrEemail(String userEmail, String userPhone, String code, String status, String userId);
	/**
	 * 修改密码
	 * @param userId
	 * @param newpassword
	 * @return
	 */
	int updateUserPass(String userId, String newpassword);
	/**
	 * 客服申诉
	 * @param securityAppealEntity
	 * @return
	 */
	int insertAppeal(SecurityAppealEntity securityAppealEntity);

	/**
	 * 修改邮箱
	 * @param eMailAddr 	邮箱地址
	 * @param id			当前登录用户的ID
	 */
	void updateUserEmail(String eMailAddr, BigInteger id);


}
