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
package com.sailmi.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.sailmi.core.boot.ctrl.AppController;
import com.sailmi.core.mp.support.Condition;
import com.sailmi.core.secure.AuthUser;
import com.sailmi.core.tool.api.R;
import com.sailmi.core.tool.constant.AppConstant;
import com.sailmi.core.tool.node.INode;
import com.sailmi.core.tool.utils.Func;
import com.sailmi.system.entity.Role;
import com.sailmi.system.service.IRoleService;
import com.sailmi.system.vo.RoleVO;
import com.sailmi.system.wrapper.RoleWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Api(value = "角色", tags = "角色管理接口")
public class RoleController extends AppController {

	private IRoleService roleService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入role")
	public R<RoleVO> detail(Role role) {
		Role detail = roleService.getOne(Condition.getQueryWrapper(role));
		return R.data(RoleWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleName", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "roleAlias", value = "角色别名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入role")
	public R<List<INode>> list(AuthUser authUser,@ApiIgnore @RequestParam Map<String, Object> role) {
		QueryWrapper<Role> queryWrapper = Condition.getQueryWrapper(role, Role.class);
		queryWrapper.eq("is_sys",0);
		if(authUser!=null && authUser.getEnterpriseId()!=null) {
			queryWrapper.eq("enterprise_id", authUser.getEnterpriseId());
		}
		List<Role> list = roleService.list(queryWrapper);//查询该公司的角色列表

		QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
		roleQueryWrapper.eq("is_sys",1);
		List<Role> commonList = roleService.list(queryWrapper);//查询该公司的角色列表
		if(commonList!=null && commonList.size()>0){
			list.addAll(commonList);
		}
		return R.data(RoleWrapper.build().listNodeVO(list));
	}

	/**
	 * 获取角色树形结构
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<RoleVO>> tree(String tenantId, AuthUser authUser) {
		List<RoleVO> tree = roleService.tree(Func.toStr(tenantId, authUser.getTenantId()));
		return R.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入role")
	public R submit(@Valid @RequestBody Role role, AuthUser user) {
		if (Func.isEmpty(role.getId())) {
			String enterpriseId= "000000";
			role.setEnterpriseId(Long.valueOf(enterpriseId));
		}
		if(Func.isEmpty(role.getIsSys())){// 1 是默认的系统角色，企业都可见，但是不能删除与修改
			role.setIsSys("0");
		}
		return R.status(roleService.saveOrUpdate(role));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(AuthUser authUser,@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		R<String> status=null;
			if(ids.contains("-2") || ids.contains("-3")){//包含这两个角色，则不能删除
				status = R.data(400, "", "是系统角色，没有删除权限");
			}else{
				boolean flag= roleService.removeByIds(Func.toLongList(ids));
				status = R.data(200, "", "删除成功");
			}
		return status;
	}

	/**
	 * 设置菜单权限
	 *
	 * @param roleIds
	 * @param menuIds
	 * @return
	 */
	@PostMapping("/grant")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	public R grant(AuthUser authUser,@ApiParam(value = "roleId集合", required = true) @RequestParam String roleIds,
				   @ApiParam(value = "menuId集合", required = true) @RequestParam String menuIds) {
		R<String> status=null;

		if(roleIds.contains("-2") || roleIds.contains("-3")){
			status = R.data(400, "", "该角色菜单是固定的，没有修改权限");
		}else{
			boolean temp = roleService.grant(Func.toLongList(roleIds), Func.toLongList(menuIds));
			status = R.data(200, "", "修改成功");
		}
		return status;
	}

}
