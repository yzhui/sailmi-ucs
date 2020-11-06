package com.sailmi.system.user.service.impl;

import com.sailmi.system.user.entity.RealUserEntity;
import com.sailmi.system.user.mapper.RealUserMapper;
import com.sailmi.system.user.service.RealUserService;
import com.sailmi.system.user.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

/**
 * @author syt
 */
@Service
public class RealUserServiceImpl implements RealUserService {

	/**
	 * 注入用户真实信息MAPPER
	 */
	@Autowired(required = false)
	private RealUserMapper realUserMapper;

	/**
	 * 根据ID获取用户真实信息
	 *
	 * @param userId 输入参数：用户ID
	 * @return 返回值：用户真实信息实体
	 */
	@Override
	public RealUserEntity get(BigInteger userId) {
		RealUserEntity real = realUserMapper.get(userId);
		return real;
	}

	/**
	 * 用户信息列表
	 *
	 * @param realUser 输入参数：用户ID
	 * @return 返回值：用户信息
	 */
	@Override
	public List<RealUserEntity> query(RealUserEntity realUser) {
		List<RealUserEntity> queryLi = realUserMapper.query(realUser);
		return queryLi;
	}

	/**
	 * 修改、增加用户真实信息 输入参数：用户真实信息实体类
	 *
	 * @param reUser
	 * @return 返回值：1成功，0失败
	 */
	String url , url2, url3;
	@Override
	public int upsert(RealUserEntity reUser) {

		int status,acSta = 0;
		reUser.setImgInfo(url);
		reUser.setImgInfo2(url2);
		reUser.setImgInfo3(url3);
		status = realUserMapper.insert(reUser);
		acSta = realUserMapper.insertAccount(reUser.getRealName(), reUser.getId());
		if (status == 1 & acSta == 1) {
			realUserMapper.realStatus(reUser.getId());
			return 1;
		}
		return 0;
	}

	/**
	 * 上传
	 */
	@Override
	public int IDCard(RealUserEntity reUser) {
		HashMap<String, Object> uploadData;
		try {
			if (reUser.getFile() != null) {
				uploadData = UploadUtil.uploadData(reUser.getFile(), "/usr/share/nginx/html/ucs/ftpuser", 0);
//				uploadData = UploadUtil.uploadData(reUser.getFile(), "/opt/microservice/update_file/" + reUser.getRealName() + "/uuserIDCard/", 0);
				url = (String)uploadData.get("name");
				if(uploadData.get("status").toString().equals("1")) {
					reUser.setImgInfo(uploadData.get("url").toString());
					realUserMapper.insertImgUrl(reUser);
					return 1;
				}
				return 0;
			}
			if (reUser.getFile2() != null) {
//				uploadData = UploadUtil.uploadData(reUser.getFile2(), "D:/IDCard/" + reUser.getRealName() + "/uuserIDCard/", 0);
				uploadData = UploadUtil.uploadData(reUser.getFile(), "/usr/share/nginx/html/ucs/ftpuser" + reUser.getRealName() + "/uuserIDCard/", 0);
				url2 = (String)uploadData.get("name");
				if(uploadData.get("status").toString().equals("1")) {
					return 1;
				}
				return 0;
			}
			if (reUser.getFile3() != null) {
//				uploadData = UploadUtil.uploadData(reUser.getFile3(), "D:/IDCard/" + reUser.getRealName() + "/uuserIDCard/", 0);
				uploadData = UploadUtil.uploadData(reUser.getFile(), "/usr/share/nginx/html/ucs/ftpuser" + reUser.getRealName() + "/uuserIDCard/", 0);
				url3 = (String)uploadData.get("name");
				if(uploadData.get("status").toString().equals("1")) {
					return 1;
				}
				return 0;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;


	}

}
