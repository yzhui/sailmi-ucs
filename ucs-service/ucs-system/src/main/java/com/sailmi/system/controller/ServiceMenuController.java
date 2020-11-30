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
import com.sailmi.core.secure.AuthUser;
import com.sailmi.system.entity.*;
import com.sailmi.system.feign.IEnterpriseFeign;
import com.sailmi.system.service.IMenuService;
import com.sailmi.system.service.IServiceMenuService;
import com.sailmi.system.service.IServiceService;
import com.sailmi.system.service.ISystemService;
import com.sailmi.system.vo.EnterpriseVO;
import com.sailmi.system.vo.ServiceMenuVO;
import com.sailmi.system.wrapper.ServiceMenuWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import com.sailmi.core.mp.support.Condition;
import com.sailmi.core.mp.support.Query;
import com.sailmi.core.tool.api.R;
import com.sailmi.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QuerydslUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.sailmi.core.boot.ctrl.AppController;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务功能包设定，表明此功能属于哪个服务包，只有享受此服务包的用户才能访问这个功能 控制器
 *
 * @author sailmi
 * @since 2020-09-17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/servicemenu")
@Api(value = "服务功能包设定，表明此功能属于哪个服务包，只有享受此服务包的用户才能访问这个功能", tags = "服务功能包设定，表明此功能属于哪个服务包，只有享受此服务包的用户才能访问这个功能接口")
public class ServiceMenuController extends AppController {

	private IServiceMenuService serviceMenuService;
	private ISystemService systemService;
	private IServiceService serviceService;
	private IMenuService menuService;
	private IEnterpriseFeign  iEnterpriseFeign;


	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入serviceMenu")
	public R<ServiceMenuVO> detail(ServiceMenu serviceMenu) {
		ServiceMenu detail = serviceMenuService.getOne(Condition.getQueryWrapper(serviceMenu));
		return R.data(ServiceMenuWrapper.build().entityVO(detail));
	}

	@GetMapping("/serviceMenus")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取服务包的菜单列表", notes = "传入AuthUser")
	public R<List<MenuTreeResultEntity>> queryServiceMenus(AuthUser authUser) {
		List<MenuTreeResultEntity> menuList =new ArrayList<>();

		if(authUser!=null && authUser.getEnterpriseId()!=null){
			ServiceEnterprise serviceEnterprise = new ServiceEnterprise();
			serviceEnterprise.setEnterpriseId(Long.valueOf(authUser.getEnterpriseId()));
			R<List<ServiceEnterprise>> servicelist = iEnterpriseFeign.servicelist(serviceEnterprise);
			if(servicelist!=null && servicelist.getData()!=null && servicelist.getData().size()>0){
				ArrayList<String> serviceIds = new ArrayList<>();
				servicelist.getData().stream().forEach(serviceEnterprise1 -> {
					serviceIds.add(serviceEnterprise1.getServiceId().toString());
				});
				if(serviceIds.size()>0) {
					QueryWrapper<ServiceMenu> serviceMenuQueryWrapper = new QueryWrapper<>();
					serviceMenuQueryWrapper.in("service_id", serviceIds);
					List<ServiceMenu> list = serviceMenuService.list(serviceMenuQueryWrapper);
					if (list != null && list.size() > 0) {
						ArrayList<Long> menuIds = new ArrayList<>();
						list.stream().forEach(ServiceMenu -> {
							menuIds.add(ServiceMenu.getMenuId());
						});
						//查询菜单信息
						if (menuIds.size() > 0) {
							QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
							menuQueryWrapper.in("id", menuIds);
							menuList = serviceMenuService.queryUserMenus(menuQueryWrapper);
						}
					}
				}
			}
		}
		return R.data(menuList);
	}


	/**
	* 分页 服务功能包设定，表明此功能属于哪个服务包，只有享受此服务包的用户才能访问这个功能
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入serviceMenu")
	public R<IPage<ServiceMenuVO>> list(ServiceMenu serviceMenu, Query query) {
		IPage<ServiceMenu> pages = serviceMenuService.page(Condition.getPage(query), Condition.getQueryWrapper(serviceMenu));
		return R.data(ServiceMenuWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 服务功能包设定，表明此功能属于哪个服务包，只有享受此服务包的用户才能访问这个功能
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入serviceMenu")
	public R<IPage<ServiceMenuVO>> page(ServiceMenuVO serviceMenu, Query query) {
		IPage<ServiceMenuVO> pages = serviceMenuService.selectServiceMenuPage(Condition.getPage(query), serviceMenu);
		return R.data(pages);
	}

	/**
	* 新增 服务功能包设定，表明此功能属于哪个服务包，只有享受此服务包的用户才能访问这个功能
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入serviceMenu")
	public R save(@Valid @RequestBody ServiceMenu serviceMenu) {
		return R.status(serviceMenuService.save(serviceMenu));
	}

	/**
	* 修改 服务功能包设定，表明此功能属于哪个服务包，只有享受此服务包的用户才能访问这个功能
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入serviceMenu")
	public R update(@Valid @RequestBody ServiceMenu serviceMenu) {
		return R.status(serviceMenuService.updateById(serviceMenu));
	}

	/**
	* 新增或修改 服务功能包设定，表明此功能属于哪个服务包，只有享受此服务包的用户才能访问这个功能
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入serviceMenu")
	public R submit(@Valid @RequestBody ServiceMenu serviceMenu) {
		return R.status(serviceMenuService.saveOrUpdate(serviceMenu));
	}


	/**
	* 删除 服务功能包设定，表明此功能属于哪个服务包，只有享受此服务包的用户才能访问这个功能
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(serviceMenuService.removeByIds(Func.toLongList(ids)));
	}


}
