package com.sailmi.system.user.controller;

import com.alibaba.fastjson.JSON;
import com.sailmi.core.secure.AuthUser;
import com.sailmi.system.user.entity.RealUserEntity;
import com.sailmi.system.user.service.RealUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

/**
 * 用户真实信息Controller
 *
 * @author syt
 * @version 2019-07-26
 */
@RestController
@RequestMapping(value = "/realuser")
public class RealinfoController {

	@Autowired(required = false)
	private RealUserService realUserService;

	/**
	 * 真实信息查询(产品需求相关数据不可查询)
	 * @param realinfo
	 * @return
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public String list(AuthUser authUser, RealUserEntity realinfo) {
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		BigInteger b = BigInteger.valueOf(authUser.getUserId());
		realinfo.setUserId(b);
		List<RealUserEntity> query = realUserService.query(realinfo);
		if(query.size() > 0 ) {
			hashMap.put("status", 1);
			hashMap.put("msg", "查询成功!");
			hashMap.put("result", query);
		}else {
			hashMap.put("status", 0);
			hashMap.put("msg", "查询失败!");
			hashMap.put("result", null);
		}
		return JSON.toJSONString(hashMap);
	}

	/**
	 * 真实信息插入
	 * @param realinfo
	 * @return
	 */
	@RequestMapping(value = "upsert", method = RequestMethod.POST)
	public String save(AuthUser authUser, RealUserEntity realinfo) {
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		BigInteger b = BigInteger.valueOf(authUser.getUserId());
//		BigInteger b = BigInteger.valueOf(1301788793255452722l);
//		realinfo.setId(b);
		realinfo.setUserId(b);
		int upsert = realUserService.upsert(realinfo);
		if(upsert == 1) {
			hashMap.put("status", 1);
			hashMap.put("msg", "插入成功!");
			hashMap.put("result", null);
		}else {
			hashMap.put("status", 0);
			hashMap.put("msg", "插入失败!");
			hashMap.put("result", null);
		}
		return JSON.toJSONString(hashMap);
	}

	/**
	 * 身份证上传
	 * 使用ucs上传组件后该接口废弃
	 * @param realinfo
	 * @return
	 */
	/*@RequestMapping(value = "/IDCard", method = RequestMethod.POST)
	public String IDCard(RealUserEntity realinfo) {
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		int upID = realUserService.IDCard(realinfo);
		if(upID == 1) {
			hashMap.put("status", 1);
			hashMap.put("msg", "上传成功!");

		}else {
			hashMap.put("status", 0);
			hashMap.put("msg", "上传失败!");
		}
		return JSON.toJSONString(hashMap);
	}*/

}
