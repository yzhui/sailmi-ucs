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
import com.sailmi.core.secure.annotation.PreAuth;
import com.sailmi.core.tool.constant.RoleConstant;
import com.sailmi.system.entity.SystemEntity;
import com.sailmi.system.entity.Tenant;
import com.sailmi.system.service.ISystemService;
import com.sailmi.system.service.ITenantService;
import com.sailmi.system.user.entity.UserInfo;
import com.sailmi.system.user.feign.IUserClient;
import com.sailmi.system.vo.SystemVO;
import com.sailmi.system.wrapper.SystemWrapper;
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
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.sailmi.core.boot.ctrl.AppController;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统表，租户->系统->企业，关系为，平台可以有多个租户，一个租户可以创建多个系统，一个系统，可以有多个client，一个企业可以入驻到多个系统中。企业可以采购多个服务\r\n 控制器
 *
 * @author sailmi
 * @since 2020-09-09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/system")
@Api(value = "系统表，租户->系统->企业，关系为，平台可以有多个租户，一个租户可以创建多个系统，一个系统，可以有多个client，一个企业可以入驻到多个系统中。企业可以采购多个服务\r\n", tags = "系统表，租户->系统->企业，关系为，平台可以有多个租户，一个租户可以创建多个系统，一个系统，可以有多个client，一个企业可以入驻到多个系统中。企业可以采购多个服务\r\n接口")
public class SystemController extends AppController {

	private ISystemService systemService;
	private IUserClient userClient;
	private ITenantService tenantService;
	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入system")
	public R<SystemVO> detail(SystemEntity system) {
		SystemEntity detail = systemService.getOne(Condition.getQueryWrapper(system));
		return R.data(SystemWrapper.build().entityVO(detail));
	}

	/**
	* 分页 系统表，租户->系统->企业，关系为，平台可以有多个租户，一个租户可以创建多个系统，一个系统，可以有多个client，一个企业可以入驻到多个系统中。企业可以采购多个服务\r\n
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入system")
	@PreAuth(RoleConstant.HAS_ROLE_TENANT_ADMIN)
	public R<IPage<SystemVO>> list(AuthUser user, SystemEntity system, Query query) {
		QueryWrapper<SystemEntity> queryWrapper = Condition.getQueryWrapper(system);
		if(user!=null && user.getEnterpriseId()!=null){
			QueryWrapper<Tenant> tenantQueryWrapper = new QueryWrapper<>();
			tenantQueryWrapper.eq("enterprise_id",user.getEnterpriseId());//查询改企业管理的所有的租户
			List<Tenant> tenantList = tenantService.list(tenantQueryWrapper);
			ArrayList<String> longs = new ArrayList<>();
			if(tenantList!=null && tenantList.size()>0){
				tenantList.stream().forEach(teant->{
					longs.add(teant.getTenantId());
				});
			}
			if(longs.size()>0) {
				queryWrapper.in("tenant_id", longs);
			}
		}
		IPage<SystemEntity> pages = systemService.page(Condition.getPage(query), queryWrapper);
		IPage<SystemVO> systemVOIPage = SystemWrapper.build().pageVO(pages);
		if(systemVOIPage!=null && systemVOIPage.getTotal()>0){
			systemVOIPage.getRecords().stream().forEach(systemVO->{
				R<UserInfo> userInfoR = userClient.userInfo(systemVO.getCreateUser());
				if(userInfoR!=null && userInfoR.getData()!=null && userInfoR.getData().getUser()!=null && userInfoR.getData().getUser().getRealName()!=null) {
					systemVO.setCreateName(userInfoR.getData().getUser().getRealName());
				}
				R<UserInfo> userInfoU = userClient.userInfo(systemVO.getUpdateUser());
				if(userInfoU!=null && userInfoU.getData()!=null && userInfoU.getData().getUser()!=null && userInfoU.getData().getUser().getRealName()!=null) {
					systemVO.setUpdateName(userInfoU.getData().getUser().getRealName());
				}
			});
		}
		return R.data(systemVOIPage);
	}

	@GetMapping("/systemlist")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "下拉列表", notes = "传入system")
	public R<List<SystemEntity>> querylist(SystemEntity system) {
		List<SystemEntity> list = systemService.list();
		return R.data(list);
	}

	/**
	* 自定义分页 系统表，租户->系统->企业，关系为，平台可以有多个租户，一个租户可以创建多个系统，一个系统，可以有多个client，一个企业可以入驻到多个系统中。企业可以采购多个服务\r\n
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入system")
	public R<IPage<SystemVO>> page(SystemVO system, Query query) {
		IPage<SystemVO> pages = systemService.selectSystemPage(Condition.getPage(query), system);
		return R.data(pages);
	}

	/**
	* 新增 系统表，租户->系统->企业，关系为，平台可以有多个租户，一个租户可以创建多个系统，一个系统，可以有多个client，一个企业可以入驻到多个系统中。企业可以采购多个服务\r\n
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入system")
	public R save(@Valid @RequestBody SystemEntity system) {
		return R.status(systemService.save(system));
	}

	/**
	* 修改 系统表，租户->系统->企业，关系为，平台可以有多个租户，一个租户可以创建多个系统，一个系统，可以有多个client，一个企业可以入驻到多个系统中。企业可以采购多个服务\r\n
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入system")
	public R update(@Valid @RequestBody SystemEntity system) {
		return R.status(systemService.updateById(system));
	}

	/**
	* 新增或修改 系统表，租户->系统->企业，关系为，平台可以有多个租户，一个租户可以创建多个系统，一个系统，可以有多个client，一个企业可以入驻到多个系统中。企业可以采购多个服务\r\n
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入system")
	public R submit(@Valid @RequestBody SystemEntity system) {
		return R.status(systemService.saveOrUpdate(system));
	}


	/**
	* 删除 系统表，租户->系统->企业，关系为，平台可以有多个租户，一个租户可以创建多个系统，一个系统，可以有多个client，一个企业可以入驻到多个系统中。企业可以采购多个服务\r\n
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(systemService.removeByIds(Func.toLongList(ids)));
	}


}
