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
import com.sailmi.enterprise.mapper.EnterpriseDetailsMapper;
import com.sailmi.enterprise.service.IEnterpriseDetailsService;
import com.sailmi.system.entity.EnterpriseDetails;
import com.sailmi.system.vo.EnterpriseDetailsVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 企业详细信息表 服务实现类
 *
 * @author sailmi
 * @since 2020-10-23
 */
@Service
public class EnterpriseDetailsServiceImpl extends ServiceImpl<EnterpriseDetailsMapper, EnterpriseDetails> implements IEnterpriseDetailsService {

	@Override
	public IPage<EnterpriseDetailsVO> selectEnterpriseDetailsPage(IPage<EnterpriseDetailsVO> page, EnterpriseDetailsVO enterpriseDetails) {
		return page.setRecords(baseMapper.selectEnterpriseDetailsPage(page, enterpriseDetails));
	}

}
