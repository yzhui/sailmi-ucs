package com.sailmi.system.user.controller;

import com.alibaba.fastjson.JSON;
//import com.linose.accountuser.sendmail.MailService;
//import com.linose.accountuser.service.AccountUserService;
//import com.linose.accountuser.service.SecurityService;
//import com.linose.common.entity.AccountUserEntity;
//import com.linose.common.entity.SecurityAppealEntity;
//import com.linose.common.util.JWTDecodeUtil;
//import com.linose.common.util.ResponseMessage;
//import com.linose.common.util.Result;
import com.sailmi.core.secure.AuthUser;
import com.sailmi.core.tool.utils.DigestUtil;
import com.sailmi.system.entity.ResponseMessage;
import com.sailmi.system.entity.Result;
import com.sailmi.system.user.entity.AccountUserEntity;
import com.sailmi.system.user.service.AccountUserService;
import com.sailmi.system.user.service.MailService;
import com.sailmi.system.user.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 安全凭证
 * @Date 2020-1-8
 * @author suyt
 * @author xingsg
 *
 */
@RestController
@RequestMapping("security")
@Slf4j
public class SecurityController {

    @Autowired(required = false)
    private AccountUserService accountUserService;

    @Autowired(required = false)
    private SecurityService securityService;

    /**
     * 邮件发送
     */
    @Autowired(required = false)
    private MailService mailService;

    /**
     * redis
     */
    @Autowired
	StringRedisTemplate stringRedisTemplate;

    /**
     * 账号安全风险评估
     * level:1低,2中,3高    每一位用户都会有密码,等级默认为1
     * @param id 当前登录用户ID
     * @author suyt
     * @return
     */
    @RequestMapping("getLevel")
	public Result getLevel(AuthUser authUser, BigInteger id) {
		int level = 1;//初始值为1
		Result result = new Result();
		HashMap<String, Integer> hashMap = new HashMap<String,Integer>();
		id = BigInteger.valueOf(authUser.getUserId());
		AccountUserEntity acQuery = accountUserService.get(id);
		if(acQuery.getPhoneSta().equals("1")) {
			level += 1;
		}
		if(acQuery.getEmailSta().equals("1")) {
			level += 1;
		}
		if(level == 2) {
			result.setCode(ResponseMessage.SUCCESS);
			result.setMsg("账号风险评估成功");
			hashMap.put("level",level);
			result.setData(hashMap);
			return result;
		}
		if(level == 3) {
			result.setCode(ResponseMessage.SUCCESS);
			result.setMsg("账号风险评估成功");
			hashMap.put("level",level);
			result.setData(hashMap);
			return result;
		}
		if(level == 1) {
			result.setCode(ResponseMessage.SUCCESS);
			result.setMsg("账号风险评估成功");
			hashMap.put("level",level);
			result.setData(hashMap);
			return result;
		}
		result.setCode(ResponseMessage.FAILE);
		result.setMsg("账号风险评估失败");
		result.setData(new HashMap<String,Integer>().put("level",level));
		return result;
	}

    /**
     * 上传头像(限定jpg和png格式,大小不得超过500K)
     *
     * @param headImg 	图片
     * @param id 		当前登录用户ID
     * @author suyt
     */
    //头像上传的路径
    @Value("${headimg.uploadPath}")
    private String headUrl ;
    @RequestMapping("uploadHeadImg")
	public Result uploadHeadImg(AuthUser authUser, MultipartFile headImg,
			BigInteger id, HttpServletRequest request) {
    	log.info("headImg是否接收到{}",headImg);
    	String path = headUrl;
    	Result result = new Result();
    	// token

    	//Map<String, String> parseToken = JWTDecodeUtil.parseToken(request);
    	// token中解析userId
    	//id = BigInteger.valueOf(Long.parseLong(parseToken.get("userId")));
    	try {
			if(headImg == null || headImg.getSize() <= 0 || headImg.getBytes().length <= 0) {
				result.setCode(ResponseMessage.PARAMETER);
	    		result.setMsg("头像上传失败,文件不可为空");
	    		return result;
			}
		} catch (IOException e) {
			log.error("文件上传异常", e);
			result.setCode(ResponseMessage.FAILE);
			result.setMsg("头像上传失败,系统异常");
			result.setData(e);
			return result;
		}
    	int sta = securityService.uploadHeadImg(headImg, id, path);
    	if(sta == 1) {
    		result.setCode(ResponseMessage.SUCCESS);
    		result.setMsg("头像上传成功");
    		return result;
    	}
    	if(sta == 2) {
    		result.setCode(ResponseMessage.FILESIZEMAX);
    		result.setMsg("大小超出限制");
    		return result;
    	}
    	if(sta == 3) {
    		result.setCode(ResponseMessage.FILETYPE);
    		result.setMsg("类型不是jpg或者png");
    		return result;
    	}
    	result.setCode(ResponseMessage.FAILE);
    	result.setMsg("头像上传失败");
    	return result;
    }

    /**
     * <p>Description: 保存头像路径</p>
     *
     * @param link:
     * @return: com.sailmi.system.entity.Result
     * @Author: syt
     * @Date: 2020/12/1/0001 13:32
     */
	@RequestMapping("saveHeadImgUrl")
    public Result saveHeadImg(AuthUser authUser, String link){
		Result result = new Result();
		BigInteger id = BigInteger.valueOf(authUser.getUserId());
		int sta = securityService.saveHeadImg(id, link);
		if (sta ==1) {
			result.setCode(ResponseMessage.SUCCESS);
			result.setMsg("头像路径保存成功");
		} else {
			result.setCode(ResponseMessage.FAILE);
			result.setMsg("头像路径保存失败");
		}
		return result;
	}

    /**
     * 发送邮箱验证码
     *
     * @param eMailAddr 邮箱地址
     * @param id 		当前登录用户ID
     * @author suyt
     * @return
     */
    @Value("${sendEmail.title}")
    private String eMailTitle;
    @RequestMapping("eMailCode")
    public Result eMailCode(String eMailAddr,BigInteger id) {
    	Result result = new Result();
    	String sexCode = null;

    	try {
    		//生成6位验证码
    		sexCode = String.valueOf((int)((Math.random()*9+1)*100000));
    	}catch(Exception e){
    		log.error("生成验证码异常", e);
    	}

    	//邮件标题
    	String title = this.eMailTitle;
    	//邮件内容
    	String text = "工业测试服务云,你正在修改信息,验证码为:" + sexCode + ",验证码有效时限为20分钟";

        try {
        	//发送邮件
            mailService.sendSimpleMail(eMailAddr, title, text);
        } catch (Exception e) {
            log.error("系统错误::邮件发送异常" + eMailAddr, e);
            result.setCode(ResponseMessage.FAILE);
            result.setMsg("邮件发送失败,系统异常");
            return result;
        }

        try {
        	//验证码存入redis
        	String key = "EmailCode:"+eMailAddr;
        	stringRedisTemplate.boundValueOps(key).set(sexCode, 20, TimeUnit.MINUTES);
        }catch(Exception e) {
        	log.error("验证码存入redis失败",e);
        	result.setCode(ResponseMessage.FAILE);
        	result.setMsg("验证码存入redis失败,系统异常");
        	return result;
        }
        result.setCode(ResponseMessage.SUCCESS);
        result.setMsg("邮件发送成功");
    	return result;
    }

    /**
     * 邮箱验证码是否正确
     * @param code		验证码
     * @param eMailAddr 邮箱地址
     * @param id		当前登录用户ID
     * @author suyt
     */
    @RequestMapping("codeVerify")
    public Result codeVerify(AuthUser authUser, String eMailAddr, String code,BigInteger id) {
    	Result result = new Result();
    	String key = "EmailCode:"+eMailAddr;
		Long expire = stringRedisTemplate.getExpire(key, TimeUnit.MINUTES);
		if(expire<0) {
			result.setCode(ResponseMessage.TIMEOUT);
			result.setMsg("验证码超时");
		}else {
			String redisCode = stringRedisTemplate.opsForValue().get(key);
			if(redisCode!=null) {
				if(redisCode.equals(code)) {//验证码验证成功
					//修改邮箱
					id = BigInteger.valueOf(authUser.getUserId());
					securityService.updateUserEmail(eMailAddr,id);
					result.setCode(ResponseMessage.SUCCESS);
					result.setMsg("验证成功");
				}else {
					result.setCode(ResponseMessage.PARAERROE);
					result.setMsg("验证码错误");
				}
			}
		}
		return result;
    }

    /**
     * 验证邮箱是否唯一
     * @param eMailAddr	邮箱地址
     * @author suyt
     */
    @RequestMapping("eMailOnlyOne")
    public Result eMailOnlyOne(String eMailAddr) {
    	Result result = new Result();
    	try {
    		int sta = securityService.eMailOnlyOne(eMailAddr);
    		if(sta == 1) {
    			result.setCode(ResponseMessage.SUCCESS);
    			result.setMsg("邮箱可用");
    			return result;
    		}
    		if(sta == 2) {
    			result.setCode(ResponseMessage.FAILE);
    			result.setMsg("邮箱已被使用");
    			return result;
    		}
    	}catch (Exception e) {
    		result.setCode(ResponseMessage.FAILE);
    		result.setMsg("校验失败,系统异常");
		}
    	return result;
    }



    /**
     * 验证手机号或邮箱及手机号邮箱插入功能
     * @param userPhone
     * @param userEmail
     * @param code
     * @param status
     * @return
     */
    @RequestMapping(value = "phoneOrEmilCode", method = RequestMethod.POST)
    public String checkPhoneOrEmail(AuthUser authUser, String userPhone, String userEmail, String code, String status, HttpServletRequest request) {
    	Result result = new Result();
//    	Map<String, String> parseToken = JWTDecodeUtil.parseToken(request);
    	//Map<String, String> parseToken=new HashMap<String,String>();
    	//parseToken.put("1", "sdf");
//		if(parseToken!=null && parseToken.size()>0) {
//			String userId = parseToken.get("userId");
			//String userId ="12";
//			if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(code) || StringUtils.isEmpty(status)) {
//				result.setCode(ResponseMessage.PARAMETER);
//				result.setMsg("缺少参数");
//			}else {
				 String userId = authUser.getUserId().toString();
				 result = securityService.checkPhoneOrEemail(userEmail,userPhone,code,status,userId);
//			}
//		}else {
//			result.setCode(ResponseMessage.INVALID_TOKEN);
//			result.setMsg("请登录");
//		}
		return JSON.toJSONString(result);

    }

    /**
     * 修改密码
     * @param newpassword
     * @return
     */
    @RequestMapping(value = "updateUserPassword", method = RequestMethod.POST)
    public String updatePassword(AuthUser authUser, String newpassword,HttpServletRequest request) {
    	Result result = new Result();
    	if(StringUtils.isEmpty(newpassword)) {
    		result.setCode(ResponseMessage.PARAMETER);
			result.setMsg("缺少参数");
    	}else {

//    		Map<String, String> parseToken = JWTDecodeUtil.parseToken(request);
    		//Map<String, String> parseToken=new HashMap<String,String>();
        	//parseToken.put("1", "sdf");
//    		if(parseToken!=null && parseToken.size()>0) {
//    			String userId = parseToken.get("userId");
    		//	String userId ="12";
//    			if(StringUtils.isEmpty(userId) ) {
//    				result.setCode(ResponseMessage.INVALID_TOKEN);
//    				result.setMsg("缺少正确参数token");
//    			}else {
//    				 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//    				  String hashPass = passwordEncoder.encode(newpassword);
					String encrypt = DigestUtil.encrypt(newpassword);
					String userId = authUser.getUserId().toString();
    				int status=securityService.updateUserPass(userId,encrypt);

    				if(status>0) {
    					result.setCode(ResponseMessage.SUCCESS);
    					result.setMsg("修改成功");
    				}else {
    					result.setCode(ResponseMessage.FAILE);
    					result.setMsg("修改失败");
    				}

//    			}
//    		}
    	}


		return JSON.toJSONString(result);

    }


//    @RequestMapping("appeal")
//    public String updatePassword(SecurityAppealEntity securityAppealEntity,HttpServletRequest request) {
//    	Result result = new Result();
//
//    	if(StringUtils.isEmpty(securityAppealEntity.getIDcard()) || StringUtils.isEmpty(securityAppealEntity.getRealName()) || StringUtils.isEmpty(securityAppealEntity.getReason()) || StringUtils.isEmpty(securityAppealEntity.getUserEmail()) || StringUtils.isEmpty(securityAppealEntity.getUserPhone()) ) {
//    		result.setCode(ResponseMessage.PARAMETER);
//			result.setMsg("缺少参数");
//    	}else {
//    		Map<String, String> parseToken = JWTDecodeUtil.parseToken(request);
//    		if(parseToken!=null && parseToken.size()>0) {
//    			String userId = parseToken.get("userId");
//    			securityAppealEntity.setAccountId(new BigInteger(userId));
//    			securityAppealEntity.setAppealDate(String.valueOf(System.currentTimeMillis()));
//    	    	int sta = securityService.insertAppeal(securityAppealEntity);
//    	    	if(sta >0){
//    	    		result.setCode(ResponseMessage.SUCCESS);
//    	    		result.setMsg("申诉成功");
//    	    	}else {
//    		    	result.setCode(ResponseMessage.FAILE);
//    		    	result.setMsg("申诉失败");
//    	    	}
//    		}else {
//    			result.setCode(ResponseMessage.INVALID_TOKEN);
//    			result.setMsg("token参数缺失或错误token");
//    		}
//    	}
//
//
//    		return JSON.toJSONString(result);
//    }



}
