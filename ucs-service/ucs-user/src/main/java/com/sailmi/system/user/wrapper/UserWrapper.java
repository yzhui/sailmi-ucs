/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sailmi.system.user.wrapper;

import com.sailmi.core.mp.support.BaseEntityWrapper;
import com.sailmi.core.tool.api.R;
import com.sailmi.core.tool.utils.BeanUtil;
import com.sailmi.core.tool.utils.Func;
import com.sailmi.core.tool.utils.SpringUtil;
import com.sailmi.system.feign.IDictClient;
import com.sailmi.system.user.entity.User;
import com.sailmi.system.user.service.IUserService;
import com.sailmi.system.user.vo.UserVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

	private static IUserService userService;
	private static IDictClient dictClient;

	static {
		userService = SpringUtil.getBean(IUserService.class);
		dictClient = SpringUtil.getBean(IDictClient.class);
	}

	public static UserWrapper build() {
		return new UserWrapper();
	}

	@Override
	public UserVO entityVO(User user) {
		UserVO userVO = BeanUtil.copy(user, UserVO.class);
		List<String> roleName =new ArrayList<>();

			// 查询用户的role信息
		if(user.getDefaultEnterpriseId()!=null) {
			List<String> roles = userService.queryUserRoles(user.getId().toString(), user.getDefaultEnterpriseId().toString(),user.getTenantId());
			if(roles!=null && roles.size()>0) {
				 roleName = userService.getRoleName(roles.get(0));
				userVO.setRoleName(Func.join(roleName));
			}
		}
		List<String> deptName = new ArrayList<>();
		// 查询用户的部门信息
		//userService.getDeptName(user.getDeptId());
		//userVO.setDeptName(Func.join(deptName));
		userVO.setDeptName("技术部");


		R<String> dict = dictClient.getValue("sex", Func.toInt(user.getSex()));
		if (dict.isSuccess()) {
			userVO.setSexName(dict.getData());
		}
		return userVO;
	}

}
