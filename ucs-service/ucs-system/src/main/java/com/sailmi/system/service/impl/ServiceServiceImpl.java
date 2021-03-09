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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.sailmi.core.secure.AuthUser;
import com.sailmi.core.tool.node.ForestNodeMerger;
import com.sailmi.core.tool.utils.Func;
import com.sailmi.system.entity.RoleMenu;
import com.sailmi.system.entity.ServiceEntity;
import com.sailmi.system.entity.ServiceMenu;
import com.sailmi.system.mapper.ServiceMapper;
import com.sailmi.system.service.IServiceService;
import com.sailmi.system.vo.ServiceVO;
import com.sailmi.system.wrapper.ServiceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 可提供的服务清单，企业可以通过服务清单 服务实现类
 *
 * @author sailmi
 * @since 2020-09-09
 */
@Service
public class ServiceServiceImpl extends ServiceImpl<ServiceMapper, ServiceEntity> implements IServiceService {

	@Autowired
	private  ServiceMenuServiceImpl serviceMenuServiceImpl;

	@Override
	public IPage<ServiceVO> selectServicePage(IPage<ServiceVO> page, ServiceVO service) {
		return page.setRecords(baseMapper.selectServicePage(page, service));
	}

	@Override
	public List<String> serviceTreeKeys(String serviceId) {
		QueryWrapper<ServiceMenu> serviceMenuQueryWrapper = new QueryWrapper<>();
		serviceMenuQueryWrapper.eq("service_id",serviceId);
		List<ServiceMenu> list = serviceMenuServiceImpl.list(serviceMenuQueryWrapper);
		return  list.stream().map(serviceMenu->Func.toStr(serviceMenu.getMenuId())).collect(Collectors.toList());
	}

	@Override
	public boolean grant(String serviceId, List<Long> menuIds) {
// 删除服务包配置的菜单集合
		QueryWrapper<ServiceMenu> serviceMenuQueryWrapper = new QueryWrapper<>();
		serviceMenuQueryWrapper.eq("service_id",serviceId);
		serviceMenuServiceImpl.remove(serviceMenuQueryWrapper);
		// 组装配置
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("");
		List<ServiceMenu> serviceMenus = new ArrayList<>();
		menuIds.forEach(menuId -> {
			ServiceMenu serviceMenu = new ServiceMenu();
			serviceMenu.setServiceId(Long.valueOf(serviceId));
			serviceMenu.setMenuId(menuId);
			serviceMenu.setCreateTime(new Date());
			serviceMenu.setUpdateTime(new Date());
			serviceMenus.add(serviceMenu);
		});
		// 新增配置
		return serviceMenuServiceImpl.saveBatch(serviceMenus);

	}

	@Override
	public List<ServiceVO> grantTree(AuthUser authUser) {
		QueryWrapper<ServiceEntity> serviceEntityQueryWrapper = new QueryWrapper<>();
		if(authUser!=null && authUser.getEnterpriseId()!=null){
			serviceEntityQueryWrapper.eq("enterprise_id",authUser.getEnterpriseId());
		}
		List<ServiceEntity> serviceEntities = baseMapper.selectList(serviceEntityQueryWrapper);
		ArrayList<ServiceVO> serviceVOS = new ArrayList<>();
		if(serviceEntities!=null && serviceEntities.size()>0){
			serviceEntities.stream().forEach(serviceEntity->{
				ServiceVO serviceVO = ServiceWrapper.build().entityVO(serviceEntity);
				serviceVOS.add(serviceVO);
			});
		}
		return ForestNodeMerger.merge(serviceVOS);
	}



}
