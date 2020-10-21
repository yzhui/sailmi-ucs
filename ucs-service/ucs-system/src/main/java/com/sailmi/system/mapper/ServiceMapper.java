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
package com.sailmi.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.system.entity.ServiceEntity;
import com.sailmi.system.vo.ServiceVO;

import java.util.List;

/**
 * 可提供的服务清单，企业可以通过服务清单 Mapper 接口
 *
 * @author sailmi
 * @since 2020-09-09
 */
public interface ServiceMapper extends BaseMapper<ServiceEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param service
	 * @return
	 */
	List<ServiceVO> selectServicePage(IPage page, ServiceVO service);

}
