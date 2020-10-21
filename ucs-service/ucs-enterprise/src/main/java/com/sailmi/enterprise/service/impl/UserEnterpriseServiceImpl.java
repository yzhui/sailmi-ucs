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

import com.sailmi.enterprise.mapper.UserEnterpriseMapper;
import com.sailmi.enterprise.service.IUserEnterpriseService;
import com.sailmi.system.entity.UserEnterprise;
import com.sailmi.system.vo.UserEnterpriseVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 用户-企业关系表 服务实现类
 *
 * @author sailmi
 * @since 2020-10-16
 */
@Service
public class UserEnterpriseServiceImpl extends ServiceImpl<UserEnterpriseMapper, UserEnterprise> implements IUserEnterpriseService {

	@Override
	public IPage<UserEnterpriseVO> selectUserEnterprisePage(IPage<UserEnterpriseVO> page, UserEnterpriseVO userEnterprise) {
		return page.setRecords(baseMapper.selectUserEnterprisePage(page, userEnterprise));
	}

}
