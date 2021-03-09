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

import com.sailmi.system.entity.EnterpriseDetails;
import com.sailmi.system.vo.EnterpriseDetailsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 企业详细信息表 Mapper 接口
 *
 * @author sailmi
 * @since 2020-10-23
 */
public interface EnterpriseDetailsMapper extends BaseMapper<EnterpriseDetails> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param enterpriseDetails
	 * @return
	 */
	List<EnterpriseDetailsVO> selectEnterpriseDetailsPage(IPage page, EnterpriseDetailsVO enterpriseDetails);

	/**
	 * <p>Description: 插入企业详细信息</p>
	 *
	 * @param enterpriseDetails:
	 * @return: com.sailmi.system.entity.EnterpriseDetails
	 * @Author: syt
	 * @Date: 2020/10/24/0024 11:01
	 */
	int insertDetail(EnterpriseDetails enterpriseDetails);
}
