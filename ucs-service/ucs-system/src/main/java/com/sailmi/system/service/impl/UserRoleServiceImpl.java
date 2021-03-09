/**
 * Copyright (c) 2018-2028.
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
package com.sailmi.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sailmi.system.entity.UserRole;
import com.sailmi.system.mapper.UserRoleMapper;
import com.sailmi.system.service.IUserRoleService;
import com.sailmi.system.vo.UserRoleVO;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *  服务实现类
 *
 * @author sailmi
 * @since 2020-10-23
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

	@Override
	public IPage<UserRoleVO> selectUserRolePage(IPage<UserRoleVO> page, UserRoleVO userRole) {
		return page.setRecords(baseMapper.selectUserRolePage(page, userRole));
	}

	@Override
	public List<UserRole> queryUserlistByRoleId(String roleId) {
		QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
		userRoleQueryWrapper.eq("role_id",roleId);
		return baseMapper.selectList(userRoleQueryWrapper);
	}

}
