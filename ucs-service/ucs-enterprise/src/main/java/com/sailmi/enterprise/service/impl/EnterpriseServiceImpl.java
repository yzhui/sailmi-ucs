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
package com.sailmi.enterprise.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sailmi.core.secure.AuthUser;
import com.sailmi.core.tool.utils.Func;
import com.sailmi.system.entity.Enterprise;
import com.sailmi.system.entity.ServiceEnterprise;
import com.sailmi.system.vo.EnterpriseVO;
import com.sailmi.enterprise.mapper.EnterpriseMapper;
import com.sailmi.enterprise.service.IEnterpriseService;
import com.sailmi.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  服务实现类
 *
 * @author sailmi
 * @since 2020-09-02
 */
@Service
public class EnterpriseServiceImpl extends BaseServiceImpl<EnterpriseMapper, Enterprise> implements IEnterpriseService {

	@Autowired
	private  ServiceEnterpriseServiceImpl serviceEnterpriseServiceImpl;

	@Override
	public IPage<EnterpriseVO> selectEnterprisePage(IPage<EnterpriseVO> page, EnterpriseVO enterprise) {
		return page.setRecords(baseMapper.selectEnterprisePage(page, enterprise));
	}

	@Override
	public List<String> enterpriServiceTreeKeys(String enterpriseId) {
		QueryWrapper<ServiceEnterprise> serviceEnterpriseQueryWrapper = new QueryWrapper<>();
		serviceEnterpriseQueryWrapper.eq("enterprise_id",enterpriseId);
		List<ServiceEnterprise> list = serviceEnterpriseServiceImpl.list(serviceEnterpriseQueryWrapper);
		return list.stream().map(servicebao->Func.toStr(servicebao.getServiceId())).collect(Collectors.toList());
	}

	@Override
	public boolean grantservices(String enterpriseId, List<Long> serviceIds,AuthUser user) {
		//删除配置的服务包集合
		QueryWrapper<ServiceEnterprise> serviceEnterpriseQueryWrapper = new QueryWrapper<>();
		serviceEnterpriseQueryWrapper.eq("enterprise_id",enterpriseId);
		boolean remove = serviceEnterpriseServiceImpl.remove(serviceEnterpriseQueryWrapper);
		//批量插入企业的服务包
		ArrayList<ServiceEnterprise> serviceEnterprises = new ArrayList<>();
		serviceIds.stream().forEach(serviceId->{
			ServiceEnterprise serviceEnterprise = new ServiceEnterprise();
			serviceEnterprise.setServiceId(serviceId);  //服务包ID
			serviceEnterprise.setEnterpriseId(Long.valueOf(enterpriseId));//企业ID
			serviceEnterprise.setIsDeleted(0);//删除状态
			serviceEnterprise.setStatus(0);//状态
			if(user!=null && user.getUserId()!=null) {
				serviceEnterprise.setCreateUser(user.getUserId());//创建人
				serviceEnterprise.setUpdateUser(user.getUserId());//修改人
			}


			serviceEnterprise.setCreateTime(new Date());
			serviceEnterprise.setUpdateTime(new Date());
			serviceEnterprises.add(serviceEnterprise);
		});
		return serviceEnterpriseServiceImpl.saveBatch(serviceEnterprises);
	}

	@Override
	public Enterprise saveEnterpriseInfo(Enterprise enterprise) {

		return EnterpriseMapper.insertEnterpriseInfo(enterprise);
	}

}
