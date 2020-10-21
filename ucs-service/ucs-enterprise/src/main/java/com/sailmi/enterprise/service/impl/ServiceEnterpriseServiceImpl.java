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


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sailmi.enterprise.mapper.ServiceEnterpriseMapper;
import com.sailmi.enterprise.service.IServiceEnterpriseService;
import com.sailmi.system.entity.ServiceEnterprise;
import com.sailmi.system.vo.ServiceEnterpriseVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 服务企业授权表，表示哪个企业具有此服务的权限，此版本暂不考虑个人用户的服务授权 服务实现类
 *
 * @author sailmi
 * @since 2020-10-14
 */
@Service
public class ServiceEnterpriseServiceImpl extends ServiceImpl<ServiceEnterpriseMapper, ServiceEnterprise> implements IServiceEnterpriseService {

	@Override
	public IPage<ServiceEnterpriseVO> selectServiceEnterprisePage(IPage<ServiceEnterpriseVO> page, ServiceEnterpriseVO serviceEnterprise) {
		return page.setRecords(baseMapper.selectServiceEnterprisePage(page, serviceEnterprise));
	}

}
