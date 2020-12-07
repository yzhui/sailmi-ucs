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
import com.sailmi.core.tool.api.R;
import com.sailmi.system.entity.*;
import com.sailmi.system.feign.IEnterpriseFeign;
import com.sailmi.system.service.IRoleService;
import lombok.AllArgsConstructor;
import com.sailmi.core.boot.tenant.TenantId;
import com.sailmi.core.mp.base.BaseServiceImpl;
import com.sailmi.core.tool.constant.AppConstant;
import com.sailmi.core.tool.utils.Func;
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
	private IEnterpriseFeign enterpriseFeign;
	private IRoleService roleService;
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
		}


		if(tenant!=null && tenant.getEnterpriseId()!=null && tenant.getEnterpriseId().length()>0){//选择了某企业，创建企业管理员角色
			Role role = new Role();
			role.setEnterpriseId(Long.valueOf(tenant.getEnterpriseId()));
			role.setParentId(0l);
			role.setRoleName("租户管理员"+tenant.getEnterpriseId());
			role.setRoleAlias(tenant.getEnterpriseId()+"tenant_administrator");
			role.setSort(2);
			role.setIsDeleted(0);
			roleService.save(role);
			//	R<UserEnterprise> userEnterpriseR = enterpriseFeign.queryUserEnterpriseInfo(tenant.getEnterpriseId());
			//	if(userEnterpriseR!=null && userEnterpriseR.getData()!=null && userEnterpriseR.getData().getUserId()!=null){
			//TODO
			//设定租户管理员登陆人和role的绑定还是在用户中设定呢
			//	}
//			Enterprise enterprise = new Enterprise();
//			enterprise.setTenantId(tenant.getTenantId());
//			enterprise.setId(Long.valueOf(tenant.getEnterpriseId()));
//			enterpriseFeign.update(enterprise);
		}else{
			tenant.setEnterpriseId("0");
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
