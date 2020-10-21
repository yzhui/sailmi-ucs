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
package com.sailmi.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import com.sailmi.core.boot.tenant.TenantId;
import com.sailmi.core.mp.base.BaseServiceImpl;
import com.sailmi.core.tool.constant.AppConstant;
import com.sailmi.core.tool.utils.Func;
import com.sailmi.system.entity.Dept;
import com.sailmi.system.entity.Role;
import com.sailmi.system.entity.Tenant;
import com.sailmi.system.mapper.DeptMapper;
import com.sailmi.system.mapper.RoleMapper;
import com.sailmi.system.mapper.TenantMapper;
import com.sailmi.system.service.ITenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class TenantServiceImpl extends BaseServiceImpl<TenantMapper, Tenant> implements ITenantService {

	private final TenantId tenantId;
	private final RoleMapper roleMapper;
	private final DeptMapper deptMapper;

	@Override
	public IPage<Tenant> selectTenantPage(IPage<Tenant> page, Tenant tenant) {
		return page.setRecords(baseMapper.selectTenantPage(page, tenant));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveTenant(Tenant tenant) {
		if (Func.isEmpty(tenant.getId())) {
			List<Tenant> tenants = baseMapper.selectList(Wrappers.<Tenant>query().lambda().eq(Tenant::getIsDeleted, AppConstant.DB_NOT_DELETED));
			List<String> codes = tenants.stream().map(Tenant::getTenantId).collect(Collectors.toList());
			String tenantId = getTenantId(codes);//生成新的tennantId
			tenant.setTenantId(tenantId);
			// 新建租户对应的默认角色
			/*
			// 新建租户时，建立对应的管理员企业
			String enterpriseId="000000";
			Role role = new Role();
			role.setEnterpriseId(enterpriseId);
			role.setParentId(0L);
			role.setRoleName("管理员");
			role.setRoleAlias("admin");
			role.setSort(2);
			role.setIsDeleted(0);
			roleMapper.insert(role);
			// 新建租户对应的默认部门
//			Dept dept = new Dept();
//			dept.setTenantId(tenantId);
//			dept.setParentId(0L);
//			dept.setDeptName(tenant.getTenantName());
//			dept.setFullName(tenant.getTenantName());
//			dept.setSort(2);
//			dept.setIsDeleted(0);
//			deptMapper.insert(dept);
			*/
		}
		return super.saveOrUpdate(tenant);
	}

	private String getTenantId(List<String> codes) {
		String code = tenantId.generate();
		if (codes.contains(code)) {
			return getTenantId(codes);
		}
		return code;
	}

}
