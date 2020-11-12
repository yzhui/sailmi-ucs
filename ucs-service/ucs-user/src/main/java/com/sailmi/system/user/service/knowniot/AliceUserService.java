package com.sailmi.system.user.service.knowniot;

import com.sailmi.core.tool.api.R;
import com.sailmi.core.tool.utils.Func;
import com.sailmi.system.user.mapper.AliceUserMapper;
import com.sailmi.system.user.persist.AliceUserModel;
import com.sailmi.system.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author Tomonori
 * @mail gutrse3321@live.com
 * @data 2020-11-10 20:01
 */
@Service
public class AliceUserService implements IAliceUserService {

	@Autowired
	IUserService iUcsUserService;

	@Autowired(required = false)
	AliceUserMapper userMapper;

	/**
	 * 创建员工，关联企业、部门
	 *
	 * @return
	 */
	@Override
	public String createKnowniotUser(AliceUserModel model) {
		int count = iUcsUserService.queryUnikePhone(model.getUserPhone());
		if (count>0) {
			return "该手机号已经被注册: " + model.getUserPhone();
		}

		if (insertUser(model) == 0) {
			return "创建员工异常";
		}
//		String userId = userMapper.queryUserIdByPhone(model.getUserPhone());
		return "0";
	}

	@Transactional
	protected int insertUser(AliceUserModel model) {
		return userMapper.insertUser(model);
	}
}
