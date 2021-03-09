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
package com.sailmi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sailmi.core.tool.node.ForestNodeMerger;
import com.sailmi.core.tool.utils.BeanUtil;
import com.sailmi.core.tool.utils.Func;
import com.sailmi.system.entity.Dept;
import com.sailmi.system.mapper.DeptMapper;
import com.sailmi.system.service.IDeptService;
import com.sailmi.system.vo.DeptVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

	@Override
	public IPage<DeptVO> selectDeptPage(IPage<DeptVO> page, DeptVO dept) {
		return page.setRecords(baseMapper.selectDeptPage(page, dept));
	}

	@Override
	public List<DeptVO> tree(String tenantId) {
		return ForestNodeMerger.merge(baseMapper.tree(tenantId));
	}

	@Override
	public String getDeptIds(String tenantId, String deptNames) {
		List<Dept> deptList = baseMapper.selectList(Wrappers.<Dept>query().lambda().eq(Dept::getEnterpriseId, tenantId).in(Dept::getDeptName, Func.toStrList(deptNames)));
		if (deptList != null && deptList.size() > 0) {
			return deptList.stream().map(dept -> Func.toStr(dept.getId())).distinct().collect(Collectors.joining(","));
		}
		return null;
	}

	@Override
	public List<String> getDeptNames(String deptIds) {
		return baseMapper.getDeptNames(Func.toLongArray(deptIds));
	}

	@Override
	public List<DeptVO> queryEnterTree(String enterpriseId) {
		List<DeptVO> deptVOS = new ArrayList<>();
		QueryWrapper<Dept> deptQueryWrapper = new QueryWrapper<>();
		deptQueryWrapper.eq("enterprise_id",enterpriseId);
		List<Dept> depts = baseMapper.selectList(deptQueryWrapper);
		if(depts!=null && depts.size()>0){
			depts.stream().forEach(dept -> {
				DeptVO deptVO = BeanUtil.copy(dept, DeptVO.class);
				deptVOS.add(deptVO);
			});
		}
		return ForestNodeMerger.merge(deptVOS);
	}

}
