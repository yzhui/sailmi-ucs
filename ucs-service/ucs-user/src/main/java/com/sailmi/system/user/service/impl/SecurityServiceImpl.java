package com.sailmi.system.user.service.impl;

//import com.linose.accountuser.mapper.SecurityMapper;
//import com.linose.accountuser.service.SecurityService;
//import com.linose.common.entity.SecurityAppealEntity;
//import com.linose.common.util.ResponseMessage;
//import com.linose.common.util.Result;
//import com.linose.common.util.UploadUtil;
import com.sailmi.system.entity.ResponseMessage;
import com.sailmi.system.entity.Result;
import com.sailmi.system.user.entity.SecurityAppealEntity;
import com.sailmi.system.user.mapper.SecurityMapper;
import com.sailmi.system.user.service.SecurityService;
import com.sailmi.system.user.utils.UploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @author
 */
@Service
@Slf4j
public class SecurityServiceImpl implements SecurityService {

	@Autowired(required = false)
	private SecurityMapper securityMapper;

	@Autowired
	  StringRedisTemplate stringRedisTemplate;
	/**
	 * 上传头像
	 * @return 1上传成功,2大小超出限制,3格式不符
	 */
	@Value("${headimg.readPath}")
	private String readPath;
	@Override
	public int uploadHeadImg(MultipartFile headImg, BigInteger id, String path) {
		try {
			HashMap<String, Object> uploadData = UploadUtil.uploadData(headImg,path,0);
			//图片格式
			String type = uploadData.get("type").toString();
			//名称
			String fileName = uploadData.get("fileName").toString();
	    	//文件大小
	    	Long valueOf = Long.valueOf(((Long) uploadData.get("size")).longValue());

	    	if(valueOf > 512000) {//大于500K
	    		//超出大小限制
	    		return 2;
	    	}

	    	if(type.equals("jpg") || type.equals("png")) {
	    		HashMap<String, Object> saveMap = new HashMap<String, Object>();
	    		saveMap.put("id", id);
	    		saveMap.put("headImg", readPath + fileName);
	    		//图片信息存到数据库
	    		int saveHead = securityMapper.saveHeadImg(saveMap);
	    		if(saveHead == 1) {
	    			//上传成功
	    			return 1;
	    		}
	    	}else {
	    		//格式不符合
	    		return 3;
	    	}
	    	//错误信息
	    	log.debug("上传头像失败,图片路径写入数据库失败");
	    	this.logFileInfo();
	    	return 0;
		} catch (Exception e) {
			log.error("上传头像失败,系统异常",e);
			//上传失败
			return 0;
		}
	}

	private void logFileInfo() {
		log.debug("类",Thread.currentThread().getStackTrace()[1].getFileName());
		log.debug("行: ",Thread.currentThread().getStackTrace()[1].getLineNumber());
	}

	 /**
     * 验证邮箱是否唯一
     * @param eMailAddr	邮箱地址
     * @return 0失败,1邮箱可用,2邮箱已被使用
     */
	@Override
	public int eMailOnlyOne(String eMailAddr) {
		int mail = -1;
		try {
			mail = securityMapper.eMailOnlyOne(eMailAddr);
			if(mail == 0) {
				//邮箱可用
				return 1;
			}
			//邮箱已被使用
			return 2;
		}catch(Exception e) {
			log.error("验证邮箱唯一性异常",e);
			return 0;
		}
	}
	/**
	 * 手机号邮箱校验
	 * @param userEmail
	 * @param userPhone
	 * @param code
	 * @param status
	 */
	@Override
	public Result checkPhoneOrEemail(String userEmail, String userPhone, String code, String status, String userId) {

	  	Result result = new Result();
    	if(StringUtils.isEmpty(status) && StringUtils.isEmpty(code)) {
    		result.setCode(ResponseMessage.PARAERROE);
    		result.setMsg("参数缺失");
    	}else {
    		String key="";
    		if(status.equals("1")) { 	//手机号验证
    			 key = "phoneCode:check"+userPhone;
    		}else{
    			key = "EmailCode:"+userEmail;

    		}
    		Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);

    			if(expire<0) {
    				result.setCode(ResponseMessage.TIMEOUT);
    				result.setMsg("验证码超时");
    			}else {
    				String redisCode = stringRedisTemplate.opsForValue().get(key);
    				if(redisCode!=null) {
    					if(redisCode.equals(code)) {//验证码验证成功
    						result.setCode(ResponseMessage.SUCCESS);
    						result.setMsg("验证成功");

    						if(status.equals("1")) { 	//手机号验证
    							if(StringUtils.isEmpty(userPhone)) {
    							}else {
    								securityMapper.updateUserPhone(userId,userPhone);
    							}
    						}else{
    							if(StringUtils.isEmpty(userEmail)) {
    							}else {
    								securityMapper.updateUserEmail(userId,userEmail);

    							}

    						}
    					}else {
    						result.setCode(ResponseMessage.PARAERROE);
    						result.setMsg("验证码错误");
    					}
    				}
    			}

    	}

			return result;

	}
	/**
	 * 修改密码
	 * @param userId
	 * @param newpassword
	 * @return
	 */
	@Override
	public int updateUserPass(String userId, String newpassword) {

		return securityMapper.updatePassword(userId,newpassword);
	}
	/**
	 * 客服申诉
	 * @param securityAppealEntity
	 * @return
	 */
	@Override
	public int insertAppeal(SecurityAppealEntity securityAppealEntity) {

		return  securityMapper.insertAppeal(securityAppealEntity);
	}

	@Override
	public void updateUserEmail(String eMailAddr, BigInteger id) {
		securityMapper.updateUserEmaila(eMailAddr,id);

	}
}
