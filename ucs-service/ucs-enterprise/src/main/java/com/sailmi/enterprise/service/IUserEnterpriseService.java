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
package com.sailmi.enterprise.service;

import com.sailmi.system.entity.UserEnterprise;
import com.sailmi.system.vo.UserEnterpriseVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 用户-企业关系表 服务类
 *
 * @author sailmi
 * @since 2020-10-16
 */
public interface IUserEnterpriseService extends IService<UserEnterprise> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param userEnterprise
	 * @return
	 */
	IPage<UserEnterpriseVO> selectUserEnterprisePage(IPage<UserEnterpriseVO> page, UserEnterpriseVO userEnterprise);

}
