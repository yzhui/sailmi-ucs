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
package com.sailmi.enterprise.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sailmi.system.entity.ServiceEnterprise;
import com.sailmi.system.vo.ServiceEnterpriseVO;

/**
 * 服务企业授权表，表示哪个企业具有此服务的权限，此版本暂不考虑个人用户的服务授权 服务类
 *
 * @author sailmi
 * @since 2020-10-14
 */
public interface IServiceEnterpriseService extends IService<ServiceEnterprise> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param serviceEnterprise
	 * @return
	 */
	IPage<ServiceEnterpriseVO> selectServiceEnterprisePage(IPage<ServiceEnterpriseVO> page, ServiceEnterpriseVO serviceEnterprise);

}
