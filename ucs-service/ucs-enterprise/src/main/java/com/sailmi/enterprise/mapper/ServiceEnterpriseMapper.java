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
package com.sailmi.enterprise.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.system.entity.ServiceEnterprise;
import com.sailmi.system.vo.ServiceEnterpriseVO;

import java.util.List;


/**
 * 服务企业授权表，表示哪个企业具有此服务的权限，此版本暂不考虑个人用户的服务授权 Mapper 接口
 *
 * @author sailmi
 * @since 2020-10-14
 */
public interface ServiceEnterpriseMapper extends BaseMapper<ServiceEnterprise> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param serviceEnterprise
	 * @return
	 */
	List<ServiceEnterpriseVO> selectServiceEnterprisePage(IPage page, ServiceEnterpriseVO serviceEnterprise);

}
