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
import com.sailmi.system.entity.SystemEntity;
import com.sailmi.system.entity.Tenant;
import com.sailmi.system.service.ISystemService;
import com.sailmi.system.service.ITenantService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.sailmi.core.boot.ctrl.AppController;
import com.sailmi.core.mp.support.Condition;
import com.sailmi.core.secure.AuthUser;
import com.sailmi.core.secure.annotation.PreAuth;
import com.sailmi.core.tool.api.R;
import com.sailmi.core.tool.constant.RoleConstant;
import com.sailmi.core.tool.support.Kv;
import com.sailmi.core.tool.utils.Func;
import com.sailmi.system.entity.Menu;
import com.sailmi.system.service.IMenuService;
import com.sailmi.system.vo.MenuVO;
import com.sailmi.system.wrapper.MenuWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
@Api(value = "菜单", tags = "菜单管理接口")
public class MenuController extends AppController {

	private IMenuService menuService;
	private ISystemService systemService;
	private ITenantService tenantService;
	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@PreAuth(RoleConstant.HAS_ROLE_TENANT_ADMIN)
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入menu")
	public R<MenuVO> detail(Menu menu) {
		Menu detail = menuService.getOne(Condition.getQueryWrapper(menu));
		return R.data(MenuWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "菜单编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "string")
	})
	@PreAuth(RoleConstant.HAS_ROLE_TENANT_ADMIN)
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入menu")
	public R<List<MenuVO>> list(AuthUser user,@ApiIgnore @RequestParam Map<String, Object> menu) {
		//查询登陆人企业下租户的所有菜单
		ArrayList<Long> systemIds = new ArrayList<>();
		QueryWrapper<SystemEntity> systemEntityQueryWrapper = new QueryWrapper<>();
		if(user!=null && user.getEnterpriseId()!=null ){
			QueryWrapper<Tenant> tenantQueryWrapper = new QueryWrapper<>();
			tenantQueryWrapper.eq("enterprise_id",user.getEnterpriseId());
			List<Tenant> tenantList = tenantService.list(tenantQueryWrapper);//查询登陆人下的所有租户
			ArrayList<String> strings = new ArrayList<>();
			if(tenantList!=null && tenantList.size()>0){
				tenantList.stream().forEach(tenant -> {
					strings.add(tenant.getTenantId());
				});
			}
			if(strings.size()>0) {
				systemEntityQueryWrapper.in("tenant_id", strings);
			}
			List<SystemEntity> list = systemService.list(systemEntityQueryWrapper);
			if(list!=null && list.size()>0){
				list.stream().forEach(sysEntity->{
					systemIds.add(sysEntity.getId());
				});
			}
		}
		QueryWrapper<Menu> queryWrapper = Condition.getQueryWrapper(menu, Menu.class);
		if(systemIds!=null && systemIds.size()>0){
			queryWrapper.in("system_id",systemIds);
		}
		@SuppressWarnings("unchecked")
		List<Menu> list = menuService.list(queryWrapper.lambda().orderByAsc(Menu::getSort));
		List<MenuVO> menuVOS = MenuWrapper.build().listNodeVO(list);
		if(menuVOS!=null && menuVOS.size()>0){
			menuVOS.stream().forEach(menuVO->{
				menuVO.setSystemName(systemService.getById(menuVO.getSystemId()).getSystemName());
			});
		}
		return R.data(menuVOS);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@PreAuth(RoleConstant.HAS_ROLE_TENANT_ADMIN)
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增或修改", notes = "传入menu")
	public R submit(@Valid @RequestBody Menu menu) {
		return R.status(menuService.saveOrUpdate(menu));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@PreAuth(RoleConstant.HAS_ROLE_TENANT_ADMIN)
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(menuService.removeByIds(Func.toLongList(ids)));
	}


	@PostMapping("/serviceMenus")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "菜单列表", notes = "传入enterpriseid")
	public R<List<MenuVO>> queryEnterpriseServiceMenus(String enterpriseId){


		return  null;
	}


	/**
	 * 前端菜单数据
	 */
	@GetMapping("/routes")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "前端菜单数据", notes = "前端菜单数据")
	public R<List<MenuVO>> routes(AuthUser user) {
		System.out.println("~~~~~~~~~~~AuthUser IS:"+user);
		List<MenuVO> list = menuService.routes(user.getRoleId());
		return R.data(list);
	}

	/**
	 * 前端按钮数据
	 */
	@GetMapping("/buttons")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "前端按钮数据", notes = "前端按钮数据")
	public R<List<MenuVO>> buttons(AuthUser user) {
		List<MenuVO> list = menuService.buttons(user.getRoleId());
		return R.data(list);
	}

	/**
	 * 获取菜单树形结构
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "功能树", notes = "功能树")
	public R<List<MenuVO>> tree() {
		List<MenuVO> tree = menuService.tree();
		return R.data(tree);
	}

	/**
	 * 获取权限分配树形结构
	 */
	@GetMapping("/grant-tree")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "权限分配树形结构", notes = "权限分配树形结构")
	public R<List<MenuVO>> grantTree(AuthUser user) {

		return R.data(menuService.grantTree(user));
	}

	/**
	 * 服务包菜单分配树形结构
	 */
	@GetMapping("/grantservicemenu-tree")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "权限分配树形结构", notes = "权限分配树形结构")
	public R<List<MenuVO>> grantServiceTree(AuthUser user) {
		return R.data(menuService.grantServiceMenuTree(user));
	}

	/**
	 * 获取权限分配树形结构
	 */
	@GetMapping("/role-tree-keys")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "角色所分配的树", notes = "角色所分配的树")
	public R<List<String>> roleTreeKeys(String roleIds) {
		return R.data(menuService.roleTreeKeys(roleIds));
	}

	/**
	 * 获取配置的角色权限
	 */
	@GetMapping("auth-routes")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "菜单的角色权限")
	public R<List<Kv>> authRoutes(AuthUser user) {
		return R.data(menuService.authRoutes(user));
	}

}
