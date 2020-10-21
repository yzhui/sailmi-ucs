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
package com.sailmi.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sailmi.system.entity.ServiceMenu;
import com.sailmi.system.mapper.ServiceMenuMapper;
import com.sailmi.system.service.IServiceMenuService;
import com.sailmi.system.vo.ServiceMenuVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 服务功能包设定，表明此功能属于哪个服务包，只有享受此服务包的用户才能访问这个功能 服务实现类
 *
 * @author sailmi
 * @since 2020-09-17
 */
@Service
public class ServiceMenuServiceImpl extends ServiceImpl<ServiceMenuMapper, ServiceMenu> implements IServiceMenuService {

	@Override
	public IPage<ServiceMenuVO> selectServiceMenuPage(IPage<ServiceMenuVO> page, ServiceMenuVO serviceMenu) {
		return page.setRecords(baseMapper.selectServiceMenuPage(page, serviceMenu));
	}

}
