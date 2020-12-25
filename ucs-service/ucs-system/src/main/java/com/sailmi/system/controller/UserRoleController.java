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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.system.entity.UserRole;
import com.sailmi.system.service.IUserRoleService;
import com.sailmi.system.vo.UserRoleVO;
import com.sailmi.system.wrapper.UserRoleWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.sailmi.core.mp.support.Condition;
import com.sailmi.core.mp.support.Query;
import com.sailmi.core.tool.api.R;
import com.sailmi.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

import com.sailmi.core.boot.ctrl.AppController;

import java.util.List;


/**
 *  控制器
 *
 * @author sailmi
 * @since 2020-10-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/userrole")
@Api(value = "", tags = "接口")
public class UserRoleController extends AppController {

	private IUserRoleService userRoleService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入userRole")
	public R<UserRoleVO> detail(UserRole userRole) {
		UserRole detail = userRoleService.getOne(Condition.getQueryWrapper(userRole));
		return R.data(UserRoleWrapper.build().entityVO(detail));
	}


	/**
	* 分页
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入userRole")
	public R<IPage<UserRoleVO>> list(UserRole userRole, Query query) {
		IPage<UserRole> pages = userRoleService.page(Condition.getPage(query), Condition.getQueryWrapper(userRole));
		return R.data(UserRoleWrapper.build().pageVO(pages));
	}

	@GetMapping("/urlist")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入userRole")
	public R<List<UserRole>> querylist(UserRole userRole, Query query) {
		List<UserRole> ulist = userRoleService.list(Condition.getQueryWrapper(userRole));
		return R.data(ulist);
	}

	@GetMapping("/rolelist")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "人员列表", notes = "传入roleId")
	public R<List<UserRole>> queryRolelist(HttpServletRequest request) {
		String roleId = request.getParameter("id");
		List<UserRole> ulist = userRoleService.queryUserlistByRoleId(roleId);
		return R.data(ulist);
	}

	/**
	* 自定义分页
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入userRole")
	public R<IPage<UserRoleVO>> page(UserRoleVO userRole, Query query) {
		IPage<UserRoleVO> pages = userRoleService.selectUserRolePage(Condition.getPage(query), userRole);
		return R.data(pages);
	}

	/**
	* 新增
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入userRole")
	public R save(@Valid @RequestBody UserRole userRole) {
		return R.status(userRoleService.save(userRole));
	}

	/**
	* 修改
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入userRole")
	public R update(@Valid @RequestBody UserRole userRole) {
		return R.status(userRoleService.updateById(userRole));
	}

	/**
	* 新增或修改
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入userRole")
	public R submit(@Valid @RequestBody UserRole userRole) {
		return R.status(userRoleService.saveOrUpdate(userRole));
	}


	/**
	* 删除
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(userRoleService.removeByIds(Func.toLongList(ids)));
	}


}
