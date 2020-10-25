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
package com.sailmi.enterprise.mapper;

import com.sailmi.system.entity.Enterprise;
import com.sailmi.system.vo.EnterpriseVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 *  Mapper 接口
 *
 * @author sailmi
 * @since 2020-09-02
 */
public interface EnterpriseMapper extends BaseMapper<Enterprise> {


	 Enterprise insertEnterpriseInfo(Enterprise enterprise);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param enterprise
	 * @return
	 */
	List<EnterpriseVO> selectEnterprisePage(IPage page, EnterpriseVO enterprise);

	/**
	 * <p>Description: 社会统一验证码</p>
	 *

	 * @return: java.util.List<java.lang.String>
	 * @Author: syt
	 * @Date: 2020/10/25/0025 21:39
	 */
	List<String> getAll();
}
