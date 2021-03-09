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

import com.sailmi.system.entity.EnterpriseFinance;
import com.sailmi.system.vo.EnterpriseFinanceVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 企业财务信息表 服务类
 *
 * @author sailmi
 * @since 2020-10-23
 */
public interface IEnterpriseFinanceService extends IService<EnterpriseFinance> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param enterpriseFinance
	 * @return
	 */
	IPage<EnterpriseFinanceVO> selectEnterpriseFinancePage(IPage<EnterpriseFinanceVO> page, EnterpriseFinanceVO enterpriseFinance);

	/**
	 * <p>Description: 企业财务信息</p>
	 *
	 * @param enterpriseFinance:
	 * @return: void
	 * @Author: syt
	 * @Date: 2020/10/24/0024 12:58
	 */
	int saveFiance(EnterpriseFinance enterpriseFinance);
}
