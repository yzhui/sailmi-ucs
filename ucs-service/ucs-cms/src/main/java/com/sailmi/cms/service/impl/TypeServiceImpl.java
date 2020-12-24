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
package com.sailmi.cms.service.impl;

import com.sailmi.cms.utils.TreeUtils;
import com.sailmi.cms.entity.Type;
import com.sailmi.cms.utils.TreeEntity;
import com.sailmi.cms.vo.TypeVO;
import com.sailmi.cms.mapper.TypeMapper;
import com.sailmi.cms.service.ITypeService;
import com.sailmi.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 内容分类 服务实现类
 *
 * @author sailmi
 * @since 2020-12-16
 */
@Service
public class TypeServiceImpl extends BaseServiceImpl<TypeMapper, Type> implements ITypeService {

	@Override
	public IPage<TypeVO> selectTypePage(IPage<TypeVO> page, TypeVO type) {
		return page.setRecords(baseMapper.selectTypePage(page, type));
	}

	/**
	 * 下载中心 下载分类树
	 */
	@Override
	public List<TreeEntity> downTypeTree() {
		List<TreeEntity> allDownType = baseMapper.downTypeTree();
		List<TreeEntity> treeEntities = TreeUtils.treeMenuList(allDownType,0l);
		return treeEntities;
	}

}
